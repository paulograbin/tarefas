<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<td>${tarefa.id}</td>
<td>${tarefa.descricao}</td>
<td>
    <a href="#" onclick="finalizaAgora(${tarefa.id}); return false;" class="badge badge-warning" style="text-decoration:none; cursor:pointer;">
        Pendente
    </a>
</td>
<td></td>
<td>
    <div class="actions">
        <a href="mostraTarefa?id=${tarefa.id}" class="btn btn-outline btn-sm">Alterar</a>
        <a href="#" onclick="if(confirm('Tem certeza que deseja remover esta tarefa?')) window.location='removeTarefa?id=${tarefa.id}'; return false;" class="btn btn-danger btn-sm">Remover</a>
    </div>
</td>
