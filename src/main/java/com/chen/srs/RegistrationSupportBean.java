package com.chen.srs;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Topic;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import util.BusinessException;

@Named
@SessionScoped
public class RegistrationSupportBean implements Serializable {

    private String selectedCourse;
    private long courseCapacity;
    private static String message="";
    private static String messageCSSClass="error";


    
    private List<Course> courses ;

	@EJB
	private CourseDao courseDao;
	
	@EJB
	private RegistrarCourseBean registrarCourseBean;

	
	@PostConstruct
	public void init() {

		try {
			courses = courseDao.getCourses();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void register() {
	    try {
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        final long courseCapacity = Long.parseLong(facesContext.getExternalContext().getInitParameter("course-capacity"));
	        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            Student student = (Student) session.getAttribute("student");
	        String successMessage = registrarCourseBean.register(Long.valueOf(selectedCourse), courseCapacity, student);
	        setMessage(successMessage);
	        setMessageCSSClass("success");
	        
	    } catch (Exception e) {
	        // Handle exception
	        System.out.println("Error during registration: " + e.getMessage());
	        setMessage(e.getMessage());
	        setMessageCSSClass("error");
	    }
	}




    public String getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public String getMessage() {
        return message;
    }

    public static void setMessage(String newMessage) {
        message = newMessage;
    }

    public String getMessageCSSClass() {
        return messageCSSClass;
    }

    public void setMessageCSSClass(String messageCSSClass) {
        this.messageCSSClass = messageCSSClass;
    }

    public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public long getCourseCapacity() {
		return courseCapacity;
	}

	public void setCourseCapacity(long courseCapacity) {
		this.courseCapacity = courseCapacity;
	}


}
