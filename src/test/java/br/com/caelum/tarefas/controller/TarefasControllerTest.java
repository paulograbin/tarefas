package br.com.caelum.tarefas.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.caelum.tarefas.dao.JdbcTarefaDao;
import br.com.caelum.tarefas.modelo.Tarefa;

public class TarefasControllerTest {

    private MockMvc mockMvc;
    private JdbcTarefaDao dao;

    @Before
    public void setUp() {
        dao = mock(JdbcTarefaDao.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new TarefasController(dao))
                .build();
    }

    // --- listaTarefas ---

    @Test
    public void listaTarefas_returnsListView() throws Exception {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setDescricao("Estudar Spring");
        when(dao.lista()).thenReturn(Arrays.asList(tarefa));

        mockMvc.perform(get("/listaTarefas"))
                .andExpect(status().isOk())
                .andExpect(view().name("tarefa/lista"))
                .andExpect(model().attributeExists("tarefas"));
    }

    @Test
    public void listaTarefas_emptyList() throws Exception {
        when(dao.lista()).thenReturn(Collections.<Tarefa>emptyList());

        mockMvc.perform(get("/listaTarefas"))
                .andExpect(status().isOk())
                .andExpect(view().name("tarefa/lista"))
                .andExpect(model().attribute("tarefas", Collections.emptyList()));
    }

    // --- novaTarefa ---

    @Test
    public void novaTarefa_returnsFormView() throws Exception {
        mockMvc.perform(get("/novaTarefa"))
                .andExpect(status().isOk())
                .andExpect(view().name("tarefa/formulario"));
    }

    // --- adicionaTarefa ---

    @Test
    public void adicionaTarefa_withValidData_redirectsToList() throws Exception {
        mockMvc.perform(post("/adicionaTarefa").param("descricao", "Tarefa valida"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("listaTarefas?adicionada=1"));

        verify(dao).adiciona(any(Tarefa.class));
    }

    @Test
    public void adicionaTarefa_withShortDescription_returnsForm() throws Exception {
        mockMvc.perform(post("/adicionaTarefa").param("descricao", "ab"))
                .andExpect(status().isOk())
                .andExpect(view().name("tarefa/formulario"));

        verify(dao, never()).adiciona(any(Tarefa.class));
    }

    @Test
    public void adicionaTarefa_withEmptyDescription_returnsForm() throws Exception {
        mockMvc.perform(post("/adicionaTarefa").param("descricao", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("tarefa/formulario"));

        verify(dao, never()).adiciona(any(Tarefa.class));
    }

    // --- mostraTarefa ---

    @Test
    public void mostraTarefa_populatesModel() throws Exception {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setDescricao("Estudar Hibernate");
        when(dao.buscaPorId(1L)).thenReturn(tarefa);

        mockMvc.perform(get("/mostraTarefa").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("tarefa/mostra"))
                .andExpect(model().attribute("tarefa", tarefa));
    }

    // --- finalizaTarefa ---

    @Test
    public void finalizaTarefa_finalizesAndReturnsView() throws Exception {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setFinalizado(true);
        when(dao.buscaPorId(1L)).thenReturn(tarefa);

        mockMvc.perform(get("/finalizaTarefa").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("tarefa/finalizada"))
                .andExpect(model().attribute("tarefa", tarefa));

        verify(dao).finaliza(1L);
    }

    // --- reabreTarefa ---

    @Test
    public void reabreTarefa_reopensAndReturnsView() throws Exception {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setFinalizado(false);
        when(dao.buscaPorId(1L)).thenReturn(tarefa);

        mockMvc.perform(get("/reabreTarefa").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("tarefa/reaberta"))
                .andExpect(model().attribute("tarefa", tarefa));

        verify(dao).reabrir(1L);
    }

    // --- alteraTarefa ---

    @Test
    public void alteraTarefa_updatesAndRedirects() throws Exception {
        mockMvc.perform(post("/alteraTarefa")
                        .param("id", "1")
                        .param("descricao", "Descricao atualizada"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("listaTarefas?alterada=1"));

        verify(dao).altera(any(Tarefa.class));
    }

    // --- removeTarefa ---

    @Test
    public void removeTarefa_deletesAndRedirects() throws Exception {
        mockMvc.perform(get("/removeTarefa").param("id", "1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("listaTarefas?removida=1"));

        verify(dao).remove(any(Tarefa.class));
    }
}
