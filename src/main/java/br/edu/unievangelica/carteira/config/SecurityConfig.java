package br.edu.unievangelica.carteira.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuracao de seguranca (Spring Security).
 *
 * <p>Boas praticas aplicadas aqui:</p>
 * <ul>
 *   <li>Login por formulario; todas as paginas exigem autenticacao, exceto a
 *       pagina de login e os arquivos estaticos (CSS).</li>
 *   <li>Protecao CSRF permanece ATIVADA (padrao do Spring Security).</li>
 *   <li>As senhas sao verificadas contra um HASH BCrypt (ver {@link #passwordEncoder()}).</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
        // Observacao: NAO desabilitamos o CSRF. Ele protege contra requisicoes
        // forjadas e vem ligado por padrao; os formularios Thymeleaf ja enviam o token.
        return http.build();
    }

    /**
     * Codificador de senhas BCrypt.
     *
     * <p>BCrypt aplica um "sal" aleatorio e e lento de proposito, dificultando
     * ataques de forca bruta. A senha original nunca e guardada — apenas o hash.</p>
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
