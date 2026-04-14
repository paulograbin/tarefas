# ---- Build stage ----
FROM maven:3.8-eclipse-temurin-8 AS build

WORKDIR /app

# Copy pom.xml first and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build
COPY src/ src/
RUN mvn package -DskipTests

# ---- Runtime stage ----
FROM tomcat:8.5-jre8-temurin

# Remove default Tomcat apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Deploy the WAR as ROOT
COPY --from=build /app/target/tarefas.war /usr/local/tomcat/webapps/ROOT.war

ENV DB_HOST=localhost

EXPOSE 8080

CMD ["catalina.sh", "run"]
