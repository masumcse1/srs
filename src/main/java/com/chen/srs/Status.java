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
	
	
	@EJB
   private RegistrationDao registrationDao;

    public  Object getStatus(String courseTitle) throws SQLException, NamingException, SystemException, NotSupportedException {
       //return Registration.getStatus(courseId);

        return registrationDao.getCourseStatus(courseTitle);
    }

    public Map<String, Object> getAllStatus() throws SQLException, NamingException, SystemException, NotSupportedException {
        return registrationDao.getAllStatus();
    }
}
