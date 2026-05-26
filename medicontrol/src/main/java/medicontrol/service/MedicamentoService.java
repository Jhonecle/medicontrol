// Define o pacote da classe
package medicontrol.service;

// Importa entidade Medicamento
import medicontrol.entity.Medicamento;

// Importa Repository
import medicontrol.repository.MedicamentoRepository;

// Importa anotações do Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importa List
import java.util.List;

// Define esta classe como Service do Spring
@Service
public class MedicamentoService {

    // Injeta automaticamente o Repository
    @Autowired
    private MedicamentoRepository repository;

    // ==================================================
    // LISTAR TODOS OS MEDICAMENTOS
    // ==================================================
    public List<Medicamento> listarTodos() {

        // Retorna todos os medicamentos cadastrados
        return repository.findAll();
    }

    // ==================================================
    // BUSCAR MEDICAMENTO POR ID
    // ==================================================
    public Medicamento buscarPorId(Long id) {

        // Busca medicamento pelo ID
        return repository.findById(id).orElse(null);
    }

    // ==================================================
    // SALVAR MEDICAMENTO
    // ==================================================
    public Medicamento salvar(Medicamento medicamento) {

        // Salva medicamento no banco
        return repository.save(medicamento);
    }

    // ==================================================
    // ATUALIZAR MEDICAMENTO
    // ==================================================
    public Medicamento atualizar(Long id, Medicamento medicamentoAtualizado) {

        // Busca medicamento existente
        Medicamento medicamento = repository.findById(id).orElse(null);

        // Verifica se encontrou
        if (medicamento != null) {

            // Atualiza os dados
            medicamento.setNome(medicamentoAtualizado.getNome());
            medicamento.setDosagem(medicamentoAtualizado.getDosagem());
            medicamento.setHorario(medicamentoAtualizado.getHorario());
            medicamento.setObservacao(medicamentoAtualizado.getObservacao());

            // Salva alterações
            return repository.save(medicamento);
        }

        // Retorna null caso não encontre
        return null;
    }

    // ==================================================
    // DELETAR MEDICAMENTO
    // ==================================================
    public void deletar(Long id) {

        // Remove medicamento do banco
        repository.deleteById(id);
    }
}
