package org.cmyk.aifocus.config;

import com.upyun.RestManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {
    @Value("${upyun.oss.space}")
    private String SPACE;
    @Value("${upyun.oss.user}")
    private String USER;
    @Value("${upyun.oss.password}")
    private String PASSWORD;

    @Bean
    public RestManager restManager() {
        return new RestManager(SPACE, USER, PASSWORD);
    }
}
