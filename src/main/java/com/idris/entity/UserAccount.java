package com.idris.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idris.util.JsonDateSerializer;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="user_account")
@Getter
@Setter
public class UserAccount implements Serializable {
    private static final long serialVersionUID = 2405172041950455807L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String firstname;
    private String lastname;
    private String email;
    private String mobile;
    private String password;
    @ManyToOne
    @JoinColumn(name ="role")
    private UserRole role;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date registered;
    private boolean verified;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date verifiedDate;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date deactivatedDate;
    @ManyToOne
    @JoinColumn(name ="status")
    private UserStatus status;

}
