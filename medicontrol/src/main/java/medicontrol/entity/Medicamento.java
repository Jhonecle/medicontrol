// Define o pacote da classe
package medicontrol.entity;

// Importa anotações JPA
import jakarta.persistence.*;

// Importa validações
import jakarta.validation.constraints.NotBlank;

// Define esta classe como entidade do banco
@Entity

// Define nome da tabela
@Table(name = "medicamentos")
public class Medicamento {

    // Define chave primária
    @Id

    // Gera ID automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==================================================
    // NOME
    // ==================================================

    // Campo obrigatório
    @NotBlank(message = "O nome do medicamento é obrigatório")
    private String nome;

    // ==================================================
    // DOSAGEM
    // ==================================================

    // Campo obrigatório
    @NotBlank(message = "A dosagem é obrigatória")
    private String dosagem;

    // ==================================================
    // HORÁRIO
    // ==================================================

    // Campo obrigatório
    @NotBlank(message = "O horário é obrigatório")
    private String horario;

    // ==================================================
    // OBSERVAÇÃO
    // ==================================================

    private String observacao;

    // ==================================================
    // CONSTRUTOR VAZIO
    // ==================================================
    public Medicamento() {
    }

    // ==================================================
    // GETTERS E SETTERS
    // ==================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
