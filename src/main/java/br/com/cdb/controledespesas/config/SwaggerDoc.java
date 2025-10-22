package br.com.cdb.controledespesas.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "API de Controle de Despesas",
                version = "1.0",
                description = """
            Esta API permite gerenciar usuários, categorias e despesas.
            Desenvolvida como parte do projeto de backend com Spring Boot.
        """,
                contact = @Contact(
                        name = "Tarita Lima",
                        email = "tarita36@hotmail.com",
                        url = "https://github.com/taritalima"
                )
        )
)
public interface SwaggerDoc {
}
