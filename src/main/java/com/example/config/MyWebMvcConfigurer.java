package com.example.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: MyWebMvcConfigurer
 * @Description: 配置 SpringMvc
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {  // WebMvcConfigurerAdapter

    private final Logger logger = LoggerFactory.getLogger(MyWebMvcConfigurer.class);

    /**
     * 消息转换器 消息内容转换
     * 使用阿里 FastJson 作为JSON MessageConverter
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);//保留空的字段
        //SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
        //SerializerFeature.WriteNullNumberAsZero//Number null -> 0
        // 参考FastJson文档

        converter.setFastJsonConfig(config);
        //converter.setDefaultCharset(Charset.forName("UTF-8"));
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        //converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converters.add(converter);
    }

    /**
     * 页面跨域访问Controller过滤
     *
     * @return
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //super.addCorsMappings(registry);
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE")
                .allowedOrigins("*");
    }

    // 注意 :
    // 1. addResourceHandler 参数可以有多个
    // 2. addResourceLocations 参数可以是多个，可以混合使用 file: 和 classpath : 资源路径
    // 3. addResourceLocations 参数中资源路径必须使用 / 结尾，如果没有此结尾则访问不到
    // 映射到文件系统中的静态文件(应用运行时，这些文件无业务逻辑，但可能被替换或者修改)
    // 映射到jar包内的静态文件(真正的静态文件，应用运行时，这些文件无业务逻辑，也不能被替换或者修改)
    /**
     * 如果继承了WebMvcConfigurationSupport，在yml中配置的相关内容会失效。 需要重新指定静态资源
     * 静态资源文件映射配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("doc.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        //super.addResourceHandlers(registry);
        WebMvcConfigurer.super.addResourceHandlers(registry);
        //super.wait();
    }


    /**
     * 配置servlet处理
     */
    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }




}

