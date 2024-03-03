package com.example.notes.service;

import com.example.notes.dto.LoginDTO;
import com.example.notes.dto.UserDTO;
import com.example.notes.model.UserModel;

import java.util.List;

public interface UserService {

    UserModel createUserData(UserDTO userDTO);

    List<UserModel> getAllUserData();

    UserModel getUserDataById(int id);

    UserModel updateUserDataById(int id, UserDTO bookStoreDTO);

    String deleteUserDataById(int id);

    boolean userVerification(String emailId, int otp);

    LoginDTO userLogin(LoginDTO loginDTO);

    UserModel resetPassword(String token, String password);

    void forgetPassword(String emailId);

    UserModel getUserDataById(String token);

}
