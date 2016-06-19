package br.com.caelum.tarefas.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.caelum.tarefas.dao.JdbcTarefaDao;
import br.com.caelum.tarefas.modelo.Tarefa;

@Controller
public class TarefasController {

	JdbcTarefaDao dao;
	
	@Autowired
	public TarefasController(JdbcTarefaDao dao) {
		this.dao = dao;  
	}
	
	@RequestMapping("finalizaTarefa")
	public String finaliza(Long id, Model model) throws IOException {
		dao.finaliza(id);
		model.addAttribute("tarefa", dao.buscaPorId(id));
		return "tarefa/finalizada";
	}
	
	@RequestMapping("alteraTarefa")
	public String altera(Tarefa tarefa) {
		dao.altera(tarefa);
		return "redirect:listaTarefas";
	}
	
	@RequestMapping("mostraTarefa")
	public String mostra(Long id, Model model) {
		model.addAttribute("tarefa", dao.buscaPorId(id));
		
		return "tarefa/mostra";
	}
	
	@RequestMapping("removeTarefa")
	public String remove(Long id) {
		Tarefa tarefa = new Tarefa();
		tarefa.setId(id);
		
		dao.remove(tarefa);
		return "redirect:listaTarefas";
	}
	
	@RequestMapping("listaTarefas")
	public String lista(Model model) {
		List<Tarefa> tarefas = dao.lista();
		
//		ModelAndView mv = new ModelAndView("tarefas/lista");
//		mv.addObject("tarefas", tarefas);
		
		model.addAttribute("tarefas", tarefas);
		return "tarefa/lista";
	}
	
	@RequestMapping("novaTarefa")
	public String mostraForm() {
		return "tarefa/formulario";
	}
	
	@RequestMapping("adicionaTarefa")
	public String adiciona(@Valid Tarefa tarefa, BindingResult result) {
		if(result.hasErrors()) {
			return "tarefa/formulario";
		}
		
		if(result.hasFieldErrors("descricao")) {
			return "tarefas/formulario";
		}
		
		dao.adiciona(tarefa);
		return "tarefa/adicionada";
	}
}
