package com.example.ristu.hackathon_recommendator.model;

/**
 * Created by ristu on 4/9/2016.
 */
public class SubjectDTO {
    private String id;
    private String name;
    private String numberclass;
    private String startcourse;
    private String endcourse;
    private String tblcategory_id;
    private String description;

    public SubjectDTO(String id, String name, String numberclass, String startcourse, String endcourse, String tblcategory_id, String description) {
        this.id = id;
        this.name = name;
        this.numberclass = numberclass;
        this.startcourse = startcourse;
        this.endcourse = endcourse;
        this.tblcategory_id = tblcategory_id;
        this.description = description;
    }
}
