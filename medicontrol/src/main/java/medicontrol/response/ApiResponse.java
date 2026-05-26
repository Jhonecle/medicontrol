// Define pacote
package medicontrol.response;

// Classe genérica de resposta da API
public class ApiResponse<T> {

    // ==================================================
    // INDICA SE OPERAÇÃO FOI REALIZADA
    // ==================================================
    private boolean sucesso;

    // ==================================================
    // MENSAGEM DA API
    // ==================================================
    private String mensagem;

    // ==================================================
    // DADOS RETORNADOS
    // ==================================================
    private T dados;

    // ==================================================
    // CONSTRUTOR VAZIO
    // ==================================================
    public ApiResponse() {
    }

    // ==================================================
    // CONSTRUTOR COMPLETO
    // ==================================================
    public ApiResponse(boolean sucesso, String mensagem, T dados) {

        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.dados = dados;
    }

    // ==================================================
    // GETTERS E SETTERS
    // ==================================================

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public T getDados() {
        return dados;
    }

    public void setDados(T dados) {
        this.dados = dados;
    }
}
