package com.dvb.forum.dto.authentication;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String email;
    private String password;

}