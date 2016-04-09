package com.example.ristu.hackathon_recommendator.model;

/**
 * Created by ristu on 4/9/2016.
 */
public class SubjectDTO {
    public String id;
    public String name;
    public String numberclass;
    public String startcourse;
    public String endcourse;
    public String category;
    public String description;

    public boolean isRegister;

    public SubjectDTO(String id, String name, String numberclass, String startcourse, String endcourse, String category, String description) {
        this.id = id;
        this.name = name;
        this.numberclass = numberclass;
        this.startcourse = startcourse;
        this.endcourse = endcourse;
        this.category = category;
        this.description = description;
    }
    public SubjectDTO (int id, String name) {
        this.id = id + "";
        this.name = name;
        this.numberclass = "aaa";
        this.startcourse = "aaa";
        this.endcourse = "aaa";
        this.category = "aaa";
        this.description = "aaa";
    }
}
