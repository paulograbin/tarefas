<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OK - Tarefas</title>
    <link type="text/css" href="resources/css/tarefas.css" rel="stylesheet" />
</head>
<body>
    <nav class="navbar">
        <a href="listaTarefas" class="navbar-brand">Tarefas</a>
        <ul class="navbar-nav">
            <li><a href="listaTarefas">Minhas Tarefas</a></li>
            <li><a href="logout">Sair</a></li>
        </ul>
    </nav>
    <div class="container">
        <div class="card card-centered" style="text-align: center; max-width: 480px; margin-left: auto; margin-right: auto;">
            <div class="alert alert-success" style="justify-content: center;">
                Operação realizada com sucesso!
            </div>
            <a href="listaTarefas" class="btn btn-primary">Voltar para a lista</a>
        </div>
    </div>
</body>
</html>
