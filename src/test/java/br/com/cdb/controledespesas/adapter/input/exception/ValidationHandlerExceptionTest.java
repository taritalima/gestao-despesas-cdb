package br.com.cdb.controledespesas.adapter.input.exception;

import br.com.cdb.controledespesas.adapter.input.response.ErroResponse;
import br.com.cdb.controledespesas.core.domain.exception.BusinessRuleException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidationExceptionHandlerTest {

    private ValidationExceptionHandler handler;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new ValidationExceptionHandler();
        when(request.getRequestURI()).thenReturn("/teste");
    }

    @Test
    void testExceptionHandlerMethodArgumentNotValidException() {
        // mock dos erros de validação
        FieldError fieldError = new FieldError("objeto", "campo", "Erro de validação");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ErroResponse response = handler.exceptionHandler(ex, request);

        assertNotNull(response);
        assertEquals(400, response.getStatus());
        assertEquals("/teste", response.getPath());
        assertTrue(response.getErros().containsKey("campo"));
        assertEquals("Erro de validação", response.getErros().get("campo"));
    }

    @Test
    void testHandleBusinessRuleException() {
        BusinessRuleException ex = new BusinessRuleException("Regra de negócio violada");

        ErroResponse response = handler.handleBusinessRuleException(ex, request);

        assertNotNull(response);
        assertEquals(400, response.getStatus());
        assertEquals("/teste", response.getPath());
        Map<String, String> erros = response.getErros();
        assertTrue(erros.containsKey("mensagem"));
        assertEquals("Regra de negócio violada", erros.get("mensagem"));
    }
}
