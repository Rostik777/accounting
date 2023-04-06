package com.cydeo.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtil {
    private final ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) { this.modelMapper = modelMapper; }

    public <T> T convert(Object source, T resultClass) {
        return modelMapper.map(source, (Type) resultClass.getClass());
    }
}
