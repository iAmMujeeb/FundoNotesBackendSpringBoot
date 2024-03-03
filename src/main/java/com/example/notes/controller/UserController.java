package com.example.notes.controller;

import com.example.notes.dto.LoginDTO;
import com.example.notes.dto.ResponseDTO;
import com.example.notes.dto.UserDTO;
import com.example.notes.model.UserModel;
import com.example.notes.repository.UserRepository;
import com.example.notes.service.UserService;
import com.example.notes.token.JwtToken;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtToken jwtToken;

    //Adding new user
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createUserData(@Valid @RequestBody UserDTO userDTO){
        UserModel userModel = userService.createUserData(userDTO);
        ResponseDTO responseDTO = new ResponseDTO("User Data Created Successfully",userModel);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //verifying user
    @PutMapping("/verify/{emailId}")
    public ResponseEntity<ResponseDTO> verifyOtp(@PathVariable("emailId") String emailId, @RequestParam(value = "otp") int otp){
        boolean verify = userService.userVerification(emailId, otp);
        ResponseDTO responseDTO = new ResponseDTO("User Verified Successfully", verify);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //user login
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO){
        LoginDTO login = userService.userLogin(loginDTO);
        ResponseDTO responseDTO = new ResponseDTO("Login Successful", login);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //getting user details by token
    @GetMapping("/getuser/{token}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable("token") String token) {
        UserModel userModel = userService.getUserDataById(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", userModel);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

}
