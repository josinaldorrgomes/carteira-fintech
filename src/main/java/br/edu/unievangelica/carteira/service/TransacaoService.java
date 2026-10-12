package br.edu.unievangelica.carteira.service;

import java.math.BigDecimal;
import java.util.List;

import br.edu.unievangelica.carteira.model.Transacao;
import br.edu.unievangelica.carteira.pagamento.TipoPagamento;

/**
 * Contrato do servico de transacoes (regras de negocio dos pagamentos).
 *
 * <p>O controller (camada web) vai depender DESTA interface, e nao da classe
 * concreta que a implementa. Isso e "programar para a interface" e deixa o
 * sistema facil de testar e de trocar de implementacao.</p>
 */
public interface TransacaoService {

    Transacao processarPagamento(String usuario, TipoPagamento tipo, BigDecimal valor);

    List<Transacao> listarDoUsuario(String usuario);
}
