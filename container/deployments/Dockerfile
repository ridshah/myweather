FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD myweather-0.0.1.jar api.jar
RUN sh -c 'touch /api.jar'
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /api.jar" ]
