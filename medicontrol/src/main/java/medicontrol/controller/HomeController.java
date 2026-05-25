// Define o pacote onde esta classe está localizada
package medicontrol.controller;

// Importa a anotação para criar rotas GET
import org.springframework.web.bind.annotation.GetMapping;

// Importa a anotação que transforma a classe em um controller REST
import org.springframework.web.bind.annotation.RestController;

// Indica que esta classe será um controller REST
// Ela responderá requisições HTTP
@RestController
public class HomeController {

    // Cria uma rota GET para a URL principal "/"
    // Exemplo:
    // http://localhost:8080/
    @GetMapping("/")
    public String home() {

        // Retorna uma mensagem simples em texto
        return "API do sistema de medicamentos funcionando!";
    }
}