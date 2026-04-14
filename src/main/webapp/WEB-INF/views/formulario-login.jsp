<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Tarefas</title>
    <link type="text/css" href="resources/css/tarefas.css" rel="stylesheet" />
</head>
<body>
    <div class="login-wrapper">
        <div class="login-card">
            <div class="card">
                <div class="login-logo">
                    <h1>Tarefas</h1>
                    <p>Entre com suas credenciais para continuar</p>
                </div>
                <c:if test="${not empty param.expirado}">
                    <div class="alert alert-info">Sua sessao expirou. Faca login novamente.</div>
                </c:if>
                <c:if test="${not empty param.erro}">
                    <div class="alert alert-danger">Login ou senha incorretos.</div>
                </c:if>
                <form action="efetuaLogin" method="post">
                    <div class="form-group">
                        <label for="login">Login</label>
                        <input type="text" id="login" name="login" class="form-control" placeholder="Seu login" autofocus />
                    </div>
                    <div class="form-group">
                        <label for="senha">Senha</label>
                        <input type="password" id="senha" name="senha" class="form-control" placeholder="Sua senha" />
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Entrar</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
