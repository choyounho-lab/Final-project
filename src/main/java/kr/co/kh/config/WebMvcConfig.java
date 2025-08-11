package kr.co.kh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:static/"
    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost", "http://localhost:81", "http://localhost:8000", "http://200.200.200.58", "http://200.200.200.58:81", "http://200.200.200.122", "http://200.200.200.122:81", "http://200.200.200.58:8000", "http://localhost:8000")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // 모든 도메인에서의 요청을 허용
//                .allowedOrigins("*")
//                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
//                .allowedHeaders("*")  // 모든 헤더 허용 allowedHeaders, exposedHeaders, allowCredentials 3가지 추가
//                .exposedHeaders("Authorization")  // 클라이언트에서 사용할 수 있는 헤더 노출
//                .allowCredentials(true)  // 자격증명(쿠키, 세션 등)을 허용할 경우
//                .maxAge(MAX_AGE_SECS);
//    }

}
