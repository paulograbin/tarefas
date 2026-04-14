package br.com.caelum.tarefas.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.caelum.tarefas.modelo.Tarefa;

public class BuscaTarefas {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tarefas");
		EntityManager manager = factory.createEntityManager();

		TypedQuery<Tarefa> query = manager.createQuery("select t from Tarefa as t where t.finalizado = :paramFinalizado", Tarefa.class);
		query.setParameter("paramFinalizado", false);

		List<Tarefa> lista = query.getResultList();
		System.out.println(lista.size() + " tarefa(s) encontrada(s)!");
		
		for (Tarefa t : lista) {
			System.out.println(t.getDescricao());
		}
		
		manager.close();
	}
}
