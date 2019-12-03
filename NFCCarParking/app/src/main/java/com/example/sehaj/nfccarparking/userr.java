package com.example.sehaj.nfccarparking;

/**
 * Created by Sehaj on 23-02-2018.
 */

public class userr {
    String name;
    String email;
    int number;
    String address;


    public userr(){

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public userr(String name, String email, int number, String address){
        this.email = email;
        this.number = number;
        this.name = name;
        this.address = address;

    }
}
