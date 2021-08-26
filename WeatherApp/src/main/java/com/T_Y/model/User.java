package com.T_Y.model;

import com.T_Y.controller.UserManagement;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class User implements Serializable {
    private String id;
    private String name;
    private char[] pwd;
    private String dateOfBirth;

    public User(String id, String name, char[] pwd, String dateOfBirth) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.dateOfBirth = dateOfBirth;
    }

    public User(String id, char[] pwd) {
        this.id = id;
        this.pwd = pwd;
        this.dateOfBirth = LocalDate.of(1900, 1, 1).toString();
        this.name="tempName";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getPwd() {
        return pwd;
    }

    public void setPwd(char[] pwd) {
        this.pwd = pwd;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}