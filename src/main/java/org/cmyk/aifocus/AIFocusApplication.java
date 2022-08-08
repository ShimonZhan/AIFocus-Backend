package org.cmyk.aifocus;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("org.cmyk.aifocus.dao")
@EnableCaching
@EnableAsync
@OpenAPIDefinition(
        info = @Info(
                title = "AIFocus-back",
                version = "1.0",
                description = "基于人工智能监控的分布式在线考试系统-后端api"
        ),
        externalDocs = @ExternalDocumentation(description = "参考文档",
                url = "https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations"
        )
)
public class AIFocusApplication {
    public static void main(String[] args) {
        SpringApplication.run(AIFocusApplication.class, args);
    }
}