package com.chen.srs;

import util.BusinessException;

public interface IRegistrarCourseBean {
	public String register(long selectedCourse ,long courseCapacity ,Student student ) throws BusinessException;
	public String registerWithLongRunning(long selectedCourse ,long courseCapacity ,Student student ) throws BusinessException;

}
