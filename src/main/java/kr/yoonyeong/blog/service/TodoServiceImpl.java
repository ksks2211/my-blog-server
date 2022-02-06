package kr.yoonyeong.blog.service;

import kr.yoonyeong.blog.entity.TodoEntity;
import kr.yoonyeong.blog.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author rival
 * @since 2022-02-06
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

  private final TodoRepository repository;

  @Override
  public String testService() {
    TodoEntity entity = TodoEntity.builder()
      .title("My First doto item")
      .build();
    TodoEntity save = repository.save(entity);
    return save.getTitle();
  }

  @Override
  public List<TodoEntity> create(final TodoEntity entity) {
    validate(entity);
    TodoEntity save = repository.save(entity);
    log.info("Entity : {} is saved",save.getId());
    return repository.findByUserId(save.getUserId());
  }

  @Override
  public List<TodoEntity> retrieve(final String userId) {
    return repository.findByUserId(userId);
  }

  @Override
  @Transactional
  public List<TodoEntity> update(final TodoEntity entity) {
    validate(entity);

    final Optional<TodoEntity> original = repository.findById(entity.getId());

    original.ifPresent(todo->{
      todo.changeTitle(entity.getTitle());
      todo.changeDone(entity.isDone());

      //repository.save(todo);
    });

    return retrieve(entity.getUserId());
  }

  @Override
  public List<TodoEntity> delete(final TodoEntity entity) {

    validate(entity);

    try{
      repository.delete(entity);
    }catch(Exception e){
      log.error("error deleting entity",entity.getId(),e);
      throw new RuntimeException("error deleting entity"+entity.getId());
    }
    return retrieve(entity.getUserId());
  }


  private void validate(final TodoEntity entity){
    if(entity==null){
      log.warn("Entity cannot be null");
      throw new RuntimeException("Entity cannot be null");
    }

    if(entity.getUserId() == null){
      log.warn("Unknown user.");
      throw new RuntimeException("Unknown user.");

    }
  }
}

