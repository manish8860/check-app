package com.pass.check.unittest.service;

import com.pass.check.converter.EntityDtoConverter;
import com.pass.check.dao.PassCheckerDao;
import com.pass.check.dto.PassDto;
import com.pass.check.entity.Pass;
import com.pass.check.service.PassService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PassServiceTests {

    @InjectMocks
    private PassService passService;

    @Mock
    private PassCheckerDao passCheckerDao;

    static MockedStatic<EntityDtoConverter> entityDtoConverterMockedStatic;

    @BeforeAll
    public static void init(){
        entityDtoConverterMockedStatic = mockStatic(EntityDtoConverter.class);
    }

    @AfterAll
    public static void dest(){
        entityDtoConverterMockedStatic.close();
    }

    @Test
    public void addNewPass(){
        final Pass pass = preparePass();
        final PassDto passDto = preparePassDto();
        entityDtoConverterMockedStatic.when(() -> EntityDtoConverter.toEntity(passDto)).thenReturn(pass);

        passService.addNewPass(passDto);
        verify(passCheckerDao, times(1)).save(pass);
    }

    @Test
    public void showAllPass(){
        final Pass pass = preparePass();
        final PassDto passDto = preparePassDto();
        final List<Pass> passList = createListOfPass();
        final List<PassDto> passDtoList = createListOfPassDto();

        when(passCheckerDao.findAll()).thenReturn(passList);
        entityDtoConverterMockedStatic.when(() -> EntityDtoConverter.toDto(pass)).thenReturn(passDto);

        assertIterableEquals(passDtoList, passService.showAllPass());
    }

    @Test
    public void addMultiplePass(){
        final Pass pass = preparePass();
        final PassDto passDto = preparePassDto();
        final List<Pass> passList = createListOfPass();
        final List<PassDto> passDtoList = createListOfPassDto();

        entityDtoConverterMockedStatic.when(() -> EntityDtoConverter.toEntity(passDto)).thenReturn(pass);

        passService.addMultiplePass(passDtoList);
        verify(passCheckerDao, times(1)).saveAll(passList);
    }

    @Test
    public void addNewPassFailureTest(){
        final Pass pass = preparePass();
        final PassDto passDto = preparePassDto();
        entityDtoConverterMockedStatic.when(() -> EntityDtoConverter.toEntity(passDto)).thenReturn(pass);
        when(passCheckerDao.save(pass)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> passService.addNewPass(passDto));
    }

    @Test
    public void showAllPassFailureTest(){
        when(passCheckerDao.findAll()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> passService.showAllPass());
    }

    @Test
    public void addMultiplePassFailureTest(){
        final Pass pass = preparePass();
        final PassDto passDto = preparePassDto();
        final List<Pass> passList = createListOfPass();
        final List<PassDto> passDtoList = createListOfPassDto();

        entityDtoConverterMockedStatic.when(() -> EntityDtoConverter.toEntity(passDto)).thenReturn(pass);
        when(passCheckerDao.saveAll(passList)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> passService.addMultiplePass(passDtoList));
    }

    private PassDto preparePassDto() {
        PassDto passDto = new PassDto();
        passDto.setName("testUser");
        passDto.setCode("abcde12345");
        return passDto;
    }

    private Pass preparePass() {
        Pass pass = new Pass();
        pass.setName("testUser");
        pass.setCode("abcde12345");
        return pass;
    }

    private List<PassDto> createListOfPassDto() {
        List<PassDto> passDtoList = new ArrayList<>();
        passDtoList.add(preparePassDto());
        passDtoList.add(preparePassDto());
        return passDtoList;
    }

    private List<Pass> createListOfPass() {
        List<Pass> passList = new ArrayList<>();
        passList.add(preparePass());
        passList.add(preparePass());
        return passList;
    }
}
