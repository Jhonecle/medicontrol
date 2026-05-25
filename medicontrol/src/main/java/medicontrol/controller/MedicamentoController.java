// Define o pacote da classe
package medicontrol.controller;

// Importa a entidade Medicamento
import medicontrol.entity.Medicamento;

// Importa anotação para rota GET
import org.springframework.web.bind.annotation.GetMapping;

// Importa anotação para rota POST
import org.springframework.web.bind.annotation.PostMapping;

// Importa anotação para receber JSON no corpo da requisição
import org.springframework.web.bind.annotation.RequestBody;

// Importa anotação de controller REST
import org.springframework.web.bind.annotation.RestController;

// Importa ArrayList
import java.util.ArrayList;

// Importa List
import java.util.List;

// Define a classe como controller REST
@RestController
public class MedicamentoController {

    // Cria rota GET:
    // http://localhost:8080/medicamentos
    @GetMapping("/medicamentos")
    public List<Medicamento> listarMedicamentos() {

        // Cria lista de medicamentos
        List<Medicamento> lista = new ArrayList<>();

        // Cria objeto medicamento
        Medicamento medicamento = new Medicamento();

        // Define ID
        medicamento.setId(1L);

        // Define nome
        medicamento.setNome("Dipirona");

        // Define dosagem
        medicamento.setDosagem("500mg");

        // Define horário
        medicamento.setHorario("08:00");

        // Define observação
        medicamento.setObservacao("Tomar após café");

        // Adiciona medicamento na lista
        lista.add(medicamento);

        // Retorna lista em JSON
        return lista;
    }

    // Cria rota POST:
    // http://localhost:8080/medicamentos
    @PostMapping("/medicamentos")
    public Medicamento cadastrarMedicamento(

            // Recebe JSON enviado na requisição
            @RequestBody Medicamento medicamento) {

        // Retorna o medicamento recebido
        return medicamento;
    }
}