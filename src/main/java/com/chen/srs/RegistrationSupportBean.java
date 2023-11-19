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

@Named
@SessionScoped
public class RegistrationSupportBean implements Serializable {

    private String selectedCourse;
    private long courseCapacity;
    private static String message="";
    private static String messageCSSClass="error";

    @Resource(mappedName = "java:/topic/RegCourseTopic")
    private Topic topic;

    @Inject
    private JMSContext context;
    
    private List<Course> courses ;

	@EJB
	private CourseDao courseDao;
	@EJB
	private RegistrationDao registrationDao;

	@EJB
	private PersonDao personDao;

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

//    public void register() {
//        try{
//            FacesContext facesContext=FacesContext.getCurrentInstance();
//            final long courseCapacity=Long.parseLong(facesContext.getExternalContext().getInitParameter("course-capacity"));
//            String courseTitle=selectedCourse.split("\\.")[1];
//            Course course=(Course)CourseDao.getCourseByTitle(courseTitle);
//            if(course!=null){
//                long numberRegistered=(Long)RegistrationDao.getCourseStatus(course.getCourseTitle());
//                if(numberRegistered<courseCapacity){
//                    HttpSession session=(HttpSession) facesContext.getExternalContext().getSession(true);
//                    Student student=(Student)session.getAttribute("student");
//                    Registration registration=new Registration(course.getCourseId(),numberRegistered+1,student);
//                    RegistrationDao.save(registration);
//                    setMessage("Registration for "+selectedCourse+" successful");
//                    setMessageCSSClass("success");
//                    //send Message
//                    try{
//                        System.out.println("Connection Factory: "+connectionFactory);
//                        if(connection==null) connection=connectionFactory.createConnection();
//                        System.out.println("Connection: "+connection);
//                        if(msgSession==null) msgSession=connection.createSession();
//                        System.out.println("Session: "+msgSession);
//                        MessageProducer producerQueue=msgSession.createProducer(queue);
//                        System.out.println("Queue: "+queue);
//                        MessageProducer producerTopic=msgSession.createProducer(topic);
//                        System.out.println("Topic: "+topic);
//                        MessageConsumer consumerTopic=msgSession.createConsumer(topic);
//                        MapMessage message=msgSession.createMapMessage();
//                        message.setString("Text","hello");
//                        System.out.println("Sending message "+message);
//                        producerQueue.send(message);
//                        producerTopic.send(message);
//                    }
//                    catch(JMSException e){
//                        System.out.println(e.getMessage());
//                        if(connection!=null){
//                            try{
//                                connection.close();
//                            }
//                            catch (JMSException err){
//                                System.out.println(err.getMessage());
//                            }
//                        }
//                    }
//                }
//                else{
//                    setMessage("Course is full");
//                    setMessageCSSClass("error");
//                }
//            }
//            else{
//                setMessage("Course does not exist");
//                setMessageCSSClass("error");
//            }
//        }
//        catch(SystemException | NotSupportedException e){
//            System.out.println(e.getMessage());
//            setMessage("Something went wrong");
//            setMessageCSSClass("error");
//        }
//    }
public void register() {
    try {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        final long courseCapacity = Long.parseLong(facesContext.getExternalContext().getInitParameter("course-capacity"));
        Course course = (Course) courseDao.getCourseById(Long.valueOf(selectedCourse));

        if (course != null) {
            long numberRegistered = (Long) registrationDao.getCourseStatus(course.getCourseTitle());

            if (numberRegistered < courseCapacity) {
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
                Student student = (Student) session.getAttribute("student");
                Registration registration = new Registration(course.getCourseId(), numberRegistered + 1, student);
                registrationDao.save(registration);
                setMessage("Registration for " + selectedCourse + " successful");
                setMessageCSSClass("success");

                // Send Message
                try {


                    MapMessage message = context.createMapMessage();
                    message.setString("User_ID", student.getId());
                    message.setLong("Course_ID", course.getCourseId());
                    message.setString("Course_Name", course.getCourseTitle());
                    message.setString("Date_of_Registration", LocalDate.now().toString());

            		context.createProducer().send(topic, message);
            		   System.out.println("Message send done!");
                } catch (JMSException e) {
                    // Handle JMS exception
                    System.out.println("Error sending JMS message: " + e.getMessage());
                }
            } else {
                setMessage("Course is full");
                setMessageCSSClass("error");
            }
        } else {
            setMessage("Course does not exist");
            setMessageCSSClass("error");
        }
    } catch (SystemException | NotSupportedException e) {
        // Handle exception
        System.out.println("Error during registration: " + e.getMessage());
        setMessage("Something went wrong");
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
