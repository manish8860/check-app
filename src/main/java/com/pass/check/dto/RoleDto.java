package com.pass.check.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoleDto {

    @NotNull
    private String role_name;
}
