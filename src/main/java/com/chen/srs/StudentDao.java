package com.chen.srs;



import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import util.BusinessException;

public class StudentDao extends PersonDao {

	@PersistenceContext
	private EntityManager em;

	public Integer getTotalNoOfStudentRegistered() throws BusinessException {

		Integer numberOfStudents = 0 ;
		try {
			Date today = new Date(); 
			numberOfStudents = (int) em
					.createQuery("SELECT COUNT(s) FROM Student s WHERE s.registrationDate=:registrationDate")
					.setParameter("registrationDate", today)
					.getSingleResult();

		} catch (Exception e) {
			System.out.println("not more than X students registering during a given timeframe"+ e.getMessage());
			throw new BusinessException(e.getMessage());

		}
		return numberOfStudents;
	}

}
