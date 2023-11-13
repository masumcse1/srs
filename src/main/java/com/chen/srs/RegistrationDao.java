package com.chen.srs;

import javax.persistence.*;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import util.EntityManagerFactoryS;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegistrationDao {

   
    @PersistenceContext
    static EntityManager em;
    @Resource
    UserTransaction utx;

    public static Object getCourseStatus(String courseTitle) throws SystemException, NotSupportedException {
        Object courseId=CourseDao.getCourseId(courseTitle);
        if(courseId==null)return courseId;
        Long num=new Long(courseId.toString());
        em=EntityManagerFactoryS.getEntityManagerFactory().createEntityManager();
        Object result=em.createQuery("SELECT COUNT(r.courseId) FROM  Registration r WHERE r.courseId=:courseId").setParameter("courseId",num).getSingleResult();
        return result;
    }

    public static Map<String,Object> getAllStatus() throws SystemException, NotSupportedException {
        List courseTitles=CourseDao.getCourseTitles();
        String courseTitle="";
        Map<String,Object> courseStatuses=new HashMap<>();
        for(Object obj: courseTitles){
            courseTitle=(String)obj;
            courseStatuses.put(courseTitle,getCourseStatus(courseTitle));
        }
        return courseStatuses;
    }

    public static List getCourseRegistrationsIdsByStudentId(String id){
    	em=EntityManagerFactoryS.getEntityManagerFactory().createEntityManager();
        List registrations= em.createQuery("SELECT DISTINCT r.courseId FROM  Registration r WHERE r.student.id=:studentId").setParameter("studentId",id).getResultList();
        return registrations;
    }

    public static void save(Registration registration) throws SystemException, NotSupportedException{
    	em=EntityManagerFactoryS.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(registration);
        em.getTransaction().commit();
    }

}
