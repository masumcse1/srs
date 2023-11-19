package com.chen.srs;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public  class PersonDao {
	

	@PersistenceContext
	private EntityManager em;

    public static void createTable(Connection conn) throws SQLException {
        String query="create table if not exists STUDENT(\n" +
                " USER_ID varchar(255) primary key,\n" +
                " Password varchar(255) not null,\n" +
                " First_Name varchar(255) not null,\n" +
                " Last_Name varchar(255),\n" +
                " SSN varchar(255),\n" +
                " Email varchar(255) unique not null,\n" +
                " Address varchar(255)\n" +
                ");";
        Statement stmt=conn.createStatement();
        stmt.execute(query);
    }

    
    
    public  Person login(String userId, String password) throws SQLException, NamingException, NoSuchAlgorithmException, InvalidKeySpecException {
        Person person=null;
        try{
            person=(Person)em.createQuery("SELECT p FROM  Person p WHERE p.id=:userId").setParameter("userId",userId).getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
        boolean authenticate=MyDataSource.checkPasswordHash(person.password,password);
        if(authenticate){
            return person;
        }
        else{
            return null;
        }
    }

    public  boolean isStillLoggedIn(String userId, String password) throws SQLException, NamingException {
        Person person=(Person)em.createQuery("SELECT p FROM  Person p WHERE p.id=:userId").setParameter("userId",userId).getSingleResult();
        if(person!=null){
            boolean authenticate=person.getPassword().equals(password);
            if(authenticate){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public  boolean isRegistered(String userId) throws SQLException, NamingException {
        Person person=(Person)em.createQuery("SELECT p FROM  Person p WHERE p.id=:userId").setParameter("userId",userId).getSingleResult();
        return person!=null;
    }

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public  boolean save(Person person) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, NamingException {
        
        String fullAddress=person.address+" "+person.city+", "+person.state+" "+person.zipCode;
        person.setAddress(fullAddress);
        //hash and salt password
        byte[] salt=MyDataSource.getSalt();
        String saltString= Arrays.toString(salt);
        saltString=saltString.substring(1,saltString.length()-1);
        String password_hash= MyDataSource.getPasswordHash(salt,person.password);
        person.setPassword(password_hash);
        try{
            em.persist(person);
           
            return true;
        }
        catch(PersistenceException e){
            System.out.println("CONSTRAINT VIOLATION: "+e.getMessage());
         
            return false;
        }
    }

    public  List<Person> getPersonsByLastName(String lastName){
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> person = cq.from(Person.class);
        EntityType<Person> Person_ = person.getModel();
        cq.where(person.get(Person_.getAttribute("lastName").getName()).in(lastName));

        TypedQuery<Person> q = em.createQuery(cq);
        List<Person> allPersons = q.getResultList();
        return allPersons;
    }

}
