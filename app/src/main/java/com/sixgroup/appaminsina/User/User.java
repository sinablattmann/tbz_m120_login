package com.sixgroup.appaminsina.User;


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
    private String vorname;

    //nachname is called lastname in the database
    @ColumnInfo(name="lastname")
    private String nachname;

    private String email;

    private byte[] image;

    //passwort is called password in the database
    @ColumnInfo(name="password")
    private String passwort;

    //constructor
    public User(String vorname, String nachname, String email, String passwort, byte[] image) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.passwort = passwort;
        this.image = image;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}