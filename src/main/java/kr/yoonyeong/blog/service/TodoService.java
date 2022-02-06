package kr.yoonyeong.blog.service;

import kr.yoonyeong.blog.entity.TodoEntity;

import java.util.List;

/**
 * @author rival
 * @since 2022-02-06
 */
public interface TodoService {

  public String testService();

  public List<TodoEntity> create(final TodoEntity entity);


  public List<TodoEntity> retrieve(final String userId);


  public List<TodoEntity> update(final TodoEntity entity);


  public List<TodoEntity> delete(final TodoEntity entity);

}
