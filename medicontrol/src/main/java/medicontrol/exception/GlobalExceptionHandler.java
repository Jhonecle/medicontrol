// Define pacote da classe
package medicontrol.exception;

// Importa classes de validação
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

// Importa ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Importa Map
import java.util.HashMap;
import java.util.Map;

// Define classe global de tratamento de erros
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==================================================
    // TRATAMENTO DE ERROS DE VALIDAÇÃO
    // ==================================================

    @ExceptionHandler(MethodArgumentNotValidException.class)

    // Retorna HTTP 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)

    public Map<String, String> tratarErrosValidacao(
            MethodArgumentNotValidException ex) {

        // Cria mapa de erros
        Map<String, String> erros = new HashMap<>();

        // Percorre todos os erros encontrados
        ex.getBindingResult().getFieldErrors().forEach(erro -> {

            // Adiciona:
            // campo -> mensagem
            erros.put(
                    erro.getField(),
                    erro.getDefaultMessage()
            );
        });

        // Retorna erros em JSON
        return erros;
    }
}
