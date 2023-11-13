package com.chen.srs;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Person.class)
public class Person_ {

    public static volatile SingularAttribute<Person, String> id;
    public static volatile SingularAttribute<Person, String> password;

    public static volatile SingularAttribute<Person, String> firstName;
    public static volatile SingularAttribute<Person, String> lastName;

    public static volatile SingularAttribute<Person, String> ssn;
    public static volatile SingularAttribute<Person, String> address;

}
