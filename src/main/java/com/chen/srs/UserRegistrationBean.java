package com.chen.srs;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.NamingException;

import util.BusinessException;

@Stateless
public class UserRegistrationBean {
		
	 @EJB
	 private PersonDao personDao;
	 
	 @EJB
	 private StudentDao studentDao ;
	 
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public  boolean save(Student student , Integer maximumlimtOfRegistration) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, BusinessException, NamingException{
		
	
		long totalNoOfStudentRegistered = studentDao.getTotalNoOfStudentRegistered();
			
			if(totalNoOfStudentRegistered > maximumlimtOfRegistration ) {
				System.out.println("registermaximumlimitover");
				throw new BusinessException("registermaximumlimitover");
			}
			
			boolean result=personDao.save(student);
			
			return result;
		
		
		
		
		
		

		
	}

}
