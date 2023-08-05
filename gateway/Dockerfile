FROM amazoncorretto:11
COPY target/*.jar app.jar
ENV TZ="Europe/Moscow"
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8081
ENTRYPOINT ["java", "-jar", "/app.jar"]