package br.edu.unievangelica.carteira.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.edu.unievangelica.carteira.model.Usuario;
import br.edu.unievangelica.carteira.repository.UsuarioRepository;

/**
 * Cria um usuario inicial na primeira execucao, para o aluno conseguir logar.
 *
 * <p>Implementa {@link CommandLineRunner} (mais uma interface!): o Spring chama
 * o metodo {@code run} automaticamente quando a aplicacao sobe.</p>
 *
 * <p>Seguranca: a senha vem de variavel de ambiente (fora do codigo) e e
 * guardada apenas como HASH BCrypt. O usuario padrao serve so para
 * desenvolvimento/demonstracao.</p>
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    public DataSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByUsername(adminUsername)) {
            String hash = passwordEncoder.encode(adminPassword);
            usuarioRepository.save(new Usuario(adminUsername, hash, "USER"));
        }
    }
}
