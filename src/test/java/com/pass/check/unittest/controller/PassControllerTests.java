package com.pass.check.unittest.controller;

import com.google.gson.Gson;
import com.pass.check.controller.PassController;
import com.pass.check.dto.PassDto;
import com.pass.check.dto.UserDto;
import com.pass.check.security.JwtUtility;
import com.pass.check.service.PassService;
import com.pass.check.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(PassController.class)
public class PassControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PassService passService;

    @MockBean
    private JwtUtility jwtUtility;

    @MockBean
    private UserService userService;

    private final String customToken = "custom_token";

    @Test
    public void showAllPassSuccessfully() throws Exception {
        when(passService.showAllPass()).thenReturn(createListOfPassDto());
        MvcResult mockResult = this.mockMvc.perform(get("/v1/allpass"))
                .andReturn();
        assertEquals(200, mockResult.getResponse().getStatus());
    }

    @Test
    public void addNewPassSuccessfullly() throws Exception {
        final PassDto passDto = preparePassDto();
        Gson gson = new Gson();
        doNothing().when(passService).addNewPass(passDto);
        when(jwtUtility.verifyToken(customToken)).thenReturn(true);
        when(userService.getUserFromToken(customToken)).thenReturn(new UserDto());
        MvcResult mockResult = this.mockMvc.perform(post("/v1/addnewpass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer " + customToken)
                        .content(gson.toJson(passDto)))
                .andReturn();
        assertEquals(201, mockResult.getResponse().getStatus());
    }

    @Test
    public void addNewPassUnSuccessfulllyWithNoTokenOrInvalidToken() throws Exception {
        final PassDto passDto = preparePassDto();
        Gson gson = new Gson();
        doNothing().when(passService).addNewPass(passDto);
        when(jwtUtility.verifyToken(customToken)).thenReturn(false);
        when(userService.getUserFromToken(customToken)).thenReturn(new UserDto());
        MvcResult mockResult = this.mockMvc.perform(post("/v1/addnewpass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer " + customToken)
                        .content(gson.toJson(passDto)))
                .andReturn();

        assertEquals(401, mockResult.getResponse().getStatus());

        when(jwtUtility.verifyToken(customToken)).thenReturn(false);
        when(userService.getUserFromToken(customToken)).thenReturn(new UserDto());
        mockResult = this.mockMvc.perform(post("/v1/addnewpass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(passDto)))
                .andReturn();

        assertEquals(401, mockResult.getResponse().getStatus());
    }

    @Test
    public void addNewPassThrowsExceptionForDataNotFormatted() throws Exception {
        final PassDto passDto = prepareWrongPassDto();
        Gson gson = new Gson();
        doNothing().when(passService).addNewPass(passDto);
        when(jwtUtility.verifyToken(customToken)).thenReturn(true);
        when(userService.getUserFromToken(customToken)).thenReturn(new UserDto());
        MvcResult mockResult = this.mockMvc.perform(post("/v1/addnewpass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer " + customToken)
                        .content(gson.toJson(passDto)))
                .andReturn();
        assertEquals(417, mockResult.getResponse().getStatus());
    }

    @Test
    public void addMultiplePassUnSuccessfulllyWithNoTokenOrInvalidToken() throws Exception {
        final List<PassDto> passDtoList = createListOfPassDto();
        Gson gson = new Gson();
        doNothing().when(passService).addMultiplePass(passDtoList);
        when(jwtUtility.verifyToken(customToken)).thenReturn(false);
        when(userService.getUserFromToken(customToken)).thenReturn(new UserDto());
        MvcResult mockResult = this.mockMvc.perform(post("/v1/addmultiplepass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer " + customToken)
                        .content(gson.toJson(passDtoList)))
                .andReturn();
        assertEquals(401, mockResult.getResponse().getStatus());

        when(jwtUtility.verifyToken(customToken)).thenReturn(true);
        mockResult = this.mockMvc.perform(post("/v1/addmultiplepass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(passDtoList)))
                .andReturn();
        assertEquals(401, mockResult.getResponse().getStatus());
    }

    @Test
    public void addMultiplePassSuccessfullly() throws Exception {
        final List<PassDto> passDtoList = createListOfPassDto();
        Gson gson = new Gson();
        doNothing().when(passService).addMultiplePass(passDtoList);
        when(jwtUtility.verifyToken(customToken)).thenReturn(true);
        when(userService.getUserFromToken(customToken)).thenReturn(new UserDto());
        MvcResult mockResult = this.mockMvc.perform(post("/v1/addmultiplepass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer " + customToken)
                        .content(gson.toJson(passDtoList)))
                .andReturn();
        assertEquals(201, mockResult.getResponse().getStatus());
    }

    private PassDto prepareWrongPassDto() {
        PassDto passDto = new PassDto();
        passDto.setName("abcde12345");
        passDto.setCode("abcde");
        return passDto;
    }

    private List<PassDto> prepareWrongPassDtoList() {
        return Collections.singletonList(prepareWrongPassDto());
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
        passDtoList.add(preparePassDto());
        return passDtoList;
    }
}
