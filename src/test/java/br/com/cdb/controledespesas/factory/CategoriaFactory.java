package br.com.cdb.controledespesas.factory;


import br.com.cdb.controledespesas.adapter.input.request.CategoriaRequest;
import br.com.cdb.controledespesas.adapter.input.response.CategoriaResponse;
import br.com.cdb.controledespesas.core.domain.model.Categoria;

import java.util.List;

public class CategoriaFactory {

    public static Categoria createDefaultCategoria() {
        return new Categoria(1L, "Lanches");
    }

    public static Categoria createCustomCategoria(Long id, String nome) {
        return new Categoria(id, nome);
    }

    public static CategoriaRequest createDefaultRequest() {
        CategoriaRequest request = new CategoriaRequest();
        request.setNome("Lanches");
        return request;
    }

    public static CategoriaRequest createCustomRequest(String nome) {
        CategoriaRequest request = new CategoriaRequest();
        request.setNome(nome);
        return request;
    }

    public static CategoriaResponse createDefaultResponse() {
        return new CategoriaResponse(1L, "Lanches");
    }

    public static CategoriaResponse createCustomResponse(Long id, String nome) {
        return new CategoriaResponse(id, nome);
    }

    public static List<Categoria> createListOfCategorias() {
        return List.of(
                new Categoria(1L, "Lanches"),
                new Categoria(2L, "Bebidas"),
                new Categoria(3L, "Doces")
        );
    }
}
