package com.idris.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idris.dto.GenericResponseDto;
import com.idris.dto.UserAccountDto;
import com.idris.entity.UserAccount;
import com.idris.entity.UserStatus;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    public void given_whenPostUserAccount_thenReturnGenericResponse()
            throws Exception {

        UserAccountDto request = new UserAccountDto();
        request.setVerified("1");
        request.setVerifiedDate(new Date());
        request.setTitle("Mr");
        request.setFirstname("Idah");
        request.setLastname("Idris");
        request.setEmail("idahidris@gmail.com");
        request.setMobile("08030816764");
        request.setStatus(1);
        request.setRole(1);
        request.setDeactivatedDate(new Date());
        request.setRegistered(new Date());
        request.setPassword("password");


        mvc.perform( MockMvcRequestBuilders
                .post(Constants.BASE_URL+"/user")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());



    }


    @Test
    public void negative_given_whenPostUserAccount_thenReturnGenericResponse()
            throws Exception {

        UserAccountDto request = new UserAccountDto();
        request.setVerified("1");
        request.setVerifiedDate(new Date());
        request.setTitle("Mr");
        request.setFirstname("Idah");
        request.setLastname("Idris");
        request.setEmail("idahidrisgmail.com");
        request.setMobile("08030816764");
        request.setStatus(1);
        request.setRole(1);
        request.setDeactivatedDate(new Date());
        request.setRegistered(new Date());
        request.setPassword("password");


        mvc.perform( MockMvcRequestBuilders
                .post(Constants.BASE_URL+"/user")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    public void given_whenPutUserAccount_thenReturnGenericResponse()
            throws Exception {

        UserAccountDto request = new UserAccountDto();
        request.setVerified("1");
        request.setVerifiedDate(new Date());
        request.setTitle("Mr");
        request.setFirstname("Idah");
        request.setLastname("Idris");
        request.setEmail("idahidris@gmail.com");
        request.setMobile("08030816764");
        request.setStatus(1);
        request.setRole(1);
        request.setDeactivatedDate(new Date());
        request.setRegistered(new Date());
        request.setPassword("password");


        mvc.perform( MockMvcRequestBuilders
                .put(Constants.BASE_URL+"/user/1")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void negative_given_whenPutUserAccount_thenReturnGenericResponse()
            throws Exception {

        UserAccountDto request = new UserAccountDto();
        request.setVerified("1");
        request.setVerifiedDate(new Date());
        request.setTitle("Mr");
        request.setFirstname("Idah");
        request.setLastname("Idris");

        request.setMobile("08030816764");
        request.setStatus(1);
        request.setRole(1);
        request.setDeactivatedDate(new Date());
        request.setRegistered(new Date());
        request.setPassword("password");


        mvc.perform( MockMvcRequestBuilders
                .put(Constants.BASE_URL+"/user/1")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void given_whenDelUserAccount_thenReturnGenericResponse()
            throws Exception {

        UserAccount user1 = new UserAccount();
        user1.setId(1);
        UserStatus userStatus = new UserStatus();
        userStatus.setId(3);
        userStatus.setName("DEACTIVATED");
        user1.setStatus(userStatus);

        given(service.delById(1)).willReturn(user1);
        userStatus.setId(1);
        userStatus.setName("REGISTERED");
        user1.setStatus(userStatus);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL+"/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();
        GenericResponseDto responseDto = gson.fromJson(resultString, GenericResponseDto.class);

        assertEquals("00", responseDto.getResponseCode());
        UserAccount expectedUser = gson.fromJson(responseDto.getData().toString(),UserAccount.class);
        assertEquals(user1.getId(), expectedUser.getId());
        assertEquals(user1.getStatus().getName(), expectedUser.getStatus().getName());

    }

    @Test
    public void excep_given_whenDelUserAccount_thenReturnGenericResponse()
            throws Exception {

        UserAccount user1 = new UserAccount();
        user1.setId(1);
        UserStatus userStatus = new UserStatus();
        userStatus.setId(3);
        userStatus.setName("DEACTIVATED");
        user1.setStatus(userStatus);

        given(service.delById(1)).willReturn(user1);
        userStatus.setId(1);
        userStatus.setName("REGISTERED");
        user1.setStatus(userStatus);

         mvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL+"/user/-0.1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }

    @Test
    public void excep_given_whenPutUserAccount_thenReturnGenericResponse()
            throws Exception {

        UserAccountDto request = new UserAccountDto();
        request.setVerified("1");
        request.setVerifiedDate(new Date());
        request.setTitle("Mr");
        request.setFirstname("Idah");
        request.setLastname("Idris");
        request.setEmail("idahidris@gmail.com");
        request.setMobile("08030816764");
        request.setStatus(1);
        request.setRole(1);
        request.setDeactivatedDate(new Date());
        request.setRegistered(new Date());
        request.setPassword("password");


        mvc.perform( MockMvcRequestBuilders
                .put(Constants.BASE_URL+"/user/-0.1")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    public void excep_given_whenPostUserAccount_thenReturnGenericResponse()
            throws Exception {

        UserAccountDto request = new UserAccountDto();
        request.setVerified("1");
        request.setVerifiedDate(new Date());
        request.setTitle("Mr");
        request.setFirstname("Idah");
        request.setLastname("Idris");
        request.setEmail("idahidrisgmail.com");
        request.setMobile("08030816764");
        request.setStatus(-1);
        request.setRole(1);
        request.setDeactivatedDate(new Date());
        request.setRegistered(new Date());
        request.setPassword("password");


        mvc.perform( MockMvcRequestBuilders
                .post(Constants.BASE_URL+"/user")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }



}