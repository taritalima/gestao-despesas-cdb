package br.com.cdb.controledespesas.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ErroDTO {
    private LocalDateTime timestamp;
    private int status;
    private String path;
    private Map<String,String> erros;
}
