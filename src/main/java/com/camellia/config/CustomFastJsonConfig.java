package com.camellia.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class CustomFastJsonConfig {
    @Bean
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy年MM月dd日");
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteClassName, //是否在生产的json中生成类名
                SerializerFeature.WriteMapNullValue, //是否输出value为null的数据
                SerializerFeature.PrettyFormat, // 生产JSON的格式化
                SerializerFeature.WriteNullListAsEmpty, // 空集合输出[]而不是null
                SerializerFeature.WriteNullStringAsEmpty // 空字符串输出""而不是null
        );
        List<MediaType> supportMediaType=new ArrayList<MediaType>();
        //设置编码格式为UTF-8
        supportMediaType.add(new MediaType("application","json",StandardCharsets.UTF_8));
        converter.setSupportedMediaTypes(supportMediaType);
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }
}
