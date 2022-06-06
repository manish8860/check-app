package com.pass.check.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class UserDto {

    @NotNull
    @Pattern(regexp = "[a-zA-Z]{5,20}", message = "User name should only have alphabet, length between 5 to 20.")
    private String username;

    @NotNull
    private String password;

    private List<RoleDto> roles;
}
