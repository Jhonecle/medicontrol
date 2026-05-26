// Define o pacote da classe
package medicontrol.controller;

// Importa entidade Medicamento
import medicontrol.entity.Medicamento;

// Importa Service
import medicontrol.service.MedicamentoService;

// Importa validações
import jakarta.validation.Valid;

// Importa injeção de dependência
import org.springframework.beans.factory.annotation.Autowired;

// Importa anotações REST
import org.springframework.web.bind.annotation.*;

// Importa List
import java.util.List;

// Define classe como REST Controller
@RestController

// Define rota principal
@RequestMapping("/medicamentos")
public class MedicamentoController {

    // Injeta o Service
    @Autowired
    private MedicamentoService service;

    // ==================================================
    // LISTAR TODOS
    // ==================================================

    // Endpoint GET
    // URL:
    // http://localhost:8080/medicamentos
    @GetMapping
    public List<Medicamento> listarMedicamentos() {

        // Retorna todos os medicamentos
        return service.listarTodos();
    }

    // ==================================================
    // BUSCAR POR ID
    // ==================================================

    // Endpoint GET por ID
    // URL:
    // http://localhost:8080/medicamentos/1
    @GetMapping("/{id}")
    public Medicamento buscarPorId(@PathVariable Long id) {

        // Busca medicamento pelo ID
        return service.buscarPorId(id);
    }

    // ==================================================
    // CADASTRAR
    // ==================================================

    // Endpoint POST
    // URL:
    // http://localhost:8080/medicamentos
    @PostMapping
    public Medicamento cadastrarMedicamento(

            // Valida os campos recebidos
            @Valid @RequestBody Medicamento medicamento) {

        // Salva medicamento
        return service.salvar(medicamento);
    }

    // ==================================================
    // ATUALIZAR
    // ==================================================

    // Endpoint PUT
    // URL:
    // http://localhost:8080/medicamentos/1
    @PutMapping("/{id}")
    public Medicamento atualizarMedicamento(

            // Recebe ID da URL
            @PathVariable Long id,

            // Valida os campos recebidos
            @Valid @RequestBody Medicamento medicamentoAtualizado) {

        // Atualiza medicamento
        return service.atualizar(id, medicamentoAtualizado);
    }

    // ==================================================
    // DELETAR
    // ==================================================

    // Endpoint DELETE
    // URL:
    // http://localhost:8080/medicamentos/1
    @DeleteMapping("/{id}")
    public void deletarMedicamento(@PathVariable Long id) {

        // Remove medicamento
        service.deletar(id);
    }
}