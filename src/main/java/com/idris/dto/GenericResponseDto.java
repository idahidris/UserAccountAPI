package com.idris.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class GenericResponseDto {

    private String responseCode;
    private Set<String> errors;
    private Object data;

    public GenericResponseDto(Date responseTime, String respCode, String error) {
        this.responseCode = respCode;
        this.errors = new HashSet<>(Arrays.asList(error));

    }

    public GenericResponseDto(Date responseTime, String respCode, List<String> error) {
        this.responseCode = respCode;
        this.errors = new HashSet<>(error);
    }

    public GenericResponseDto(Object data) {
        this.data = data;
    }

    public GenericResponseDto(String error) {

        this.errors = new HashSet<>(Arrays.asList(error));

    }

}