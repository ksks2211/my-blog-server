package kr.yoonyeong.blog.repository;

import kr.yoonyeong.blog.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rival
 * @since 2022-02-06
 */
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity,String> {
  @Query("select t from TodoEntity t where t.userId = :id")
  List<TodoEntity> findByUserId(String id);
}
