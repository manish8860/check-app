package com.pass.check.converter;

import com.pass.check.dto.RoleDto;
import com.pass.check.dto.UserDto;
import com.pass.check.entity.User;

import java.util.List;
import java.util.Objects;

public class UserDtoConverter {
    public static User getUser(UserDto userDto) {
        if(userDto==null) return null;
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static UserDto getUserDto(User user) {
        if(user==null) return null;
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        List<RoleDto> roleDtoList = user.getRoles().stream().map(role -> {
            RoleDto roleDto = new RoleDto();
            roleDto.setRole_name(role.getRoleName());
            return roleDto;
        }).filter(Objects::nonNull).toList();
        userDto.setRoles(roleDtoList);
        return userDto;
    }
}
