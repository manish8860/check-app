package com.pass.check.controller;

import com.pass.check.dto.PassDto;
import com.pass.check.service.PassService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Log4j2
public class PassController {

    @Autowired
    private PassService passService;

    @GetMapping("/v1/allpass")
    public List<PassDto> getAllPass(){
        return passService.showAllPass();
    }

    @PostMapping("/v1/addnewpass")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewPass(@RequestBody @Valid PassDto passDto,
                           Errors error){
        log.info("Adding new pass.");
        if(error.hasErrors()) throw new RuntimeException("Input data is not well formatted");
        passService.addNewPass(passDto);
    }

    @PostMapping("/v1/addmultiplepass")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMultiplePass(@RequestBody @Valid List<PassDto> passDto,
                           Errors error){
        log.info("Multiple pass addition.");
        if(error.hasErrors()) throw new RuntimeException("Input data is not well formatted");
        log.debug("Adding multiple pass information if one go.");
        passService.addMultiplePass(passDto);
    }
}
