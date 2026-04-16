package br.com.caelum.tarefas.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.caelum.tarefas.dao.JdbcUsuarioDao;
import br.com.caelum.tarefas.modelo.Usuario;

public class LoginControllerTest {

    private MockMvc mockMvc;
    private JdbcUsuarioDao dao;

    @Before
    public void setUp() {
        dao = mock(JdbcUsuarioDao.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new LoginController(dao))
                .build();
    }

    @Test
    public void loginForm_returnsLoginView() throws Exception {
        mockMvc.perform(get("/loginForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("formulario-login"));
    }

    @Test
    public void efetuaLogin_withValidCredentials_redirectsToTarefas() throws Exception {
        when(dao.existeUsuario(any(Usuario.class))).thenReturn(true);

        mockMvc.perform(post("/efetuaLogin")
                        .param("login", "admin")
                        .param("senha", "admin"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("listaTarefas"));
    }

    @Test
    public void efetuaLogin_withValidCredentials_setsSessionAttribute() throws Exception {
        when(dao.existeUsuario(any(Usuario.class))).thenReturn(true);
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(post("/efetuaLogin")
                        .param("login", "admin")
                        .param("senha", "admin")
                        .session(session))
                .andExpect(status().isFound());

        assertNotNull(session.getAttribute("usuarioLogado"));
    }

    @Test
    public void efetuaLogin_withInvalidCredentials_redirectsWithError() throws Exception {
        when(dao.existeUsuario(any(Usuario.class))).thenReturn(false);

        mockMvc.perform(post("/efetuaLogin")
                        .param("login", "admin")
                        .param("senha", "wrong"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("loginForm?erro=1"));
    }

    @Test
    public void efetuaLogin_withInvalidCredentials_doesNotSetSession() throws Exception {
        when(dao.existeUsuario(any(Usuario.class))).thenReturn(false);
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(post("/efetuaLogin")
                        .param("login", "admin")
                        .param("senha", "wrong")
                        .session(session))
                .andExpect(status().isFound());

        assertNull(session.getAttribute("usuarioLogado"));
    }

    @Test
    public void logout_redirectsToLoginForm() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("loginForm"));
    }
}
