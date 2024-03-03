package com.example.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserDTO {

    public String fName;
    public String lName;
    public String password;
    public String emailId;

}
