package com.example.sehaj.nfccarparking;

/**
 * Created by Sehaj on 23-01-2018.
 */



public class user {
    public String name;
    public String email;
    public String mobile;


    public user(){

    }

    public user(String name,String email,String mobile){
        this.email = email;
        this.mobile = mobile;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}