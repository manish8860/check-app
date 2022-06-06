package com.pass.check.converter;

import com.pass.check.dto.PassDto;
import com.pass.check.entity.Pass;

public class EntityDtoConverter {

    public static Pass toEntity(final PassDto passDto) {
        if(passDto==null) return null;
        Pass pass = new Pass();
        pass.setName(passDto.getName());
        pass.setCode(passDto.getCode());
        return  pass;
    }

    public static PassDto toDto(final Pass pass){
        if(pass==null) return null;
        PassDto passDto = new PassDto();
        passDto.setCode(pass.getCode());
        passDto.setName(pass.getName());
        return passDto;
    }

}
