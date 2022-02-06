package kr.yoonyeong.blog.controller;

import kr.yoonyeong.blog.dto.ResponseDTO;
import kr.yoonyeong.blog.dto.TodoDTO;
import kr.yoonyeong.blog.entity.TodoEntity;
import kr.yoonyeong.blog.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rival
 * @since 2022-02-06
 */
@RestController
@RequestMapping("todo")
@RequiredArgsConstructor
@Slf4j
public class TodoController {


  private final TodoService service;

  @GetMapping("/test")
  public ResponseEntity<?> test(){
    String str = service.testService();
    List<String> list = new ArrayList<>();
    list.add(str);

    ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
    return ResponseEntity.ok().body(response);
  }


  @GetMapping("/testResponseEntity")
  public ResponseEntity<?> testControllerRespopnseEntity(){

    List<String> list = new ArrayList<>();
    list.add("Hello World! I'm ResponseEntity. And you got 400!");
    ResponseDTO<String> response = ResponseDTO.<String>builder()
      .data(list)
      .build();
    return ResponseEntity.badRequest().body(response);

  }


  @PostMapping
  public ResponseEntity<?> createTodo(
    @AuthenticationPrincipal String userId,
    @RequestBody TodoDTO dto){
    try{
      TodoEntity entity = TodoDTO.toEntity(dto,userId);

      List<TodoEntity> entities = service.create(entity);

      List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

      return ResponseEntity.ok().body(response);
    }catch(Exception e){
      String error = e.getMessage();
      log.error(error);

      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
      return ResponseEntity.badRequest().body(response);

    }
  }

  @GetMapping
  public ResponseEntity<?> retrieveTodoList(
    @AuthenticationPrincipal String userId
  ){
    List<TodoEntity> entities = service.retrieve(userId);

    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());


    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
      .data(dtos)
      .build();

    return ResponseEntity.ok().body(response);
  }


  @PutMapping
  public ResponseEntity<?> updateTodo(
    @AuthenticationPrincipal String userId,
    @RequestBody TodoDTO dto){

    TodoEntity entity = TodoDTO.toEntity(dto,userId);

    List<TodoEntity> entities = service.update(entity);

    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
      .data(dtos)
      .build();

    return ResponseEntity.ok().body(response);
  }


  @DeleteMapping
  public ResponseEntity<?> deleteTodo(
    @AuthenticationPrincipal String userId,
    @RequestBody TodoDTO dto){

    TodoEntity entity = TodoDTO.toEntity(dto, userId);

    List<TodoEntity> entities = service.delete(entity);
    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
      .data(dtos)
      .build();

    return ResponseEntity.ok().body(response);
  }
}

