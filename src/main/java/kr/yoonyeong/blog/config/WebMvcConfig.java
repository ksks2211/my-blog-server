package kr.yoonyeong.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author rival
 * @since 2022-02-06
 */
@Configuration // Spring Bean 등록
public class WebMvcConfig implements WebMvcConfigurer {

  private final long MAX_AGE_SECS = 3600;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // 경로 지정
      .allowedOrigins("http://localhost:3000","http://192.168.0.3:3000") // cors 허용할 origin 설정
      .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS") // 허용 메서드
      .allowedHeaders("*") // 허용 헤더
      .allowCredentials(true) // credential : true
      .maxAge(MAX_AGE_SECS); // 허용 시간

  }
}
