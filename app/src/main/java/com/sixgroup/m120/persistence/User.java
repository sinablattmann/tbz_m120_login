package com.sixgroup.m120.persistence;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    //id with autoincrement
    @PrimaryKey(autoGenerate = true)
    private int id;

    //vorname is called firstname in the database
    @ColumnInfo(name="firstname")
    private String firstName;

    //nachname is called lastname in the database
    @ColumnInfo(name="lastname")
    private String lastName;

    private String email;


    //passwort is called password in the database
    @ColumnInfo(name="password")
    private String password;

    //constructor
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}