package com.oz.controller.converter.impl;

import com.oz.controller.converter.DtoToEntityConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractUserDtoToUserEntityConverter <E, D> implements DtoToEntityConverter<E, D> {

    @Autowired
    protected ModelMapper modelMapper;

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}
