package com.chen.srs;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;


@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-cache,no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Expires", "0");

        final int maxLoginAttempts=Integer.parseInt(getServletContext().getInitParameter(("max-login-attempts")));
        if(req.getSession().getAttribute("loginAttempts")==null)req.getSession().setAttribute("loginAttempts",0);
        int loginAttempts=(int)req.getSession().getAttribute("loginAttempts");
        int remainingLoginAttempts=maxLoginAttempts-loginAttempts;
        if(remainingLoginAttempts<=0)resp.sendRedirect("/srs/terminate");
        req.setAttribute("remainingLoginAttempts",remainingLoginAttempts);

        HttpSession session = req.getSession();
        Student student = (Student)session.getAttribute("student");
        if(student ==null || student.getId()==null || student.getId().isEmpty()) req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        else{
            boolean isRegistered=false;
            try{
                isRegistered= PersonDao.isRegistered(student.getId());
                if(isRegistered && PersonDao.isStillLoggedIn(student.getId(), student.getPassword())){
                    resp.sendRedirect("/srs/dashboard");
                }
                else{
                    req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
                }
            }
            catch(SQLException | NamingException e){
                System.out.println(e.getMessage());
                req.setAttribute("failure",true);
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setHeader("Cache-Control", "no-cache,no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Expires", "0");

        HttpSession session = req.getSession();
        Student studentAttribute = (Student)session.getAttribute("student");
        if(studentAttribute ==null || studentAttribute.getId()==null || studentAttribute.getId().isEmpty()){
            String userId=req.getParameter("userId");
            if(userId==null || userId.isEmpty()){
                req.setAttribute("noCredentials",true);
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
                return;
            }
            String password=req.getParameter("password");
            if(password==null || password.isEmpty()){
                req.setAttribute("noCredentials",true);
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
                return;
            }
            Student student =null;
            try {
                Person person=PersonDao.login(userId, password);
                if(person !=null && person instanceof Student){
                    student = (Student)person;
                    session.setAttribute("student", student);
                    RegistrationSupportBean.setMessage("");
                    resp.sendRedirect("/srs/dashboard");
                }
                else{
                    Object obj=req.getSession().getAttribute("loginAttempts");
                    if(obj==null){
                        req.getSession().setAttribute("loginAttempts",0);
                        obj=req.getSession().getAttribute("loginAttempts");
                    }
                    int loginAttempts=(int)obj;
                    loginAttempts++;
                    req.getSession().setAttribute("loginAttempts",loginAttempts);
                    loginAttempts=(int)req.getSession().getAttribute("loginAttempts");
                    final int maxLoginAttempts=Integer.parseInt(getServletContext().getInitParameter(("max-login-attempts")));
                    int remainingLoginAttempts=maxLoginAttempts-loginAttempts;
                    if(remainingLoginAttempts<=0)resp.sendRedirect("/srs/terminate");
                    req.setAttribute("invalidCredentials",true);
                    req.setAttribute("remainingLoginAttempts",remainingLoginAttempts);
                    req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
                }
            }
            catch(SQLException|NamingException|NoSuchAlgorithmException|InvalidKeySpecException e){
                System.out.println(e.getMessage());
                req.setAttribute("failure", true);
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
            }
        }
        else{
            boolean isRegistered=false;
            try{
                isRegistered= PersonDao.isRegistered(studentAttribute.getId());
                if(isRegistered && PersonDao.isStillLoggedIn(studentAttribute.getId(), studentAttribute.getPassword())){
                    resp.sendRedirect("/srs/dashboard");
                }
                else{
                    req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
                }

            }
            catch(SQLException | NamingException e){
                System.out.println(e.getMessage());
                req.setAttribute("failure",true);
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
            }
            resp.sendRedirect("/srs/dashboard");
        }
    }

}
