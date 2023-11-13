package com.chen.srs;

import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="COURSES")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID")
    private long courseId;

    @Column(name="COURSE_TITLE",nullable=false,unique = true)
    private String courseTitle;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval=true)
    private List<Registration> registrations = new ArrayList<Registration>();

    public Course() {}

    public Course(String courseTitle) {
        this.courseTitle=courseTitle;
    }


    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }
}
