package kr.yoonyeong.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author rival
 * @since 2022-02-06
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Entity
@Data
public class UserEntity {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name="system-uuid",strategy = "uuid")
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(nullable=false)
  private String username;

  @Column(nullable=false)
  private String email;

  @Column(nullable=false)
  private String password;
}
