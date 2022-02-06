package kr.yoonyeong.blog.controller;

import kr.yoonyeong.blog.dto.ResponseDTO;
import kr.yoonyeong.blog.dto.UserDTO;
import kr.yoonyeong.blog.entity.UserEntity;
import kr.yoonyeong.blog.security.TokenProvider;
import kr.yoonyeong.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rival
 * @since 2022-02-06
 */
@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  private final TokenProvider tokenProvider;

  // 회원가입 컨트롤러
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
    try{
      UserEntity user = UserEntity.builder()
        .email(userDTO.getEmail())
        .username(userDTO.getUsername())
        .password(passwordEncoder.encode(userDTO.getPassword()))
        .build();

      UserEntity registeredUser = userService.create(user);
      UserDTO responseUserDTO = UserDTO.builder()
        .email(registeredUser.getEmail())
        .id(registeredUser.getId())
        .username(registeredUser.getUsername())
        .build();
      return ResponseEntity.ok(responseUserDTO);
    }catch(Exception e){
      ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }


  // 로그인, 컨트롤러 (필터를 이용하는것도 가능)
  @PostMapping("/signin")
  public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
    UserEntity user = userService.getByCredentials(
      userDTO.getEmail(),
      userDTO.getPassword()
    );

    if(user!=null){
      // 로그인 성공

      final String token = tokenProvider.create(user);// 임시

      final UserDTO responseUserDTO = UserDTO.builder()
        .email(user.getEmail())
        .id(user.getId())
        .token(token)
        .build();
      return ResponseEntity.ok().body(responseUserDTO);
    }else{
      // 로그인 실패
      ResponseDTO responseDTO = ResponseDTO.builder()
        .error("Login Failed")
        .build();
      return ResponseEntity
        .badRequest()
        .body(responseDTO);
    }
  }
}
