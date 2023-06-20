package com.alisa.amazon.clone.backend.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.*;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration
public class MapperFactoryConfig {
    ObjectMapper mapper;

    @Bean
    @ConditionalOnMissingBean
    public MapperFactory mapperFactory() {
        DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new ToEnumConverter());
        converterFactory.registerConverter(new ToObjectConverter());
        converterFactory.registerConverter(new ToStringConverter());
        converterFactory.registerConverter(new DateConverter());
        return mapperFactory;
    }

    private class ToObjectConverter implements Converter<Object, Object> {
        @Override
        public Type<Object> getAType() {
            return null;
        }

        @Override
        public Type<Object> getBType() {
            return null;
        }

        @Override
        public boolean canConvert(Type<?> sourceType, Type<?> destinationType) {
            return sourceType.isString() && !destinationType.isString() && !destinationType.isEnum();
        }

        @Override
        public Object convert(Object source, Type<?> destinationType, MappingContext mappingContext) {
            try {
                Class<?> rawType = destinationType.getRawType();
                if (rawType.isAssignableFrom(List.class)) {
                    CollectionType listType = mapper.getTypeFactory()
                            .constructCollectionType(List.class, destinationType.getComponentType().getRawType());
                    if (source instanceof String) {
                        return Arrays.asList(Objects.requireNonNull(StringUtils.split(String.valueOf(source), ",")));
                    } else {
                        return mapper.readValue((String) source, listType);
                    }
                } else {
                    return mapper.readValue((String) source, (Class<Object>) rawType);
                }
            } catch (IOException e) {
                log.error("Error while deserializing from string  ", e);
            }
            return null;
        }

        @Override
        public void setMapperFacade(MapperFacade mapper) {

        }
    }

    private class ToStringConverter implements Converter<Object, Object> {
        @Override
        public Type<Object> getAType() {
            return null;
        }

        @Override
        public Type<Object> getBType() {
            return null;
        }

        @Override
        public boolean canConvert(Type<?> sourceType, Type<?> destinationType) {
            return !sourceType.isString() && destinationType.isString();
        }

        @Override
        public Object convert(Object source, Type<?> destinationType, MappingContext mappingContext) {
            try {
                if (source instanceof Enum<?> castEnum) {
                    return castEnum.name();
                } else {
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    return mapper.writeValueAsString(source);
                }
            } catch (JsonProcessingException e) {
                log.error("Error while serializing to String ", e);
            }
            return null;
        }

        @Override
        public void setMapperFacade(MapperFacade mapper) {

        }
    }

    private class DateConverter extends BidirectionalConverter<LocalDate, LocalDateTime> {

        @Override
        public LocalDateTime convertTo(LocalDate source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
            return LocalDateTime.ofInstant(source.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        }

        @Override
        public LocalDate convertFrom(LocalDateTime source, Type<LocalDate> destinationType, MappingContext mappingContext) {
            return source.toLocalDate();
        }
    }

    private static class ToEnumConverter extends CustomConverter<String, Enum> {

        @Override
        public boolean canConvert(Type<?> sourceType, Type<?> destinationType) {
            return sourceType.isString() && destinationType.isEnum();
        }

        @Override
        public Enum convert(String source, Type<? extends Enum> destinationType, MappingContext mappingContext) {
            try {
                return Enum.valueOf(destinationType.getRawType(), source);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }
}
