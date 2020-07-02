package com.sixgroup.m120.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//creating a User Data Object
@Dao
public interface UserDao {

    //writing query for getting specific user
    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    User getByEmail(String email);

    //writing query for inserting all users
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id = :apprenticeId")
    User getById(long apprenticeId);
}