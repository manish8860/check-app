package com.pass.check.unittest.controller;

import com.pass.check.dto.RoleDto;
import com.pass.check.dto.UserDto;
import com.pass.check.security.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class JwtHelper {

    @Autowired
    private JwtUtility jwtUtility;

    public String getJwtTokenForUser() {
        UserDto userDto = getUserDto();
        return jwtUtility.getToken(userDto).getToken();
    }

    private UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername("anonymous");
        userDto.setRoles(getUserRoleDto());
        return userDto;
    }

    private List<RoleDto> getUserRoleDto() {
        RoleDto roleDto = new RoleDto();
        roleDto.setRole_name("ROLE_USER");
        return Collections.singletonList(roleDto);
    }
}
