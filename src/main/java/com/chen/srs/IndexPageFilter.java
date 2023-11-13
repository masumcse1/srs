package com.chen.srs;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/index.jsp","/register","/register.xhtml"})
public class IndexPageFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //forward to register for course controller
        String path=req.getServletPath();
        if(path.matches("^/register") || path.matches("/register.xhtml")){
            Object obj=req.getSession().getAttribute("student");
            if(obj!=null){
                Student student =(Student)obj;
                if(student.getId()!=null){
                    res.setHeader("Cache-Control", "no-cache,no-store, must-revalidate");
                    res.setHeader("Pragma", "no-cache");
                    res.setHeader("Expires", "0");
                    req.getRequestDispatcher("/register.xhtml").forward(req, res);
                }
                else{
                    req.getRequestDispatcher("/login").forward(req, res);
                }
            }
            else{
                req.getRequestDispatcher("/login").forward(req, res);
            }
        }
        else{
            req.getRequestDispatcher("/dashboard").forward(req, res);
        }
    }

}
