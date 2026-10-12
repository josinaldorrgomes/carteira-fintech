package br.edu.unievangelica.carteira.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unievangelica.carteira.model.Usuario;

/**
 * Repositorio de usuarios (padrao Repository).
 *
 * <p>Repare que isto e uma <b>interface</b>: nos so declaramos o contrato, e o
 * Spring Data JPA gera a implementacao sozinho em tempo de execucao. E outro
 * exemplo de "programar para a interface".</p>
 *
 * <p>Seguranca: as consultas geradas usam parametros (prepared statements), o
 * que previne injecao de SQL automaticamente — nunca concatenamos texto do
 * usuario dentro de uma query.</p>
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);
}
