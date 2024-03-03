package com.example.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {

    public String emailId;
    public String password;
    public String token;

}
