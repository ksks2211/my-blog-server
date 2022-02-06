package kr.yoonyeong.blog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.yoonyeong.blog.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author rival
 * @since 2022-02-06
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private TokenProvider tokenProvider;

  public JwtAuthenticationFilter(TokenProvider tokenProvider){
    this.tokenProvider=tokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try{
      // 토큰 추출
      String token = parseBearerToken(request);
      log.info("Filter is running...");

      if(token!=null && !token.equalsIgnoreCase("null")){
        // 토큰 검증
        String userId = tokenProvider.validateAndGetUserId(token);
        log.info("Authenticated user ID : "+userId);

        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userId,
          null,
          AuthorityUtils.NO_AUTHORITIES
        );

        // setDetail
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

      }
    }catch(Exception e){
      logger.error("Could not set user authentication in security context");
      // 토큰인증 실패 응답
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.setContentType("application/json; charset=utf-8");

      String message = "INVALID TOKEN";
      ResponseDTO responseDTO = ResponseDTO.builder()
        .error(message)
        .build();

      ObjectMapper objectMapper = new ObjectMapper();
      response.getWriter().print(objectMapper.writeValueAsString(responseDTO));
      return;


    }
    filterChain.doFilter(request, response);
  }

  private String parseBearerToken(HttpServletRequest request){
    String bearerToken = request.getHeader("Authorization");
    log.info("Token : "+bearerToken);
    if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer ")){
      return bearerToken.substring(7);
    }
    return null;
  }

}
