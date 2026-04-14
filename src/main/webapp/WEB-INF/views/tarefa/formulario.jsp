<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nova Tarefa - Tarefas</title>
    <link type="text/css" href="resources/css/tarefas.css" rel="stylesheet" />
</head>
<body>
    <nav class="navbar">
        <a href="listaTarefas" class="navbar-brand">Tarefas</a>
        <ul class="navbar-nav">
            <li class="navbar-user">Bem vindo, <strong>${sessionScope.usuarioLogado.login}</strong></li>
            <li><a href="listaTarefas">Minhas Tarefas</a></li>
            <li><a href="logout">Sair</a></li>
        </ul>
    </nav>
    <div class="container" style="max-width: 640px;">
        <div class="card">
            <div class="page-header">
                <h1>Nova Tarefa</h1>
                <p>Preencha a descricao para criar uma nova tarefa</p>
            </div>

            <form:errors path="tarefa.descricao" cssClass="form-errors" element="div" />

            <form action="adicionaTarefa" method="post">
                <div class="form-group">
                    <label for="descricao">Descricao</label>
                    <textarea id="descricao" name="descricao" class="form-control" rows="5" placeholder="Descreva a tarefa..."></textarea>
                </div>
                <div style="display: flex; gap: 0.75rem;">
                    <button type="submit" class="btn btn-primary">Adicionar</button>
                    <a href="listaTarefas" class="btn btn-outline">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
