<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<td>${tarefa.id}</td>
<td>${tarefa.descricao}</td>
<td><span class="badge badge-success">Finalizada</span></td>
<td>
    <fmt:formatDate value="${tarefa.dataFinalizacao.time}" pattern="dd/MM/yyyy" />
</td>
<td>
    <div class="actions">
        <a href="removeTarefa?id=${tarefa.id}" class="btn btn-danger btn-sm">Remover</a>
        <a href="mostraTarefa?id=${tarefa.id}" class="btn btn-outline btn-sm">Alterar</a>
    </div>
</td>
