package org.cmyk.aifocus.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class SwaggerConfig {
    //Gson
    @Bean
    public HttpMessageConverters customConverters() {

        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        messageConverters.add(gsonHttpMessageConverter);

        return new HttpMessageConverters(true, messageConverters);
    }

    // api-doc for gson
    @Bean
    public FilterRegistrationBean<DocsFormatterFilter> loggingFilter() {
        FilterRegistrationBean<DocsFormatterFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new DocsFormatterFilter());
        registrationBean.addUrlPatterns("/v3/api-docs");

        return registrationBean;
    }
}