// Define o pacote da classe
package medicontrol.controller;

// Importa DTO
import medicontrol.dto.MedicamentoDTO;

// Importa entidade Medicamento
import medicontrol.entity.Medicamento;

// Importa classe de resposta padrão da API
import medicontrol.response.ApiResponse;

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
    public ApiResponse<List<Medicamento>> listarMedicamentos() {

        // Busca todos os medicamentos
        List<Medicamento> lista = service.listarTodos();

        // Retorna resposta padronizada
        return new ApiResponse<>(
                true,
                "Medicamentos listados com sucesso",
                lista
        );
    }

    // ==================================================
    // BUSCAR POR ID
    // ==================================================

    // Endpoint GET por ID
    // URL:
    // http://localhost:8080/medicamentos/1
    @GetMapping("/{id}")
    public ApiResponse<Medicamento> buscarPorId(
            @PathVariable Long id) {

        // Busca medicamento pelo ID
        Medicamento medicamento = service.buscarPorId(id);

        // Retorna resposta padronizada
        return new ApiResponse<>(
                true,
                "Medicamento encontrado com sucesso",
                medicamento
        );
    }

    // ==================================================
    // CADASTRAR
    // ==================================================

    // Endpoint POST
    // URL:
    // http://localhost:8080/medicamentos
    @PostMapping
    public ApiResponse<Medicamento> cadastrarMedicamento(

            // Recebe DTO validado
            @Valid @RequestBody MedicamentoDTO dto) {

        // Cria objeto da entidade
        Medicamento medicamento = new Medicamento();

        // Copia dados do DTO para entidade
        medicamento.setNome(dto.getNome());
        medicamento.setDosagem(dto.getDosagem());
        medicamento.setHorario(dto.getHorario());
        medicamento.setObservacao(dto.getObservacao());

        // Salva medicamento no banco
        Medicamento salvo = service.salvar(medicamento);

        // Retorna resposta padronizada
        return new ApiResponse<>(
                true,
                "Medicamento cadastrado com sucesso",
                salvo
        );
    }

    // ==================================================
    // ATUALIZAR
    // ==================================================

    // Endpoint PUT
    // URL:
    // http://localhost:8080/medicamentos/1
    @PutMapping("/{id}")
    public ApiResponse<Medicamento> atualizarMedicamento(

            // Recebe ID da URL
            @PathVariable Long id,

            // Recebe DTO validado
            @Valid @RequestBody MedicamentoDTO dto) {

        // Cria entidade
        Medicamento medicamentoAtualizado = new Medicamento();

        // Copia dados do DTO para entidade
        medicamentoAtualizado.setNome(dto.getNome());
        medicamentoAtualizado.setDosagem(dto.getDosagem());
        medicamentoAtualizado.setHorario(dto.getHorario());
        medicamentoAtualizado.setObservacao(dto.getObservacao());

        // Atualiza medicamento
        Medicamento atualizado = service.atualizar(id, medicamentoAtualizado);

        // Retorna resposta padronizada
        return new ApiResponse<>(
                true,
                "Medicamento atualizado com sucesso",
                atualizado
        );
    }

    // ==================================================
    // DELETAR
    // ==================================================

    // Endpoint DELETE
    // URL:
    // http://localhost:8080/medicamentos/1
    @DeleteMapping("/{id}")
    public ApiResponse<String> deletarMedicamento(
            @PathVariable Long id) {

        // Remove medicamento
        service.deletar(id);

        // Retorna resposta padronizada
        return new ApiResponse<>(
                true,
                "Medicamento removido com sucesso",
                "ID removido: " + id
        );
    }
}