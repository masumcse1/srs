package com.chen.srs;

import java.time.LocalDate;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Topic;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import util.BusinessException;

@Stateless
public class RegistrarCourseBean {
	
	@EJB
	private CourseDao courseDao;
	@EJB
	private RegistrationDao registrationDao;
	
    @Resource(mappedName = "java:/topic/RegCourseTopic")
    private Topic topic;

    @Inject
    private JMSContext context;
    
	@EJB
	private RegistrarCourseBean registrarCourseBean;
	
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String register(long selectedCourse ,long courseCapacity ,Student student ) throws BusinessException {
	    try {
	       
	        Course course = (Course) courseDao.getCourseById(selectedCourse);
           String message ;
	        if (course != null) {
	            long numberRegistered = (Long) registrationDao.getCourseStatus(course.getCourseTitle());

	            if (numberRegistered < courseCapacity) {
	                 Registration registration = new Registration(course.getCourseId(), numberRegistered + 1, student);
	                 registrationDao.save(registration);
	                 message = "Registration for " + selectedCourse + " successful";
	        
	                try {

	                    MapMessage mapmessage = context.createMapMessage();
	                    mapmessage.setString("User_ID", student.getId());
	                    mapmessage.setLong("Course_ID", course.getCourseId());
	                    mapmessage.setString("Course_Name", course.getCourseTitle());
	                    mapmessage.setString("Date_of_Registration", LocalDate.now().toString());

	            		context.createProducer().send(topic, mapmessage);
	            		   System.out.println("Message send done!");
	            		   return message;
	                } catch (JMSException e) {
	                    throw new BusinessException(e.getMessage());
	                }
	            } else {
	            	// Implement Rule : 
	            	throw new BusinessException("You have reached the maximum number of Student registered limit");

	            }
	        } else {
	        	throw new BusinessException("Course does not exist");
	         }
	    } catch (SystemException | NotSupportedException e) {
	        // Handle exception
	        System.out.println("Error during registration: " + e.getMessage());
	        throw new BusinessException(e.getMessage());
	   
	    }
	}

}
