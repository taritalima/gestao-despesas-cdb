package br.com.cdb.controledespesas.exception;


public class BusinessRuleException  extends RuntimeException{
    public BusinessRuleException(String message) {
        super(message);
    }
}
