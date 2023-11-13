package com.chen.srs;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.*;

@Entity
@Table(name="Person")
@Inheritance(strategy=JOINED)
@DiscriminatorColumn(name="USER_TYPE", discriminatorType= STRING, length=255)
public abstract class Person {

    @Id
    @Column(name = "USER_ID")
    protected String id;
    @Column(name = "PASSWORD",nullable = false)
    protected String password;
    @Transient
    protected String repeatPassword;
    @Column(name="FIRST_NAME",nullable = false)
    protected String firstName;
    @Column(name="LAST_NAME")
    protected String lastName;
    @Column(name="SSN")
    protected String ssn;
    @Column(name="ADDRESS")
    protected String address;
    @Transient
    protected String city;
    @Transient
    protected String state;
    @Transient
    protected String zipCode;
    @Transient
    protected Map<String, String> errorsFormA = new HashMap<>();
    @Transient
    protected Map<String, String> errorsFormB = new HashMap<>();

    public Person(){}

    public Person(String id, String firstName, String lastName, String ssn, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.address = address;
    }

    public Person(String id, String password, String firstName, String lastName, String ssn, String address) {
        this.id = id;
        this.password=password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.address = address;
    }

    public Person(String id, String password, String repeatPassword, String firstName, String lastName, String ssn, String address, String city, String state, String zipCode) {
        this.id = id;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Person(String id, String password, String repeatPassword, String firstName, String lastName, String ssn, String address, String city, String state, String zipCode, Map<String, String> errorsFormA, Map<String, String> errorsFormB) {
        this.id = id;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.errorsFormA = errorsFormA;
        this.errorsFormB = errorsFormB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Map<String, String> getErrorsFormA() {
        return errorsFormA;
    }

    public void setErrorsFormA(Map<String, String> errorsFormA) {
        this.errorsFormA = errorsFormA;
    }

    public Map<String, String> getErrorsFormB() {
        return errorsFormB;
    }

    public void setErrorsFormB(Map<String, String> errorsFormB) {
        this.errorsFormB = errorsFormB;
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
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", errorsFormA=" + errorsFormA +
                ", errorsFormB=" + errorsFormB +
                '}';
    }

}
