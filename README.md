# srs
================Configure=================
 wildfy start : standalone.bat -c standalone-full.xml
 create management user : add-user.bat -a -u masum -p Masum@1234  

  create app user :       add-user.bat -a -u murad -p Murad@1234 -g guest

                         jboss-cli.bat 

                         connect 
 
 create topic :    jms-topic add --topic-address=RegCourseTopic --entries=topic/RegCourseTopic,java:jboss/exported/jms/topic/RegCourseTopic
  
  xml :            <jms-topic name="RegCourseTopic" entries="topic/RegCourseTopic java:jboss/exported/jms/topic/RegCourseTopic"/>
   
   
   