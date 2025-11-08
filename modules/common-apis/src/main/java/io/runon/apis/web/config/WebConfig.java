package io.runon.apis.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author macle
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 1. 모든 경로(/**)에 이 CORS 설정을 적용합니다.
                .allowedOriginPatterns(
                        // 2. http 허용
                        "http://runon.io",       // http://runon.io (기본 포트)
                        "http://runon.io:*",     // http://runon.io (모든 포트)
                        "http://*.runon.io",     // http://sub.runon.io (기본 포트)
                        "http://*.runon.io:*",   // ★★★ 모든 서브도메인, 모든 포트 (예: http://dev.runon.io:30399)

                        // 3. https 허용
                        "https://runon.io",
                        "https://runon.io:*",
                        "https://*.runon.io",
                        "https://*.runon.io:*" // ★★★ 모든 서브도메인, 모든 포트 (https)
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 4. 허용할 HTTP 메서드
                .allowedHeaders("*")    // 5. 허용할 모든 헤더
                .allowCredentials(true) // 6. 쿠키/자격 증명 허용
                .maxAge(3600);          // 7. (선택) Pre-flight 요청 캐시 시간(초)
    }
}