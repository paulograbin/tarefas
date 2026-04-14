# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**fj21-tarefas** is a task management web app built with Spring MVC 4.0, Hibernate 4.3, and JSP views, deployed as a WAR on Tomcat 8.5. The codebase is in Portuguese (models, views, URLs). Database is MySQL 5.7. Java 8.

## Build & Run Commands

```bash
./mvnw clean package              # Build WAR (output: target/tarefas.war)
./mvnw package -DskipTests        # Build without tests
docker-compose up                  # Start MySQL + Tomcat (app on :8080, db on :3306)
docker-compose up --build          # Rebuild and start
```

There are no tests in the project (`src/test` does not exist).

## Architecture

Classic layered Spring MVC app with XML configuration (no Spring Boot):

```
JSP Views (webapp/WEB-INF/views/)
        ↓
Spring @Controller (controller/)
        ↓
JDBC @Repository DAOs (dao/)
        ↓
MySQL 5.7 (database: fj21)
```

**Request lifecycle**: `web.xml` DispatcherServlet → `AutorizadorInterceptor` checks session auth → Controller → DAO → JSP view resolved via `InternalResourceViewResolver` (prefix `/WEB-INF/views/`, suffix `.jsp`).

**Spring config is XML-based** in `webapp/WEB-INF/spring-context.xml` (not annotation/Java config). Bean definitions, interceptors, data source, and view resolver are all there.

### Dual data access pattern

The project has two overlapping data access approaches:
- **JDBC DAOs** (`JdbcTarefaDao`, `JdbcUsuarioDao`) — used by controllers at runtime, wired via Spring
- **JPA/Hibernate** (`persistence.xml` + entity annotations on `Tarefa`) — used only by standalone utility classes in `jpa/` package (`BuscaTarefas`, `GeraTabelas`, `CarregaTarefa`), not used in the web app

### Authentication

Session-based via `AutorizadorInterceptor`. Login stores `usuarioLogado` in HttpSession. Public URLs: `/loginForm`, `/efetuaLogin`, `/resources/**`. Everything else requires auth.

## Database

- **Database**: `fj21`, MySQL 5.7, charset utf8mb4
- **Tables**: `tarefas` (id, descricao, finalizado, dataFinalizacao), `usuarios` (id, login, senha)
- **Default credentials**: root / 12345
- **Default user**: admin / admin (inserted via `docker/init.sql`)
- **Connection**: `DB_HOST` env var (defaults to `localhost`); connection string in both `spring-context.xml` and `persistence.xml`

## Key URLs (after login)

| Action | URL |
|--------|-----|
| Login page | `/loginForm` |
| Task list | `/listaTarefas` |
| New task form | `/novaTarefa` |
| Add task | POST `/adicionaTarefa` |
| Show/edit | `/mostraTarefa?id=` |
| Finalize | `/finalizaTarefa?id=` |
| Remove | `/removeTarefa?id=` |

## Docker

Multi-stage Dockerfile with layer-optimized WAR exploding (deps → config/views → static → classes). Docker Compose orchestrates MySQL 5.7 + Tomcat 8.5 with a healthcheck gate.
