package com.chen.srs;

import javax.persistence.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.JOINED;

@Entity
@Table(name="STUDENT")
@DiscriminatorValue("STUDENT")
public class Student extends Person {

    @Column(name="EMAIL",nullable = false,unique = true)
    private String email;

    @Column(name="level",nullable=false)
    private String level;
    
    @Column(name="level",nullable=false)
    private Date registrationDate;

    @OneToMany(mappedBy="student", orphanRemoval=true)
    private List<Registration> courseRegistrations;

    public Student(){
        this.level="undergraduate";
    }

    public Student(String id, String firstName, String lastName, String ssn, String email, String address, String level) {
        super(id,firstName,lastName,ssn,address);
        this.email = email;
        this.level=level;
    }

    public Student(String id, String password, String firstName, String lastName, String ssn, String email, String address, String level) {
        super(id,password,firstName,lastName,ssn,address);
        this.email = email;
        this.level=level;
    }

    public Student(String id, String password, String repeatPassword, String firstName, String lastName, String ssn, String email, String address, String city, String state, String zipCode, String level) {
        super(id,password,repeatPassword,firstName,lastName,ssn,address,city,state,zipCode);
        this.email = email;
        this.level=level;
    }

    public Student(String id, String password, String repeatPassword, String firstName, String lastName, String ssn, String email, String address, String city, String state, String zipCode, String level, Map<String, String> errorsFormA, Map<String, String> errorsFormB) {
        super(id,password,repeatPassword,firstName,lastName,ssn,address,city,state,zipCode,errorsFormA,errorsFormB);
        this.email = email;
        this.level=level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    
    public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ssn='" + ssn + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", level='" + level + '\'' +
                ", errorsFormA=" + errorsFormA +
                ", errorsFormB=" + errorsFormB +
                '}';
    }

}
