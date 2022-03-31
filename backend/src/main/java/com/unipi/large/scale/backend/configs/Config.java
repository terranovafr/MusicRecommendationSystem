package com.unipi.large.scale.backend.configs;

import com.unipi.large.scale.backend.dtos.CommentDto;
import com.unipi.large.scale.backend.entities.mongodb.Comment;
import org.bson.types.ObjectId;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(5);
    }

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Converter<String, ObjectId> toObjectId = new AbstractConverter<>() {
            protected ObjectId convert(String source) {
                return source == null ? null : new ObjectId(source);
            }
        };

        TypeMap<CommentDto, Comment> typeMap1 = modelMapper.createTypeMap(CommentDto.class, Comment.class);
        typeMap1.addMappings(mapper -> {
            mapper.using(toObjectId)
                    .map(CommentDto::getUserId, Comment::setUserId);
            mapper.using(toObjectId)
                    .map(CommentDto::getSongId, Comment::setSongId);

        });

        return modelMapper;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@SuppressWarnings("NullableProblems") CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
