package com.example.ristu.hackathon_recommendator.util;

import com.example.ristu.hackathon_recommendator.model.SubjectDTO;
import com.example.ristu.hackathon_recommendator.model.UserDTO;

import java.util.List;

/**
 * Created by ristu on 4/9/2016.
 */
public class AppStorage {

    private static AppStorage mInstance = null;

    public List<SubjectDTO> subjectDTOs;
    public List<UserDTO> subjectDTOs2;

    private AppStorage() {
    }

    public static AppStorage getInstance() {
        if (mInstance == null) {
            mInstance = new AppStorage();
        }
        return mInstance;
    }
}
