package com.chen.srs;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.transaction.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class CoursesSupportBean implements Serializable {

    private List<Course> courses;
    private String selectedCourse="1";
    private final static List<SelectItem> courseItems=createCourseItems();

    public CoursesSupportBean(){}

    public List getCourses() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        return CourseDao.getCourses();
    }

    public void setCourses(List<Course> courses){
        this.courses=courses;
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }


    private static List<SelectItem> createCourseItems() {
        List courses=null;
        try {
            courses = CourseDao.getCourses();
        }
        catch(SystemException | NotSupportedException | RuntimeException | RollbackException | HeuristicRollbackException | HeuristicMixedException e){
            System.out.println(e.getMessage());
        }
        if(courses!=null) {
            List<SelectItem> items = new ArrayList<SelectItem>();
            for (Object course : courses) {
                items.add(new SelectItem(((Course)course).getCourseId()+"."+((Course)course).getCourseTitle()));
            }
            return items;
        }
        return new ArrayList<SelectItem>();
    }

    public List<SelectItem> getCourseItems() {
        return courseItems;
    }

}
