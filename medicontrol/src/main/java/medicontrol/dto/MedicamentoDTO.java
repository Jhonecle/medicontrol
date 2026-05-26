// Define pacote da classe
package medicontrol.dto;

// Importa validações
import jakarta.validation.constraints.NotBlank;

// Classe DTO do medicamento
public class MedicamentoDTO {

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
    // GETTERS E SETTERS
    // ==================================================

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
