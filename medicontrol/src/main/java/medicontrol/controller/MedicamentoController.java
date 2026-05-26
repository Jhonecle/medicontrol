// Define o pacote onde a classe está localizada
package medicontrol.controller;

// Importa a entidade Medicamento
import medicontrol.entity.Medicamento;

// Importa o Repository responsável pela comunicação com o banco
import medicontrol.repository.MedicamentoRepository;

// Importa injeção de dependência do Spring
import org.springframework.beans.factory.annotation.Autowired;

// Importa anotações REST do Spring Boot
import org.springframework.web.bind.annotation.*;

// Importa List
import java.util.List;

// Define esta classe como um Controller REST
@RestController

// Define a rota principal da API
// Todas as rotas começarão com:
/*
    http://localhost:8080/medicamentos
*/
@RequestMapping("/medicamentos")
public class MedicamentoController {

    // Injeta automaticamente o Repository
    @Autowired
    private MedicamentoRepository repository;

    // ==================================================
    // LISTAR TODOS OS MEDICAMENTOS
    // ==================================================

    // Endpoint GET
    // URL:
    // http://localhost:8080/medicamentos
    @GetMapping
    public List<Medicamento> listarMedicamentos() {

        // Retorna todos os medicamentos cadastrados no banco
        return repository.findAll();
    }

    // ==================================================
    // BUSCAR MEDICAMENTO POR ID
    // ==================================================

    // Endpoint GET por ID
    // URL:
    // http://localhost:8080/medicamentos/1
    @GetMapping("/{id}")
    public Medicamento buscarPorId(

            // Recebe o ID enviado na URL
            @PathVariable Long id) {

        // Busca o medicamento pelo ID
        // Caso não encontre, retorna null
        return repository.findById(id).orElse(null);
    }

    // ==================================================
    // CADASTRAR MEDICAMENTO
    // ==================================================

    // Endpoint POST
    // URL:
    // http://localhost:8080/medicamentos
    @PostMapping
    public Medicamento cadastrarMedicamento(

            // Recebe os dados enviados em JSON
            @RequestBody Medicamento medicamento) {

        // Salva o medicamento no PostgreSQL
        return repository.save(medicamento);
    }

    // ==================================================
    // ATUALIZAR MEDICAMENTO
    // ==================================================

    // Endpoint PUT
    // URL:
    // http://localhost:8080/medicamentos/1
    @PutMapping("/{id}")
    public Medicamento atualizarMedicamento(

            // Recebe o ID enviado na URL
            @PathVariable Long id,

            // Recebe os novos dados enviados no JSON
            @RequestBody Medicamento medicamentoAtualizado) {

        // Busca o medicamento no banco
        Medicamento medicamento = repository.findById(id).orElse(null);

        // Verifica se o medicamento existe
        if (medicamento != null) {

            // Atualiza os dados
            medicamento.setNome(medicamentoAtualizado.getNome());
            medicamento.setDosagem(medicamentoAtualizado.getDosagem());
            medicamento.setHorario(medicamentoAtualizado.getHorario());
            medicamento.setObservacao(medicamentoAtualizado.getObservacao());

            // Salva as alterações no banco
            return repository.save(medicamento);
        }

        // Retorna null caso o medicamento não exista
        return null;
    }

    // ==================================================
    // DELETAR MEDICAMENTO
    // ==================================================

    // Endpoint DELETE
    // URL:
    // http://localhost:8080/medicamentos/1
    @DeleteMapping("/{id}")
    public void deletarMedicamento(

            // Recebe o ID enviado na URL
            @PathVariable Long id) {

        // Remove o medicamento do banco
        repository.deleteById(id);
    }
}