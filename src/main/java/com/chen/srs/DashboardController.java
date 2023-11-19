package com.chen.srs;

import java.io.IOException;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

@WebServlet(urlPatterns = {"/dashboard"})
public class DashboardController extends HttpServlet {
	
	 @EJB
	 private PersonDao personDao;
	 
	  
	  @EJB
	    private CourseDao courseDao;
	  
	  @EJB
	   private RegistrationDao registrationDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-cache,no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Expires", "0");

        HttpSession session = req.getSession();
        Student student = (Student)session.getAttribute("student");
        try {
            if(student ==null || student.getId()==null || student.getId().isEmpty() || !personDao.isRegistered(student.getId()) || !personDao.isStillLoggedIn(student.getId(), student.getPassword())) {
                resp.sendRedirect("/srs/login");
            }
            else{
                //getCourses(req,resp);
                req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
            }
        } catch (SQLException | NamingException e) {
            System.out.println(e.getMessage());
            req.setAttribute("failure",true);
            resp.sendRedirect("/srs/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-cache,no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Expires", "0");

        HttpSession session = req.getSession();
        Student student = (Student)session.getAttribute("student");
        try {
            if(student ==null || student.getId()==null || student.getId().isEmpty() || !personDao.isRegistered(student.getId()) || personDao.isStillLoggedIn(student.getId(), student.getPassword())) {
                resp.sendRedirect("/srs/login");
            }
            else{
                //getCourses(req,resp);
                req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
            }
        } catch (SQLException | NamingException e) {
            System.out.println(e.getMessage());
            req.setAttribute("failure",true);
            resp.sendRedirect("/srs/login");
        }
        String courseTitle=req.getParameter("courseTitle");
        if(courseTitle.isEmpty()){
            req.setAttribute("incomplete",true);
            req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
        }
        else {
            Course course= null;
            try {
                course = (Course)courseDao.getCourseByTitle(courseTitle);
            } catch (SystemException | NotSupportedException e) {
                System.out.println(e.getMessage());
                req.setAttribute("failure",true);
                req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
            }
            if(course!=null){
                Registration registration= null;
                try {
                    registration = new Registration(course.getCourseId(),(Long)registrationDao.getCourseStatus(course.getCourseTitle()));
                } catch (SystemException | NotSupportedException e) {
                   System.out.println(e.getMessage());
                    req.setAttribute("failure",true);
                    req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
                }
                try {
                    if(registration!=null){
                    	registrationDao.save(registration);
                        req.setAttribute("complete",true);
                        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
                    }
                    else{
                        req.setAttribute("failure",true);
                        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
                    }
                } catch (SystemException | NotSupportedException e) {
                    String msg=e.getMessage();
                    System.out.println(msg);
                    String[] arr=msg.split(" ");
                    for(String word:arr){
                        if(word.equalsIgnoreCase("unique")){
                            req.setAttribute("alreadyRegistered",true);
                            req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
                        }
                    }
                    if(req.getAttribute("alreadyRegistered")==null){
                        req.setAttribute("failure",true);
                        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
                    }
                }
            }
            else{
                req.setAttribute("noSuchCourse",true);
                req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
            }
        }
    }

   /* private void getCourses(HttpServletRequest req,HttpServletResponse resp){
        try {
            List<Course> courses=Course.getCourses();
            req.setAttribute("courses",courses);
            //System.out.println("Courses attribute "+req.getAttribute("courses"));
        } catch (SQLException | NamingException e) {
            System.out.println(e.getMessage());
            req.setAttribute("failure",true);
        }
    }*/
}
