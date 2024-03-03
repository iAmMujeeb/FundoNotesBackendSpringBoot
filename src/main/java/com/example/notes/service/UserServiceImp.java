package com.example.notes.service;

import com.example.notes.dto.LoginDTO;
import com.example.notes.dto.UserDTO;
import com.example.notes.exception.NotesException;
import com.example.notes.model.UserModel;
import com.example.notes.repository.UserRepository;
import com.example.notes.token.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel createUserData(UserDTO userDTO) {
        UserModel userModel = new UserModel(userDTO);
        List<UserModel> list = userRepository.findAllByEmailId(userDTO.emailId);
        if (list.isEmpty()) {
            emailService.sendEmail(userDTO.emailId, "User Created Successfully",
                    "Hi..."+userModel.getFName()+" "+userModel.getLName()+
                            "\nYou have been successfully added to Database!\nYour OTP is - "+ userModel.getOtp()+"\nVerify your account on - http://localhost:3000/verifyuser");
            userRepository.save(userModel);
            return userModel;
        } else {
            throw new NotesException("Email Id Already Present");
        }
    }

    @Override
    public List<UserModel> getAllUserData() {
        return userRepository.findAll();
    }

    @Override
    public UserModel getUserDataById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotesException(" User not found"));
    }

    @Override
    public UserModel updateUserDataById(int id, UserDTO userDTO) {
        UserModel userModel = getUserDataById(id);
        if (userModel != null){
            userModel.setFName(userDTO.fName);
            userModel.setLName(userDTO.lName);
            userModel.setPassword(userDTO.password);
            userModel.setEmailId(userDTO.emailId);
            return userRepository.save(userModel);
        }
        return null;
    }

    @Override
    public String deleteUserDataById(int id) {
        UserModel userModel = getUserDataById(id);
        if (userModel != null) {
            userRepository.deleteById(id);
            return "Deleted Successfully!";
        }
        return "Failed!";
    }

    @Override
    public boolean userVerification(String emailId, int otp) {
        UserModel userModel = userRepository.findByEmailId(emailId);
        if ((userModel != null)&&(userModel.getOtp() == otp)){
            userModel.setVerify(true);
            userRepository.save(userModel);
            return userModel.getVerify();
        }
        userModel.setVerify(false);
        userRepository.save(userModel);
        return false;
    }

    @Override
    public LoginDTO userLogin(LoginDTO loginDTO) {
        UserModel userModel = userRepository.findByEmailIdAndPassword(loginDTO.emailId, loginDTO.password);
        if (userModel.getVerify()) {
            if (userModel != null) {
                String token = jwtToken.createToken(userModel.getUserId());
                loginDTO.token = token;
                return loginDTO;
            }
            return null;
        } else return null;
    }

    @Override
    public UserModel resetPassword(String token, String password) {
        int userId = jwtToken.decodeToken(token);
        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new NotesException("User not found"));
        if (userModel != null){
            userModel.setPassword(password);
            return userRepository.save(userModel);
        }
        return null;
    }

    @Override
    public void forgetPassword(String emailId) {
        UserModel userModel = userRepository.findByEmailId(emailId);
        if (userModel != null){
            userModel.setOtp((int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000));
            userModel.setVerify(false);
            emailService.sendEmail(userModel.getEmailId(), "OTP for user verification",
                    "Hi..."+userModel.getFName()+" "+userModel.getLName()+
                            "\nYour otp is - "+ userModel.getOtp());
            userModel.setVerify(false);
            userRepository.save(userModel);
        }else {
            throw new NotesException("User not found");
        }
    }

    @Override
    public UserModel getUserDataById(String token) {
        int userId = jwtToken.decodeToken(token);
        return userRepository.findById(userId).orElseThrow(() -> new NotesException("User Data Not Found"));
    }

}
