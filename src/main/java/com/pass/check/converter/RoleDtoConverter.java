package com.pass.check.converter;

import com.pass.check.dto.RoleDto;
import com.pass.check.dto.UserDto;
import com.pass.check.entity.Role;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RoleDtoConverter {

    public static List<Role> getRoles(List<RoleDto> roleDtos) {
        if(roleDtos==null) return Collections.emptyList();
        return roleDtos.stream().map(roleDto -> {
            Role role = new Role();
            role.setRoleName(roleDto.getRole_name());
            return role;
        }).collect(Collectors.toList());
    }

    public static RoleDto getRoleDto(Role role) {
        if(role==null) return null;
        RoleDto roleDto = new RoleDto();
        roleDto.setRole_name(role.getRoleName());
        return roleDto;
    }
}
