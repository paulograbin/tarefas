<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Minhas Tarefas - Tarefas</title>
    <link type="text/css" href="resources/css/tarefas.css" rel="stylesheet" />
    <script type="text/javascript" src="resources/js/jquery.js"></script>
</head>
<body>
    <nav class="navbar">
        <a href="listaTarefas" class="navbar-brand">Tarefas</a>
        <ul class="navbar-nav">
            <li class="navbar-user">Bem vindo, <strong>${sessionScope.usuarioLogado.login}</strong></li>
            <li><a href="listaTarefas">Minhas Tarefas</a></li>
            <li><a href="novaTarefa">Nova Tarefa</a></li>
            <li><a href="logout">Sair</a></li>
        </ul>
    </nav>

    <script type="text/javascript">
        function finalizaAgora(id) {
            $.post("finalizaTarefa", {'id' : id}, function(resposta) {
                $("#tarefa_"+id).html(resposta);
            });
        }

        function reabreAgora(id) {
            $.post("reabreTarefa", {'id' : id}, function(resposta) {
                $("#tarefa_"+id).html(resposta);
            });
        }
    </script>

    <div class="container">
        <div class="toolbar">
            <div class="page-header">
                <h1>Minhas Tarefas</h1>
                <c:if test="${not empty tarefas}">
                    <c:set var="pendentes" value="0" />
                    <c:set var="finalizadas" value="0" />
                    <c:forEach items="${tarefas}" var="t">
                        <c:choose>
                            <c:when test="${t.finalizado}"><c:set var="finalizadas" value="${finalizadas + 1}" /></c:when>
                            <c:otherwise><c:set var="pendentes" value="${pendentes + 1}" /></c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <p>${pendentes} pendente<c:if test="${pendentes != 1}">s</c:if>, ${finalizadas} finalizada<c:if test="${finalizadas != 1}">s</c:if></p>
                </c:if>
                <c:if test="${empty tarefas}">
                    <p>Gerencie suas tarefas</p>
                </c:if>
            </div>
            <a href="novaTarefa" class="btn btn-primary">Nova Tarefa</a>
        </div>

        <c:choose>
            <c:when test="${empty tarefas}">
                <div class="card">
                    <div class="empty-state">
                        <p>Nenhuma tarefa encontrada.</p>
                        <a href="novaTarefa" class="btn btn-primary">Criar primeira tarefa</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Descricao</th>
                                <th>Status</th>
                                <th>Data de Finalizacao</th>
                                <th>Acoes</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${tarefas}" var="tarefa">
                                <tr id="tarefa_${tarefa.id}">
                                    <td>${tarefa.id}</td>
                                    <td>${tarefa.descricao}</td>
                                    <c:if test="${tarefa.finalizado eq true}">
                                        <td><span class="badge badge-success">Finalizada</span></td>
                                    </c:if>
                                    <c:if test="${tarefa.finalizado eq false}">
                                        <td>
                                            <a href="#" onclick="finalizaAgora(${tarefa.id}); return false;" class="badge badge-warning" style="text-decoration:none; cursor:pointer;">
                                                Pendente
                                            </a>
                                        </td>
                                    </c:if>
                                    <td>
                                        <fmt:formatDate value="${tarefa.dataFinalizacao.time}" pattern="dd/MM/yyyy" />
                                    </td>
                                    <td>
                                        <div class="actions">
                                            <c:if test="${tarefa.finalizado eq true}">
                                                <a href="#" onclick="reabreAgora(${tarefa.id}); return false;" class="btn btn-outline btn-sm">Reabrir</a>
                                            </c:if>
                                            <a href="mostraTarefa?id=${tarefa.id}" class="btn btn-outline btn-sm">Alterar</a>
                                            <a href="#" onclick="if(confirm('Tem certeza que deseja remover esta tarefa?')) window.location='removeTarefa?id=${tarefa.id}'; return false;" class="btn btn-danger btn-sm">Remover</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
