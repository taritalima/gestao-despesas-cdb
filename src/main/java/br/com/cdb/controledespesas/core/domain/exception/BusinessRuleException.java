package br.com.cdb.controledespesas.core.domain.exception;


public class BusinessRuleException  extends RuntimeException{
    public BusinessRuleException(String message) {
        super(message);
    }
}
