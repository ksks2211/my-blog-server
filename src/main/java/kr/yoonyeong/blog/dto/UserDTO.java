package kr.yoonyeong.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rival
 * @since 2022-02-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private String token;
  private String email;
  private String username;
  private String password;
  private String id;
}
