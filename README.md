# srs


================Configure=================

 wildfy start : standalone.bat -c standalone-full.xml

                 add-user.bat -a -u masum -p Masum@1234 

                add-user.bat -a -u murad -p Murad@1234 -g guest

               jboss-cli.bat 

               connect 
 
              jms-topic add --topic-address=myTopic --entries=topic/myTopic,java:jboss/exported/jms/topic/myTopic
  
  xml : 
  
   <jms-topic name="myTopic" entries="topic/myTopic java:jboss/exported/jms/topic/myTopic"/>