# srs
================Configure=================
 wildfy start : sh standalone.sh -c standalone-full.xml
 
 create management user : sh add-user.sh -a -u masum -p acec@1234@1234  

  create app user :       sh add-user.sh -a -u murad -p acec@1234 -g guest

                         sh jboss-cli.sh 

                         connect 
 
 create topic :    jms-topic add --topic-address=RegCourseTopic --entries=topic/RegCourseTopic,java:jboss/exported/jms/topic/RegCourseTopic
  
  xml :            <jms-topic name="RegCourseTopic" entries="topic/RegCourseTopic java:jboss/exported/jms/topic/RegCourseTopic"/>
   
   
   
==================JTA ========================