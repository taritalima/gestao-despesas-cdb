package br.com.cdb.controledespesas.adapter.input.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ErroResponse {
    private LocalDateTime timestamp;
    private int status;
    private String path;
    private Map<String,String> erros;
}
