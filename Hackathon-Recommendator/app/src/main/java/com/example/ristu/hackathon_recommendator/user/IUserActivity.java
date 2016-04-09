package com.example.ristu.hackathon_recommendator.user;

import com.example.ristu.hackathon_recommendator.model.SubjectDTO;

/**
 * Created by ristu on 4/9/2016.
 */
public interface IUserActivity {
    void showSubjectDetail(SubjectDTO subjectDTO);

    void showRate(SubjectDTO subjectDTO);

    void rate(SubjectDTO subjectDTO);

    void register(SubjectDTO subjectDTO);
}
