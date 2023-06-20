package com.alisa.amazon.clone.backend.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MappingHelper {
    MapperFactory mapperFactory;

    public <A, B> B map(A src, Class<B> cls) {
        return mapperFactory.getMapperFacade((Class<A>) src.getClass(), cls, false).map(src);
    }

    public <A, B> List<B> mapAsList(Collection<A> src, Class<B> cls) {
        Function<A, BoundMapperFacade<A, B>> facadeFunction = x -> mapperFactory.getMapperFacade((Class<A>) x.getClass(), cls, false);
        return src.stream()
                .map(it -> facadeFunction.apply(it).map(it))
                .collect(Collectors.toList());
    }

    public <A, B> B copy(A src, B dest) {
        return mapperFactory.getMapperFacade((Class<A>) src.getClass(), (Class<B>) dest.getClass(), false).map(src, dest);
    }

    public <A, B> List<B> copyList(List<A> src, List<B> dest) {
        int size = Math.min(src.size(), dest.size());
        int i = 0;
        for (; i < size; i++) {
            copy(src.get(i), dest.get(i));
        }
        return dest;
    }
}
