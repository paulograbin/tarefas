<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu - Tarefas</title>
    <link type="text/css" href="resources/css/tarefas.css" rel="stylesheet" />
</head>
<body>
    <nav class="navbar">
        <a href="listaTarefas" class="navbar-brand">Tarefas</a>
        <ul class="navbar-nav">
            <li class="navbar-user">Bem vindo, <strong>${usuarioLogado.login}</strong></li>
            <li><a href="logout">Sair</a></li>
        </ul>
    </nav>
    <div class="container">
        <div class="card card-centered" style="text-align: center; max-width: 480px; margin-left: auto; margin-right: auto;">
            <div class="page-header" style="margin-bottom: 1rem;">
                <h1>Bem vindo, ${usuarioLogado.login}</h1>
                <p>O que deseja fazer?</p>
            </div>
            <a href="listaTarefas" class="btn btn-primary">Ver lista de tarefas</a>
        </div>
    </div>
</body>
</html>
