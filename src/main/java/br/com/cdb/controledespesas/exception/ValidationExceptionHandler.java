package br.com.cdb.controledespesas.exception;

import br.com.cdb.controledespesas.dto.ErroDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroDTO exceptionHandler(MethodArgumentNotValidException ex, HttpServletRequest request){
        Map<String,String> mapCamposComErro = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            mapCamposComErro.put(fieldName, errorMessage);
        }));
        return new ErroDTO(
                LocalDateTime.now(),

                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                mapCamposComErro
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(BusinessRuleException.class)
    public ErroDTO handleBusinessRuleException(BusinessRuleException ex, HttpServletRequest request) {
        Map<String, String> mapErro = new HashMap<>();
        mapErro.put("mensagem", ex.getMessage());

        return new ErroDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                mapErro
        );
    }
}
