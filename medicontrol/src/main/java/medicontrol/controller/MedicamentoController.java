// Define o pacote onde essa classe está localizada
package medicontrol.controller;

// Importa a classe Medicamento criada no projeto
import medicontrol.entity.Medicamento;

// Importa a anotação para criar rotas GET
import org.springframework.web.bind.annotation.GetMapping;

// Importa a anotação que transforma a classe em um controller REST
import org.springframework.web.bind.annotation.RestController;

// Importa a classe ArrayList
import java.util.ArrayList;

// Importa a interface List
import java.util.List;

// Indica que esta classe será um controller REST
// Ou seja, responderá requisições HTTP
@RestController
public class MedicamentoController {

    // Cria uma rota GET no endereço:
    // http://localhost:8080/medicamentos
    @GetMapping("/medicamentos")
    public List<Medicamento> listarMedicamentos() {

        // Cria uma lista vazia de medicamentos
        List<Medicamento> lista = new ArrayList<>();

        // Cria um objeto do tipo Medicamento
        Medicamento medicamento = new Medicamento();

        // Define o ID do medicamento
        medicamento.setId(1L);

        // Define o nome do medicamento
        medicamento.setNome("Dipirona");

        // Define a dosagem
        medicamento.setDosagem("500mg");

        // Define o horário do medicamento
        medicamento.setHorario("08:00");

        // Define uma observação
        medicamento.setObservacao("Tomar após café");

        // Adiciona o medicamento dentro da lista
        lista.add(medicamento);

        // Retorna a lista em formato JSON
        return lista;
    }
}