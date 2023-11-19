package com.chen.srs;

import javax.transaction.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.*;
import java.util.function.BiFunction;
import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.registry.infomodel.PersonName;
import java.sql.Statement;

@WebServlet(urlPatterns = {"/a", "/b", "/reset","/status"})
public class RegistrationController extends HttpServlet {

    @EJB
    Status status;
    
    @EJB
    private PersonDao personDao;
    
    @EJB
    private CourseDao courseDao;


private void test() throws NamingException,SQLException {
       Connection conn=MyDataSource.getConnection();
       String query="Select * from student;";
       Statement stmt=conn.createStatement();
       ResultSet rs=stmt.executeQuery(query);
       System.out.println("Reading students....");
       while(rs.next()){
           System.out.println("Student ID: "+rs.getString("USER_ID")+", first name: "+rs.getString("FIRST_NAME"));
       }
       conn.close();
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-cache,no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Expires", "0");

        HttpSession session = req.getSession();
        String path = req.getServletPath();
        Object studentAttribute = session.getAttribute("student");

        if (path.matches("^/a")) {
            if (studentAttribute == null) {
                Student student = new Student();
                session.setAttribute("student", student);
                req.getRequestDispatcher("/WEB-INF/views/a.jsp").forward(req, resp);
            }
            else{
                Student student =(Student)studentAttribute;
                if(student.getId()!=null && !student.getId().isEmpty()) {
                    boolean isRegistered = false;
                    try {
                        isRegistered = personDao.isRegistered(student.getId());
                        if(isRegistered ){
                            if(personDao.isStillLoggedIn(student.getId(), student.getPassword())){
                                resp.sendRedirect("/srs/dashboard");
                            }
                            else{
                                req.getRequestDispatcher("/WEB-INF/views/a.jsp").forward(req, resp);
                            }
                        }
                        else{
                            //forward to form a
                            req.getRequestDispatcher("/WEB-INF/views/a.jsp").forward(req, resp);
                        }
                    } catch (SQLException | NamingException e) {
                        System.out.println(e.getMessage());
                        req.setAttribute("failure", true);
                        req.getRequestDispatcher("/WEB-INF/views/a.jsp").forward(req, resp);
                    }
                }
                else{
                    req.getRequestDispatcher("/WEB-INF/views/a.jsp").forward(req, resp);
                }
            }
        } else if (path.matches("^/b")) {
            if (studentAttribute != null) {
                Student student = (Student) studentAttribute;
                if(student.getId()!=null && !student.getId().isEmpty()){
                    boolean isRegistered = false;
                    try {
                        isRegistered = personDao.isRegistered(student.getId());
                        if(isRegistered && personDao.isStillLoggedIn(student.getId(), student.getPassword())){
                            resp.sendRedirect("/srs/dashboard");
                        }
                        else{
                            Map<String, String> errorsFormA = student.getErrorsFormA();
                            if (errorsFormA.isEmpty()) {
                                //form A is valid
                                //this happens when student enter url in browser
                                req.getRequestDispatcher("/WEB-INF/views/b.jsp").forward(req, resp);
                            } else {
                                //go back to registration form A
                                resp.sendRedirect(req.getContextPath() + "/a");
                            }
                        }
                    } catch (SQLException | NamingException e) {
                        System.out.println(e.getMessage());
                        req.setAttribute("failure", true);
                        req.getRequestDispatcher("/WEB-INF/views/b.jsp").forward(req, resp);
                    }
                }else{
                    resp.sendRedirect(req.getContextPath() + "/a");
                }
            } else {
                //go back to registration form A
                resp.sendRedirect(req.getContextPath() + "/a");
            }
        } else if (path.matches("^/reset")) {
            session.removeAttribute("user");
            //go back to registration form A
            resp.sendRedirect(req.getContextPath() + "/a");
        }
        else if(path.matches("^/status")){
            Student student =(Student)session.getAttribute("student");
            if(student ==null || student.getId()==null || student.getId().isEmpty()){
                resp.sendRedirect("/srs/login");
            }
           else{
                boolean isRegistered=false;
                try{
                    isRegistered= personDao.isRegistered(student.getId());
                    if(!isRegistered){
                        resp.sendRedirect("/srs/login");
                    }
                    else{
                        try {
                            List courses=courseDao.getCourses();
                            req.setAttribute("courses",courses);
                            req.getRequestDispatcher("/WEB-INF/views/status.jsp").forward(req, resp);
                        } catch (SystemException | NotSupportedException | HeuristicMixedException |
                                 HeuristicRollbackException | RollbackException e) {
                            System.out.println(e.getMessage());
                            req.setAttribute("failure",true);
                            req.getRequestDispatcher("/WEB-INF/views/status.jsp").forward(req, resp);
                        }
                    }
                }
                catch(SQLException | NamingException e){
                    System.out.println(e.getMessage());
                    resp.sendRedirect("/srs/login");
                }
            }
        }
        else{
            req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-cache,no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Expires", "0");

        HttpSession session = req.getSession();
        String path = req.getServletPath();
        //process registration form a
        Student student = (Student) session.getAttribute("student");
        Map<String, String> errors = new HashMap<>();
        if (path.matches("^/a")) {
            if (student == null) {
                student =new Student();
                session.setAttribute("student", student);
            }
            final String id = getParam.apply(req, "id");
            student.setId(id);
            if (id.isEmpty()) {
                errors.put("id", "Id is Required");
            }
            final String password = getParam.apply(req, "password");
            final String repeatPassword = getParam.apply(req, "repeatPassword");
            //password is 8 characters long and it is equal to repeat password
            student.setPassword(password);
            student.setRepeatPassword(repeatPassword);
            if (!(password.matches("^.{8}$") && password.equalsIgnoreCase(repeatPassword))) {
                errors.put("password", "Password Must Be 8 Characters and Equal to Repeat Password");
            }
            String firstName = getParam.apply(req, "firstName");
            student.setFirstName(firstName);
            if (firstName.isEmpty()) {
                errors.put("firstName", "First Name is Required");
            }
            String lastName = getParam.apply(req, "lastName");
            student.setLastName(lastName);
            if (lastName.isEmpty()) {
                errors.put("lastName", "Last Name is Required");
            }
            String email = getParam.apply(req, "email");
            //simple email validation
            student.setEmail(email);
            if (!email.matches("^[^@]*@[^@]*$")) {
                //Here's what this regex pattern does:
                //^ and $ are anchors that specify the start and end of the string, respectively.
                //[^@]* matches zero or more characters that are not "@".
                //@ matches the "@" symbol.
                //Again, [^@]* matches zero or more characters that are not "@".
                errors.put("email", "Invalid Email");
            }
            String ssn = getParam.apply(req, "ssn");
            student.setSsn(ssn);
            if (!ssn.matches("^[0-9]{9}$")) {
                errors.put("ssn", "Social Security Should Be 9 Digits");
            }
            String level = getParam.apply(req, "level");
            student.setLevel(level);
            if (!(level.equals("undergraduate") || level.equals("graduate"))) {
                errors.put("level", "Level must be undergraduate or graduate");
            }
            //set errors
            student.setErrorsFormA(errors);
            if (errors.isEmpty()) {
                req.getRequestDispatcher("/WEB-INF/views/b.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/WEB-INF/views/a.jsp").forward(req, resp);
            }

        } else if (path.matches("^/b")) {
            //process registration form b
            if(student ==null) {
                req.getRequestDispatcher("/WEB-INF/views/a.jsp").forward(req, resp);
            }
            else{
                final String address = getParam.apply(req, "address");
                student.setAddress(address);
                if (address.isEmpty()) {
                    errors.put("address", "Address is required");
                }
                final String city = getParam.apply(req, "city");
                student.setCity(city);
                if (city.isEmpty()) {
                    errors.put("city", "City is required");
                }
                final String state = getParam.apply(req, "state");
                student.setState(state);
                if (state.isEmpty()) {
                    errors.put("state", "State is required");
                }
                final String zipCode = getParam.apply(req, "zipCode");
                student.setZipCode(zipCode);
                if (!zipCode.matches("^[0-9]{5}$")) {
                    errors.put("zipCode", "Zip must be 5 digits");
                }

                //set errors
                student.setErrorsFormB(errors);
                Connection conn=null;
                if (errors.isEmpty()) {
                    try{;
                        boolean result=personDao.save(student);
                        if(result){
                            RegistrationSupportBean.setMessage("");
                            req.setAttribute("complete", true);
                        }
                        else{
                            req.setAttribute("duplicate", true);
                            session.removeAttribute("student");
                        }
                        session.removeAttribute("student");
                    }
                    catch(NamingException | NoSuchAlgorithmException | InvalidKeySpecException | SQLException e){
                        System.out.println(e.getMessage());
                        req.setAttribute("failure", true);
                    }
                }
                req.getRequestDispatcher("/WEB-INF/views/b.jsp").forward(req, resp);
            }

        }
        else if(path.matches("^/status")){
            boolean isRegistered=false;
            if(student ==null || student.getId().isEmpty()){
                resp.sendRedirect("/srs/login");
            }
            else{
                try {
                    isRegistered = personDao.isRegistered(student.getId());
                }
                catch(SQLException|NamingException e) {
                    System.out.println(e.getMessage());
                    resp.sendRedirect("srs/login");
                }
                if(!isRegistered){
                    resp.sendRedirect("/srs/login");
                }
                else{
                    List courses=null;
                    try{
                        courses=courseDao.getCourses();
                        req.setAttribute("courses",courses);
                    }
                    catch (SystemException | NotSupportedException | RollbackException | HeuristicRollbackException| HeuristicMixedException e){
                        System.out.println(e.getMessage());
                        req.setAttribute("failure",true);
                        req.getRequestDispatcher("/WEB-INF/views/status.jsp").forward(req, resp);
                    }
                    Enumeration<String> names= req.getParameterNames();
                    StringBuilder text=new StringBuilder();
                    Course course=null;
                    Object numRegistered=null;
                    long count=0;
                    while(names.hasMoreElements()){
                        count++;
                        String name=names.nextElement();
                        try{
                            numRegistered=status.getStatus(name);
                            if(numRegistered!=null){
                                text.append("<p>");
                                text.append(name);
                                text.append(" has ");
                                text.append(numRegistered);
                                text.append(" students");
                                text.append("</p>");
                            }
                            else{
                                text.append("<p>");
                                text.append("Course does not exist.");
                                text.append("</p>");
                                req.setAttribute("message",text.toString());
                                req.setAttribute("messageCSSClass","error");
                                req.getRequestDispatcher("/WEB-INF/views/status.jsp").forward(req, resp);
                            }
                        }
                        catch(SQLException | NamingException | SystemException | NotSupportedException e){
                            System.out.println(e.getMessage());
                            req.setAttribute("failure",true);
                            req.getRequestDispatcher("/WEB-INF/views/status.jsp").forward(req, resp);
                        }
                    }
                    if(count==0){
                        Map<String,Object> courseStatuses=new HashMap<>();
                        try {
                            courseStatuses=status.getAllStatus();
                            Set<String> courseTitles=courseStatuses.keySet();
                            Iterator<String> iterator=courseTitles.iterator();
                            String courseTitle="";
                            while(iterator.hasNext()){
                                courseTitle=iterator.next();
                                text.append("<p>");
                                text.append(courseTitle);
                                text.append(" has ");
                                text.append(courseStatuses.get(courseTitle));
                                text.append(" students");
                                text.append("</p>");
                            }
                        } catch (SQLException | NamingException | SystemException | NotSupportedException e) {
                            System.out.println(e.getMessage());
                            req.setAttribute("failure",true);
                            req.getRequestDispatcher("/WEB-INF/views/status.jsp").forward(req, resp);
                        }
                    }
                    req.setAttribute("message",text.toString());
                    req.getRequestDispatcher("/WEB-INF/views/status.jsp").forward(req, resp);
                }
            }
        }
    }

    private BiFunction<HttpServletRequest, String, String> getParam = (BiFunction<HttpServletRequest, String, String>) new BiFunction<HttpServletRequest, String, String>() {
        @Override
        public String apply(HttpServletRequest request, String param) {
            final String parameter = request.getParameter(param);
            return parameter == null ? "" : parameter.trim();
        }
    };

}
