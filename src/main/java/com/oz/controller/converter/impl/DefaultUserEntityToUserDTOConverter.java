package com.oz.controller.converter.impl;

import com.oz.dto.UserDTO;
import com.oz.model.User;
import org.springframework.stereotype.Component;

@Component
public  class DefaultUserEntityToUserDTOConverter extends AbstractUserDtoToUserEntityConverter<User, UserDTO> {

    @Override
    public UserDTO convert(final User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
