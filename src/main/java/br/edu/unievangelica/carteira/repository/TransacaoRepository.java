package br.edu.unievangelica.carteira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unievangelica.carteira.model.Transacao;

/**
 * Repositorio de transacoes (padrao Repository).
 *
 * <p>O Spring Data entende o nome do metodo e cria a consulta: aqui, buscar as
 * transacoes de um usuario ordenadas da mais recente para a mais antiga.</p>
 */
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByUsuarioOrderByCriadoEmDesc(String usuario);
}
