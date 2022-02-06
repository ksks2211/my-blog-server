package kr.yoonyeong.blog.dto;

import kr.yoonyeong.blog.entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rival
 * @since 2022-02-06
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
  private String id;
  private String title;
  private boolean done;


  public TodoDTO(final TodoEntity entity){
    this.id=entity.getId();
    this.title= entity.getTitle();
    this.done = entity.isDone();
  }

  public static TodoEntity toEntity(final TodoDTO dto,final String userId){
    return TodoEntity.builder()
      .id(dto.getId())
      .title(dto.getTitle())
      .done(dto.isDone())
      .userId(userId)
      .build();
  }
}