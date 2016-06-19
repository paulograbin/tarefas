package br.com.caelum.tarefas.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.caelum.tarefas.modelo.Tarefa;

public class GeraTabelas {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tarefas");
		
		EntityManager manager = factory.createEntityManager();
		
		Tarefa tarefa = new Tarefa();
		
		tarefa.setDescricao("blabla bla bla");
		
		manager.getTransaction().begin();
		manager.persist(tarefa);
		
		manager.getTransaction().commit();
		
		factory.close();
	}
}
