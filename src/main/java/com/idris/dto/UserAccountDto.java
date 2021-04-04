package com.idris.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.idris.util.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserAccountDto {
    private String title;
    private String firstname;
    private String lastname;
    @NotNull(message = "user email cannot be null or empty")
    @Email(message = "invalid  email entered")
    private String email;
    private String mobile;
    private String password;
    private long role;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_PATTERN, lenient = OptBoolean.FALSE)
    private Date registered;
    private String verified;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_PATTERN, lenient = OptBoolean.FALSE)
    private Date verifiedDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_PATTERN, lenient = OptBoolean.FALSE)
    private Date deactivatedDate;
    private long status;
}
