package com.chen.srs;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/topic/RegCourseTopic"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"), })
public class MessageBean implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof MapMessage) {
                MapMessage mapMessage = (MapMessage) message;
                String userId = mapMessage.getString("User_ID");
                String courseId = mapMessage.getString("Course_ID");
                String courseName = mapMessage.getString("Course_Name");
                String dateOfRegistration = mapMessage.getString("Date_of_Registration");

                System.out.println("Received Message: from MDB--->>>");
                System.out.println("User_ID--: " + userId);
                System.out.println("Course_ID--: " + courseId);
                System.out.println("Course_Name--: " + courseName);
                System.out.println("Date_of_Registration--: " + dateOfRegistration);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
