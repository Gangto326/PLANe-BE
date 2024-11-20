package com.plane.config;

import org.apache.ibatis.type.TypeHandlerRegistry;
import org.locationtech.jts.geom.Point;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.plane.common.handler.PointTypeHandler;

@Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();
            
            registry.register(Point.class, PointTypeHandler.class);
            
            configuration.setMapUnderscoreToCamelCase(true);
        };
    }
}