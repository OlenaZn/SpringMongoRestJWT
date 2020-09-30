package com.oz.controller.converter.impl;

import com.oz.controller.converter.EntityToDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractUserEntityToUserDtoConverter<E, D> implements EntityToDtoConverter<E, D> {

    @Autowired
    protected ModelMapper modelMapper;

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}
