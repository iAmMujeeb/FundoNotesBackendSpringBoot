package com.example.notes.model;

import com.example.notes.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_data")
@ToString
public class UserModel {

    @Id
    @GeneratedValue
    private int userId;

    private String fName;
    private String lName;
    private String password;
    private String emailId;
    private Boolean verify;
    private int otp;

    public UserModel(UserDTO userDTO) {
        this.fName = userDTO.fName;
        this.lName = userDTO.lName;
        this.emailId = userDTO.emailId;
        this.password = userDTO.password;
        this.otp = (int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000);
    }

    public UserModel(int userId, String password) {
        this.userId = userId;
        this.password = password;
    }

}
