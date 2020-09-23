package com.backend.config;
import com.backend.Interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedOrigins("http://i3a507.p.ssafy.io:8080")
                .allowedHeaders("*")
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("swagger-ui.html")
                .exposedHeaders("jwt-auth-token");
    }

    @Autowired
    private JwtInterceptor jwtInterceptor;

    // 댓글, 댓글 수정삭제, 포스트 수정 삭제 ,
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/signin/"); // 기본 적용 경로
//                .addPathPatterns("/api/info/");
//				.excludePathPatterns(Arrays.asList("/api/signin/**")) // 제외 경로
//				.excludePathPatterns(Arrays.asList("/api/info/**"));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
