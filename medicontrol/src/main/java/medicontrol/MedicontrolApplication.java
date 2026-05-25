// Define o pacote principal da aplicação
package medicontrol;

// Importa a classe responsável por iniciar o Spring Boot
import org.springframework.boot.SpringApplication;

// Importa a anotação principal do Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Indica que esta é a classe principal da aplicação Spring Boot
@SpringBootApplication
public class MedicontrolApplication {

	// Método principal da aplicação Java
	// Todo programa Java inicia por aqui
	public static void main(String[] args) {

		// Inicia a aplicação Spring Boot
		SpringApplication.run(MedicontrolApplication.class, args);
	}

}
