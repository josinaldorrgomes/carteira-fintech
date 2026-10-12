package br.edu.unievangelica.carteira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada da aplicacao Spring Boot.
 *
 * <p>O metodo {@code main} liga o servidor web embutido (Tomcat), conecta no
 * banco e deixa a aplicacao pronta em http://localhost:8080.</p>
 */
@SpringBootApplication
public class CarteiraApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarteiraApplication.class, args);
    }
}
