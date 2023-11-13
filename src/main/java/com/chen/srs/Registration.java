package com.chen.srs;


import javax.persistence.*;

@Entity
@Table(name="REGISTRAR")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="COURSE_ID",nullable = false)
    private long courseId;
    @Column(name="NUMBER_REGISTERED",nullable = false)
    private long numberRegistered;

    @ManyToOne
    private Student student;

    public Registration() {}
    public Registration(Long courseId, long numberRegistered) {
        this.courseId=courseId;
        this.numberRegistered=numberRegistered;
    }
    public Registration(Long courseId, long numberRegistered,Student student) {
        this.courseId=courseId;
        this.numberRegistered=numberRegistered;
        this.student=student;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getNumberRegistered() {
        return numberRegistered;
    }

    public void setNumberRegistered(long numberRegistered) {
        this.numberRegistered = numberRegistered;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id='" + id + '\'' +
                ", courseId='" + courseId + '\'' +
                ", numberRegistered='" + numberRegistered + '\'' +
                ", student='" + student.getLastName() +
                '}';
    }
}
