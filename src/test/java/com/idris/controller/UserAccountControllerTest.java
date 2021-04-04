package com.idris.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idris.dto.GenericResponseDto;
import com.idris.entity.UserAccount;
import com.idris.services.UserAccountServices;
import com.idris.util.Constants;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserAccountController.class)
public class UserAccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserAccountServices service;

    private Gson gson = new GsonBuilder().setDateFormat(Constants.DATE_PATTERN).create();

    @BeforeEach
    void setUp() {

    }


    @Test
    public void given_whenGetUserAccounts_thenReturnGenericResponse()
            throws Exception {

        UserAccount user1 = new UserAccount();
        user1.setId(1);

        List<UserAccount> allUsers = Arrays.asList(user1);

        given(service.findAll(0,100)).willReturn(allUsers);

        ResultActions result = mvc.perform(get(Constants.BASE_URL+"/users?page=0&size=100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();
        GenericResponseDto responseDto = gson.fromJson(resultString, GenericResponseDto.class);
        List actualUserAccounts  = (List)responseDto.getData();
        assertEquals("00", responseDto.getResponseCode());
        assertEquals(1, actualUserAccounts.size());
        UserAccount expectedUser = gson.fromJson(actualUserAccounts.get(0).toString(),UserAccount.class);
        assertEquals(user1.getId(), expectedUser.getId());

    }

    @Test
    public void given_whenGetUserAccount_thenReturnGenericResponse()
            throws Exception {

        UserAccount user1 = new UserAccount();
        user1.setId(1);

        given(service.findById(1)).willReturn(user1);

        ResultActions result = mvc.perform(get(Constants.BASE_URL+"/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();
        GenericResponseDto responseDto = gson.fromJson(resultString, GenericResponseDto.class);

        assertEquals("00", responseDto.getResponseCode());
        UserAccount expectedUser = gson.fromJson(responseDto.getData().toString(),UserAccount.class);
        assertEquals(user1.getId(), expectedUser.getId());

    }


}