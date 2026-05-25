// Define o pacote onde esta classe está localizada
package medicontrol.entity;

// Classe que representa um medicamento do sistema
public class Medicamento {

    // Identificador único do medicamento
    private Long id;

    // Nome do medicamento
    private String nome;

    // Dosagem do medicamento
    // Exemplo: 500mg
    private String dosagem;

    // Horário em que o medicamento deve ser tomado
    private String horario;

    // Observações adicionais
    // Exemplo: Tomar após café
    private String observacao;

    // Construtor vazio da classe
    // Necessário para o Spring Boot criar objetos automaticamente
    public Medicamento() {
    }

    // Retorna o ID do medicamento
    public Long getId() {
        return id;
    }

    // Define o ID do medicamento
    public void setId(Long id) {
        this.id = id;
    }

    // Retorna o nome do medicamento
    public String getNome() {
        return nome;
    }

    // Define o nome do medicamento
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Retorna a dosagem do medicamento
    public String getDosagem() {
        return dosagem;
    }

    // Define a dosagem do medicamento
    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    // Retorna o horário do medicamento
    public String getHorario() {
        return horario;
    }

    // Define o horário do medicamento
    public void setHorario(String horario) {
        this.horario = horario;
    }

    // Retorna a observação do medicamento
    public String getObservacao() {
        return observacao;
    }

    // Define a observação do medicamento
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
