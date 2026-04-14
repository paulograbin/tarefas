<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tarefa Adicionada - Tarefas</title>
    <link type="text/css" href="resources/css/tarefas.css" rel="stylesheet" />
</head>
<body>
    <nav class="navbar">
        <a href="listaTarefas" class="navbar-brand">Tarefas</a>
        <ul class="navbar-nav">
            <li><a href="listaTarefas">Minhas Tarefas</a></li>
            <li><a href="novaTarefa">Nova Tarefa</a></li>
            <li><a href="logout">Sair</a></li>
        </ul>
    </nav>
    <div class="container" style="max-width: 560px;">
        <div class="card card-centered" style="text-align: center;">
            <div class="alert alert-success" style="justify-content: center;">
                Nova tarefa adicionada com sucesso!
            </div>
            <div style="display: flex; gap: 0.75rem; justify-content: center;">
                <a href="listaTarefas" class="btn btn-primary">Ver lista de tarefas</a>
                <a href="novaTarefa" class="btn btn-outline">Adicionar outra</a>
            </div>
        </div>
    </div>
</body>
</html>
