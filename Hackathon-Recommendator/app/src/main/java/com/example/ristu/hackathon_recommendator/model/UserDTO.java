package com.example.ristu.hackathon_recommendator.model;

/**
 * Created by ristu on 4/10/2016.
 */
public class UserDTO {
    public String name;
    public String rate;
    public UserDTO (String name, String rate) {
        this.rate = rate;
        this.name = name;
    }
}
