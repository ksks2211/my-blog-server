package kr.yoonyeong.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author rival
 * @since 2022-02-06
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class TodoEntity {

  @Id
  @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid",strategy = "uuid")
  private String id;
  private String userId;
  private String title;
  private boolean done;

  public void changeUserId(String userId){
    this.userId = userId;
  }
  public void changeTitle(String title){this.title = title;}
  public void changeDone(boolean done){this.done = done;}
}
