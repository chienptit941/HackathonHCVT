package com.example.ristu.hackathon_recommendator.subject;

import com.example.ristu.hackathon_recommendator.model.SubjectDTO;

/**
 * Created by ristu on 4/9/2016.
 */
public interface ISubjectActivity {
    void showSubjectDetail(SubjectDTO subjectDTO);

    void showRate(SubjectDTO subjectDTO);

    void rate(SubjectDTO subjectDTO);

    void register(SubjectDTO subjectDTO);
}
