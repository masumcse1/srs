package com.chen.srs;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(urlPatterns = {"/student-course-registrations"})
public class StudentCourseRegistrationsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-cache,no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Expires", "0");

        HttpSession session = req.getSession();
        Student student = (Student)session.getAttribute("student");
        try {
            if(student ==null || student.getId()==null || student.getId().isEmpty() || !PersonDao.isRegistered(student.getId()) || !PersonDao.isStillLoggedIn(student.getId(), student.getPassword())) {
                resp.sendRedirect("/srs/login");
            }
            else{
                //getCourses(req,resp);
                req.getRequestDispatcher("/WEB-INF/views/student-course-registrations.jsp").forward(req, resp);
            }
        } catch (SQLException | NamingException e) {
            System.out.println(e.getMessage());
            req.setAttribute("failure",true);
            resp.sendRedirect("/srs/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setHeader("Cache-Control", "no-cache,no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Expires", "0");

        HttpSession session = req.getSession();
        Student student = (Student)session.getAttribute("student");
        try {
            if(student ==null || student.getId()==null || student.getId().isEmpty() || !PersonDao.isRegistered(student.getId()) || !PersonDao.isStillLoggedIn(student.getId(), student.getPassword())) {
                resp.sendRedirect("/srs/login");
            }
            else{
                StringBuilder text=new StringBuilder();
                String lastName=req.getParameter("studentLastName");
                if(lastName.isEmpty()){
                    text.append("<p>Please enter student last name</p>");
                    req.setAttribute("CSSClassText","error");
                    req.setAttribute("text",text.toString());
                    req.getRequestDispatcher("/WEB-INF/views/student-course-registrations.jsp").forward(req, resp);
                    return;
                }
                List<Person> persons=PersonDao.getPersonsByLastName(lastName);
                if(persons.isEmpty()){
                    text.append("<p>No students exist by that last name</p>");
                    req.setAttribute("CSSClassText","error");
                    req.setAttribute("text",text.toString());
                    req.getRequestDispatcher("/WEB-INF/views/student-course-registrations.jsp").forward(req, resp);
                    return;
                }
                for(Person person:persons){
                    text.append("<p class='p-subtitle'>Student name:</p>");
                    text.append("<p>");
                    text.append(person.firstName);
                    text.append(" ");
                    text.append(person.lastName);
                    text.append("</p>");
                    List courseIds=RegistrationDao.getCourseRegistrationsIdsByStudentId(person.getId());
                    text.append("<p class='p-subtitle'>Course Registrations:</p>");
                    if(courseIds.isEmpty()){
                        text.append("<p>No course registrations</p>");
                        continue;
                    }
                    Course course=null;
                    for(Object courseId:courseIds){
                        try{
                            course=(Course)CourseDao.getCourseById((Long)courseId);
                            text.append("<p>");
                            text.append(course.getCourseTitle());
                            text.append("</p>");
                        }
                        catch(SystemException | NotSupportedException e){
                            System.out.println(e.getMessage());
                            req.setAttribute("failure",true);
                            req.getRequestDispatcher("/WEB-INF/views/student-course-registrations.jsp").forward(req, resp);
                            return;
                        }
                    }
                    text.append("----------");
                }
                req.setAttribute("text",text.toString());
                req.getRequestDispatcher("/WEB-INF/views/student-course-registrations.jsp").forward(req, resp);
            }
        } catch (SQLException | NamingException e) {
            System.out.println(e.getMessage());
            req.setAttribute("failure",true);
            resp.sendRedirect("/srs/login");
        }
    }

}
