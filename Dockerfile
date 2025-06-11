FROM tomcat:9.0.104-jdk17-temurin

# Copy WAR-files to webapps-map
COPY target/eindopdracht-0.1.war /usr/local/tomcat/webapps/ROOT.war

# Port
EXPOSE 8080

# Debug logs
ENV CATALINA_OUT=/dev/stdout

# Start Tomcat
CMD ["catalina.sh", "run"]