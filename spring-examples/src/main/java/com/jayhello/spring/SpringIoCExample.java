package com.jayhello.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring IoC容器示例
 */
@Configuration
public class SpringIoCExample {
    
    @Bean
    public MessageService messageService() {
        return new MessageService();
    }
    
    public static class MessageService {
        public String getMessage() {
            return "Hello from Spring!";
        }
    }
    
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext(SpringIoCExample.class)) {
            MessageService service = context.getBean(MessageService.class);
            System.out.println(service.getMessage());
        }
    }
}
