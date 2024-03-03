package com.example.notes.repository;

import com.example.notes.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    @Query
    UserModel findByEmailId(String emailId);

    @Query
    List<UserModel> findAllByEmailId(String emailId);

    @Query(value = "select * from user_data where email_id = :emailId and password = :password", nativeQuery = true)
    UserModel findByEmailIdAndPassword(String emailId, String password);

}
