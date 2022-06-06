package com.pass.check.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class PassDto {

    @Pattern(regexp = "[a-zA-Z]{3,20}", message = "Name must contain only alphabets, no space and length within 3 to 20.")
    String name;

    @Pattern(regexp = "[a-zA-z0-9]{10}", message = "Code is alphanumeric and length is 10.")
    String code;
}
