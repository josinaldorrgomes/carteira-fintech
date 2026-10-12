package br.edu.unievangelica.carteira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Usuario que faz login no sistema.
 *
 * <p>Esta e uma entidade JPA: cada objeto vira uma linha na tabela "usuario".
 * O campo {@code senha} guarda apenas o HASH (BCrypt), nunca a senha em texto
 * puro — boa pratica de seguranca fundamental.</p>
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String username;

    /** Hash BCrypt da senha (nunca a senha em texto puro). */
    @Column(nullable = false, length = 100)
    private String senha;

    @Column(nullable = false, length = 20)
    private String papel = "USER";

    public Usuario() {
        // Construtor vazio exigido pelo JPA.
    }

    public Usuario(String username, String senha, String papel) {
        this.username = username;
        this.senha = senha;
        this.papel = papel;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }
}
