package com.idris.controller;

import com.idris.dto.GenericResponseDto;
import com.idris.dto.UserAccountDto;
import com.idris.error.AppException;
import com.idris.services.UserAccountServices;
import com.idris.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


import javax.validation.Valid;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping(Constants.BASE_URL)
@Validated
public class UserAccountController {

    @Autowired
    private UserAccountServices userAccountServices;


    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDto> findAll(@RequestParam("size") int size, @RequestParam("page") int page) {

        try{

           GenericResponseDto genericResponseDto = new GenericResponseDto(userAccountServices.findAll(page, size));
           genericResponseDto.setResponseCode(Constants.SUCCESSFUL);
           return ResponseEntity.ok(genericResponseDto);
        }
        catch (AppException ex){
            GenericResponseDto genericResponseDto = new GenericResponseDto(getCause(ex).getMessage());
            genericResponseDto.setResponseCode(Constants.FAILED);
            return ResponseEntity.badRequest().body(genericResponseDto);
        }

    }


    @PostMapping(path = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDto> verify(@RequestParam("code") String code) {

        try{

            GenericResponseDto genericResponseDto = new GenericResponseDto(userAccountServices.verify(code));
            genericResponseDto.setResponseCode(Constants.SUCCESSFUL);
            return ResponseEntity.ok(genericResponseDto);
        }
        catch (AppException ex){
            GenericResponseDto genericResponseDto = new GenericResponseDto(getCause(ex).getMessage());
            genericResponseDto.setResponseCode(Constants.FAILED);
            return ResponseEntity.badRequest().body(genericResponseDto);
        }

    }

    @GetMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDto> findById(@PathVariable("id") int id) {

        try{

            GenericResponseDto genericResponseDto = new GenericResponseDto(userAccountServices.findById(id));
            genericResponseDto.setResponseCode(Constants.SUCCESSFUL);
            return ResponseEntity.ok(genericResponseDto);
        }
        catch (AppException ex){
            GenericResponseDto genericResponseDto = new GenericResponseDto(getCause(ex).getMessage());
            genericResponseDto.setResponseCode(Constants.FAILED);
            return ResponseEntity.badRequest().body(genericResponseDto);
        }

    }



    @DeleteMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDto> delById(@PathVariable("id") int id) {

        try{


            GenericResponseDto genericResponseDto = new GenericResponseDto(userAccountServices.delById(id));
            genericResponseDto.setResponseCode(Constants.SUCCESSFUL);
            return ResponseEntity.ok(genericResponseDto);
        }
        catch (AppException ex){
            GenericResponseDto genericResponseDto = new GenericResponseDto(getCause(ex).getMessage());
            genericResponseDto.setResponseCode(Constants.FAILED);
            return ResponseEntity.badRequest().body(genericResponseDto);
        }

    }



    @PostMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDto> create(@Valid @RequestBody UserAccountDto userAccountDto) {

        try{


            GenericResponseDto genericResponseDto = userAccountServices.create(userAccountDto);
            if(Objects.nonNull(genericResponseDto))genericResponseDto.setResponseCode(Constants.SUCCESSFUL);
            return ResponseEntity.ok(genericResponseDto);
        }
        catch (AppException ex){
            GenericResponseDto genericResponseDto = new GenericResponseDto(getCause(ex).getMessage());
            genericResponseDto.setResponseCode(Constants.FAILED);
            return ResponseEntity.badRequest().body(genericResponseDto);
        }

    }



    @PutMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDto> create(@Valid @RequestBody UserAccountDto userAccountDto,@Valid @PathVariable("id") long id) {

        try{

            GenericResponseDto genericResponseDto = userAccountServices.update(userAccountDto, id);
            if(Objects.nonNull(genericResponseDto))genericResponseDto.setResponseCode(Constants.SUCCESSFUL);
            return ResponseEntity.ok(genericResponseDto);
        }
        catch (AppException ex){
            GenericResponseDto genericResponseDto = new GenericResponseDto(getCause(ex).getMessage());
            genericResponseDto.setResponseCode(Constants.FAILED);
            return ResponseEntity.badRequest().body(genericResponseDto);
        }

    }



    public static Throwable getCause(Throwable throwable){
        Throwable cause = null;
        Throwable result = throwable;
        while (null != (cause=throwable.getCause()) && (result != cause)){
            result = cause;
        }

        return result;
    }




    }
