package com.pass.check.service;

import com.pass.check.converter.EntityDtoConverter;
import com.pass.check.dao.PassCheckerDao;
import com.pass.check.dto.PassDto;
import com.pass.check.entity.Pass;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class PassService {

    @Autowired
    private PassCheckerDao passCheckerDao;

    public void addNewPass(final PassDto passDto){
        log.debug("Saving information of pass.");
        Pass pass = EntityDtoConverter.toEntity(passDto);
        passCheckerDao.save(pass);
    }

    public List<PassDto> showAllPass(){
        log.debug("Retrieving all pass details.");
        List<PassDto> allPass = new ArrayList<>();
        passCheckerDao.findAll().forEach(p -> allPass.add(EntityDtoConverter.toDto(p)));
        return allPass;
    }

    public void addMultiplePass(List<PassDto> passDto) {
        log.debug("Saving multiple pass information.");
        List<Pass> pass = new ArrayList<>();
        passDto.forEach(p -> pass.add(EntityDtoConverter.toEntity(p)));
        passCheckerDao.saveAll(pass);
    }
}
