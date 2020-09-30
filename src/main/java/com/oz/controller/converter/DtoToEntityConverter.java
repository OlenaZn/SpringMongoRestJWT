package com.oz.controller.converter;

public interface DtoToEntityConverter<E, D> {
    D convert(E dto);
}
