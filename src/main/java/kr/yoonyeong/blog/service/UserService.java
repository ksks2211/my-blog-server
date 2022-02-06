package kr.yoonyeong.blog.service;

import kr.yoonyeong.blog.entity.UserEntity;
import kr.yoonyeong.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author rival
 * @since 2022-02-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;


  public UserEntity create(final UserEntity userEntity) {
    if(userEntity == null || userEntity.getEmail() == null ){
      throw new RuntimeException("Invalid arguments");
    }

    final String email = userEntity.getEmail();
    if(userRepository.existsByEmail(email)){
      log.warn("Email already exists {}",email);
      throw new RuntimeException("Email already exists");
    }

    return userRepository.save(userEntity);
  }

  public UserEntity getByCredentials(String email, String password) { // password : 원본

    final UserEntity originalUser = userRepository.findByEmail(email);

    if(originalUser !=null && passwordEncoder.matches(password,originalUser.getPassword()))
      return originalUser;

    return null;
  }
}
