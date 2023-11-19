# srs
================Configure=================
 wildfy start : sh standalone.sh -c standalone-full.xml
 
 create management user : sh add-user.sh -a -u masum -p acec@1234@1234  

  create app user :       sh add-user.sh -a -u murad -p acec@1234 -g guest

                         sh jboss-cli.sh 

                         connect 
 
 create topic :    jms-topic add --topic-address=RegCourseTopic --entries=topic/RegCourseTopic,java:jboss/exported/jms/topic/RegCourseTopic
  
  xml :            <jms-topic name="RegCourseTopic" entries="topic/RegCourseTopic java:jboss/exported/jms/topic/RegCourseTopic"/>
   
   
   
==================JTA ===================================================================================================

1. Data source 

<datasource jndi-name="java:jboss/datasources/H2_784_JNDI" pool-name="H2_784_DS" enabled="true" use-java-context="true">
                    <connection-url>jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
                    <driver>h2</driver>
                    <security>
                        <user-name>sa</user-name>
                        <password>sa</password>
                    </security>
                </datasource>
