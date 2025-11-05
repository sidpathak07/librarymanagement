package com.chapter3.librarymanagement.configs;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import java.util.*;
import java.util.stream.Collectors;

public class SafeConvertersGPT {

    // Safely map single object (null-safe)
    public static <S, D> Converter<S, D> safeMap(ModelMapper mapper, Class<D> destinationType) {
        return ctx -> ctx.getSource() == null ? null : mapper.map(ctx.getSource(), destinationType);
    }

    // Safely map list (null-safe)
    public static <S, D> Converter<List<S>, List<D>> safeListMap(ModelMapper mapper, Class<D> destinationType) {
        return ctx -> {
            List<S> source = ctx.getSource();
            if (source == null || source.isEmpty()) return Collections.emptyList();
            return source.stream()
                    .map(item -> mapper.map(item, destinationType))
                    .collect(Collectors.toList());
        };
    }
}

