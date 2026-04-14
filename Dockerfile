# ---- Build stage ----
FROM maven:3.8-eclipse-temurin-8 AS build

WORKDIR /app

# Copy pom.xml first and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build
COPY src/ src/
RUN mvn package -DskipTests

# ---- Explode stage ----
# Unpack the WAR into separate directories for layered copying
FROM eclipse-temurin:8-jdk AS explode

COPY --from=build /app/target/tarefas.war /tmp/app.war
RUN mkdir -p /exploded && \
    cd /exploded && \
    jar xf /tmp/app.war

# ---- Runtime stage ----
FROM tomcat:8.5-jre8-temurin

# Remove default Tomcat apps
RUN rm -rf /usr/local/tomcat/webapps/*

WORKDIR /usr/local/tomcat/webapps/ROOT

# Layer 1: Dependencies (changes rarely)
COPY --from=explode /exploded/WEB-INF/lib/ WEB-INF/lib/

# Layer 2: Config and views (changes occasionally)
COPY --from=explode /exploded/WEB-INF/web.xml WEB-INF/web.xml
COPY --from=explode /exploded/WEB-INF/spring-context.xml WEB-INF/spring-context.xml
COPY --from=explode /exploded/WEB-INF/views/ WEB-INF/views/

# Layer 3: Static resources (changes occasionally)
COPY --from=explode /exploded/resources/ resources/
COPY --from=explode /exploded/META-INF/ META-INF/

# Layer 4: Application classes (changes frequently)
COPY --from=explode /exploded/WEB-INF/classes/ WEB-INF/classes/

ENV DB_HOST=localhost

EXPOSE 8080

CMD ["catalina.sh", "run"]
