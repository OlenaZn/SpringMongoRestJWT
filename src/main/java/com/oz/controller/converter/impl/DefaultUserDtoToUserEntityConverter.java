package com.oz.controller.converter.impl;

import com.oz.dto.UserDTO;
import com.oz.model.User;
import org.springframework.stereotype.Component;

@Component
public  class DefaultUserDtoToUserEntityConverter extends AbstractUserDtoToUserEntityConverter<UserDTO, User> {

    @Override
    public User convert(final UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }
}
