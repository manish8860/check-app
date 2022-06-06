package com.pass.check.integrationtest.dao;

import com.pass.check.dto.PassDto;
import com.pass.check.service.PassService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
public class PassServiceTests {

    @Autowired
    private PassService passService;

    @Test
    public void addNewPass(){
        final PassDto passDto = preparePassDto();
        passService.addNewPass(passDto);
    }

    @Test
    public void showAllPass(){
        final List<PassDto> passDtoList = passService.showAllPass();
        assertEquals("msr1234567", passDtoList.get(0).getCode());
    }

    @Test
    public void addMultiplePass(){
        final List<PassDto> passDtoList = createListOfPassDto();
        passService.addMultiplePass(passDtoList);
    }

    private PassDto preparePassDto() {
        PassDto passDto = new PassDto();
        passDto.setName("testUser");
        passDto.setCode("abcde12345");
        return passDto;
    }

    private List<PassDto> createListOfPassDto() {
        List<PassDto> passDtoList = new ArrayList<>();
        passDtoList.add(preparePassDto());
        PassDto passDto = preparePassDto();
        passDto.setCode("12345abcde");
        passDtoList.add(passDto);
        return passDtoList;
    }

}
