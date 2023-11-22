package com.chen.srs;



import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import util.BusinessException;


@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StudentDao  {

	@PersistenceContext
	private EntityManager em;

	public long getTotalNoOfStudentRegistered() throws BusinessException {

		long numberOfStudents = 0 ;
		try {
			Date today = new Date(); 
			numberOfStudents = (long) em
					.createQuery("SELECT COUNT(s) FROM Student s WHERE s.registrationDate=:registrationDate")
					.setParameter("registrationDate", today)
					.getSingleResult();

		} catch (Exception e) {
			System.out.println("registermaximumlimitover"+ e.getMessage());
			throw new BusinessException(e.getMessage());

		}
		return numberOfStudents;
	}

}
