package com.chen.srs;

import javax.persistence.*;
import javax.transaction.*;


import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.transaction.RollbackException;


import java.util.List;


@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CourseDao {

	@PersistenceContext
	private EntityManager em;

    public  Object getCourseById(long courseId) throws SystemException, NotSupportedException{
        Object result=null;
        try{
            result=em.createQuery("SELECT c FROM  Course c WHERE c.courseId=:courseId").setParameter("courseId",courseId).getSingleResult();
        }
        catch(NoResultException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public  Object getCourseId(String courseTitle) throws SystemException, NotSupportedException{
        Object result=null;
         try{
            result=em.createQuery("SELECT c.courseId FROM  Course c WHERE c.courseTitle=:courseTitle").setParameter("courseTitle",courseTitle).getSingleResult();
        }
        catch(NoResultException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public  List getCourseTitles() throws SystemException, NotSupportedException{
        return em.createQuery("SELECT c.courseTitle FROM  Course c").getResultList();
    }

    public  Object getCourseByTitle(String courseTitle) throws SystemException, NotSupportedException{
        return em.createQuery("SELECT c FROM  Course c WHERE c.courseTitle=:courseTitle").setParameter("courseTitle",courseTitle).getSingleResult();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public  List<Course> getCourses() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
       
    	List<Course> courses= (List<Course>)em.createQuery("SELECT c FROM  Course c").getResultList();
	   
         if(courses.size()==0){
            addCourses();
            courses= (List<Course>)em.createQuery("SELECT c FROM  Course c").getResultList();
        }
        return courses;
    }
    
    public  void addCourses() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        String[] courseTitles={"Enterprise Computing with Java","Web Services: Frameworks, Processes, Applications","Enterprise System Design and Implementation","Rich Internet Applications with Ajax","Big Data Processing Using Hadoop"};
        Course course =null;
     
        for(String courseTitle: courseTitles){
            course =new Course(courseTitle);
            em.persist(course);
            
        }
        em.flush();
    }



}
