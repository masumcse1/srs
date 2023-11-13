package com.chen.srs;

import javax.ejb.*;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import java.sql.SQLException;
import java.util.Map;

@Stateless
public class Status {

    public  Object getStatus(String courseTitle) throws SQLException, NamingException, SystemException, NotSupportedException {
       //return Registration.getStatus(courseId);

        return RegistrationDao.getCourseStatus(courseTitle);
    }

    public Map<String, Object> getAllStatus() throws SQLException, NamingException, SystemException, NotSupportedException {
        return RegistrationDao.getAllStatus();
    }
}
