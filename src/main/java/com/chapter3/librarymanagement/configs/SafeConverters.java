package com.chapter3.librarymanagement.configs;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SafeConverters {
    public static <S,D> Converter<List<S>,List<D>> safeListMap(ModelMapper modelMapper, Class<D> destination){
        return
         ctx->{
             List<S> source = ctx.getSource();
             if (source == null || source.isEmpty()) return Collections.emptyList();
             return source.stream().map(item->modelMapper.map(item,destination)).collect(Collectors.toList());
         };
    }

    public static <S,D> Converter<S,D> safeMap(ModelMapper modelMapper, Class<D> destination){
        return ctx->{
            if(ctx.getSource()==null) return null;
            return modelMapper.map(ctx.getSource(),destination);
        };
    }
}
