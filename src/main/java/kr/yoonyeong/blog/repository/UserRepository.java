package kr.yoonyeong.blog.repository;

import kr.yoonyeong.blog.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rival
 * @since 2022-02-06
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
  UserEntity findByEmail(String email);
  boolean existsByEmail(String email);
  UserEntity findByEmailAndPassword(String email, String password);
}
