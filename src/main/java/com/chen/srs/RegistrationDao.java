package com.chen.srs;

import javax.persistence.*;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import util.BusinessException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegistrationDao {

	@PersistenceContext
	private EntityManager em;
	
 
    @EJB
    private CourseDao courseDao;

    public  Object getCourseStatus(String courseTitle) throws SystemException, NotSupportedException, BusinessException {
    	Object result = null;
    	try {
    	    Object courseId=courseDao.getCourseId(courseTitle);
            if(courseId==null)return courseId;
            Long num=new Long(courseId.toString());
             result=em.createQuery("SELECT COUNT(r.courseId) FROM  Registration r WHERE r.courseId=:courseId").setParameter("courseId",num)
            		 .setLockMode(LockModeType.READ).getSingleResult();
           
    		
    	}catch (Exception e) {
			System.out.println("Mylock Exception :################ "+e.getMessage());
			throw new BusinessException("pessimistic locking   occured");
    		
		}
		return result;
        }
    //https://arnoldgalovics.com/jpa-pessimistic-locking/
    //https://blog.mimacom.com/handling-pessimistic-locking-jpa-oracle-mysql-postgresql-derbi-h2/
    
    public  Map<String,Object> getAllStatus() throws SystemException, NotSupportedException, BusinessException {
        List courseTitles=courseDao.getCourseTitles();
        String courseTitle="";
        Map<String,Object> courseStatuses=new HashMap<>();
        for(Object obj: courseTitles){
            courseTitle=(String)obj;
            courseStatuses.put(courseTitle,getCourseStatus(courseTitle));
        }
        return courseStatuses;
    }

    public  List getCourseRegistrationsIdsByStudentId(String id){
    	List registrations= em.createQuery("SELECT DISTINCT r.courseId FROM  Registration r WHERE r.student.id=:studentId").setParameter("studentId",id).getResultList();
        return registrations;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public  void save(Registration registration) throws SystemException, NotSupportedException{
       
        em.persist(registration);
       
    }

}
