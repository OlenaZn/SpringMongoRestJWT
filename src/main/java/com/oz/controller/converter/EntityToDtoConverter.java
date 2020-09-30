package com.oz.controller.converter;

public interface EntityToDtoConverter<E, D> {
    D convert(E entity);
}
