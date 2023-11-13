package com.chen.srs;

import javax.persistence.*;
import javax.transaction.*;

import javax.annotation.Resource;
import javax.transaction.RollbackException;

import util.EntityManagerFactoryS;

import java.util.List;

public class CourseDao {

 
    @PersistenceContext
    static EntityManager em;
    @Resource
    UserTransaction utx;

    public static Object getCourseById(long courseId) throws SystemException, NotSupportedException{
        Object result=null;
        em=EntityManagerFactoryS.getEntityManagerFactory().createEntityManager();
        try{
            result=em.createQuery("SELECT c FROM  Course c WHERE c.courseId=:courseId").setParameter("courseId",courseId).getSingleResult();
        }
        catch(NoResultException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static Object getCourseId(String courseTitle) throws SystemException, NotSupportedException{
        Object result=null;
        em=EntityManagerFactoryS.getEntityManagerFactory().createEntityManager();
        try{
            result=em.createQuery("SELECT c.courseId FROM  Course c WHERE c.courseTitle=:courseTitle").setParameter("courseTitle",courseTitle).getSingleResult();
        }
        catch(NoResultException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static List getCourseTitles() throws SystemException, NotSupportedException{
    	em=EntityManagerFactoryS.getEntityManagerFactory().createEntityManager();
        return em.createQuery("SELECT c.courseTitle FROM  Course c").getResultList();
    }

    public static Object getCourseByTitle(String courseTitle) throws SystemException, NotSupportedException{
    	em=EntityManagerFactoryS.getEntityManagerFactory().createEntityManager();
        return em.createQuery("SELECT c FROM  Course c WHERE c.courseTitle=:courseTitle").setParameter("courseTitle",courseTitle).getSingleResult();
    }

    public static List getCourses() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
    	em=EntityManagerFactoryS.getEntityManagerFactory().createEntityManager();
        List courses=em.createQuery("SELECT c FROM  Course c").getResultList();
        if(courses.size()==0){
            addCourses();
            courses=em.createQuery("SELECT c FROM  Course c").getResultList();
        }
        return courses;
    }

    public static void addCourses() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        String[] courseTitles={"Enterprise Computing with Java","Web Services: Frameworks, Processes, Applications","Enterprise System Design and Implementation","Rich Internet Applications with Ajax","Big Data Processing Using Hadoop"};
        Course course =null;
        em=EntityManagerFactoryS.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        for(String courseTitle: courseTitles){
            course =new Course(courseTitle);
            em.persist(course);
        }
        em.getTransaction().commit();
    }



}
