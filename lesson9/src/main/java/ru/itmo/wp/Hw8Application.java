package ru.itmo.wp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.itmo.wp.security.interceptor.SecurityInterceptor;

@SpringBootApplication
public class Hw8Application implements WebMvcConfigurer {

    private final SecurityInterceptor securityInterceptor;

    public Hw8Application(SecurityInterceptor securityInterceptor) {
        this.securityInterceptor = securityInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor);
    }

    public static void main(String[] args) {
        SpringApplication.run(Hw8Application.class, args);
    }
}
