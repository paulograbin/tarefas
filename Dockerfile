# ---- Build stage ----
FROM eclipse-temurin:8-jdk AS build

# Download Tomcat 8.5 to get the servlet-api jar for compilation
ENV TOMCAT_VERSION=8.5.100
RUN mkdir -p /opt/tomcat && \
    curl -fsSL "https://archive.apache.org/dist/tomcat/tomcat-8/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz" \
    | tar xz --strip-components=1 -C /opt/tomcat

WORKDIR /app

# Copy library JARs and source
COPY WebContent/WEB-INF/lib/ /app/lib/
COPY src/ /app/src/

# Compile all Java sources
RUN mkdir -p /app/classes && \
    find /app/src -name "*.java" > /tmp/sources.txt && \
    javac -encoding UTF-8 \
          -source 1.8 -target 1.8 \
          -cp "/app/lib/*:/opt/tomcat/lib/*" \
          -d /app/classes \
          @/tmp/sources.txt

# ---- Runtime stage ----
FROM tomcat:8.5-jre8-temurin

# Remove default Tomcat apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the webapp into ROOT so it serves at /
COPY WebContent/ /usr/local/tomcat/webapps/ROOT/

# Copy compiled classes
COPY --from=build /app/classes/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/

# Copy persistence.xml
COPY src/META-INF/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/META-INF/

ENV DB_HOST=localhost

EXPOSE 8080

CMD ["catalina.sh", "run"]
