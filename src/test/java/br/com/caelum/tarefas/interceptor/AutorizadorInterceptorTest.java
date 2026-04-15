package br.com.caelum.tarefas.interceptor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

public class AutorizadorInterceptorTest {

    private AutorizadorInterceptor interceptor;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @Before
    public void setUp() {
        interceptor = new AutorizadorInterceptor();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void allowsLoginFormUrl() throws Exception {
        when(request.getRequestURI()).thenReturn("/loginForm");

        assertTrue(interceptor.preHandle(request, response, null));
    }

    @Test
    public void allowsEfetuaLoginUrl() throws Exception {
        when(request.getRequestURI()).thenReturn("/efetuaLogin");

        assertTrue(interceptor.preHandle(request, response, null));
    }

    @Test
    public void allowsResourceUrls() throws Exception {
        when(request.getRequestURI()).thenReturn("/resources/css/style.css");

        assertTrue(interceptor.preHandle(request, response, null));
    }

    @Test
    public void allowsAuthenticatedUser() throws Exception {
        when(request.getRequestURI()).thenReturn("/listaTarefas");
        when(session.getAttribute("usuarioLogado")).thenReturn(new Object());

        assertTrue(interceptor.preHandle(request, response, null));
    }

    @Test
    public void redirectsUnauthenticatedUserToLogin() throws Exception {
        when(request.getRequestURI()).thenReturn("/listaTarefas");
        when(session.getAttribute("usuarioLogado")).thenReturn(null);
        when(request.getRequestedSessionId()).thenReturn(null);

        assertFalse(interceptor.preHandle(request, response, null));
        verify(response).sendRedirect("loginForm");
    }

    @Test
    public void redirectsExpiredSessionWithFlag() throws Exception {
        when(request.getRequestURI()).thenReturn("/listaTarefas");
        when(session.getAttribute("usuarioLogado")).thenReturn(null);
        when(request.getRequestedSessionId()).thenReturn("old-session-id");
        when(request.isRequestedSessionIdValid()).thenReturn(false);

        assertFalse(interceptor.preHandle(request, response, null));
        verify(response).sendRedirect("loginForm?expirado=1");
    }

    @Test
    public void validSessionIdButNoUserStillRedirects() throws Exception {
        when(request.getRequestURI()).thenReturn("/novaTarefa");
        when(session.getAttribute("usuarioLogado")).thenReturn(null);
        when(request.getRequestedSessionId()).thenReturn("current-session-id");
        when(request.isRequestedSessionIdValid()).thenReturn(true);

        assertFalse(interceptor.preHandle(request, response, null));
        verify(response).sendRedirect("loginForm");
    }
}
