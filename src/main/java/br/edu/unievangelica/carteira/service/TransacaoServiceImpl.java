package br.edu.unievangelica.carteira.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.unievangelica.carteira.model.Transacao;
import br.edu.unievangelica.carteira.pagamento.Auditavel;
import br.edu.unievangelica.carteira.pagamento.MeioDePagamento;
import br.edu.unievangelica.carteira.pagamento.PagamentoFactory;
import br.edu.unievangelica.carteira.pagamento.ResultadoPagamento;
import br.edu.unievangelica.carteira.pagamento.TipoPagamento;
import br.edu.unievangelica.carteira.repository.TransacaoRepository;

/**
 * Implementacao das regras de negocio de transacoes.
 *
 * <p>Esta classe recebe suas dependencias pelo CONSTRUTOR (injecao de
 * dependencia). Ela depende da FABRICA e do REPOSITORIO; nunca faz
 * {@code new Pix()} diretamente. E o Principio da Inversao de Dependencia (DIP):
 * a regra de negocio depende de abstracoes, nao de implementacoes concretas.</p>
 */
@Service
public class TransacaoServiceImpl implements TransacaoService {

    private final PagamentoFactory pagamentoFactory;
    private final TransacaoRepository transacaoRepository;

    public TransacaoServiceImpl(PagamentoFactory pagamentoFactory,
                                TransacaoRepository transacaoRepository) {
        this.pagamentoFactory = pagamentoFactory;
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public Transacao processarPagamento(String usuario, TipoPagamento tipo, BigDecimal valor) {
        // 1) A fabrica entrega a estrategia certa, tratada pelo tipo do contrato.
        MeioDePagamento meio = pagamentoFactory.criar(tipo);

        // 2) Chamamos o contrato, sem saber (nem precisar saber) qual e a classe concreta.
        ResultadoPagamento resultado = meio.pagar(valor);

        // 3) O MESMO objeto tambem e Auditavel (implementacao multipla): aqui o
        //    tratamos por esse outro contrato para obter a descricao de auditoria.
        String auditoria = meio.rotulo();
        if (meio instanceof Auditavel) {
            auditoria = ((Auditavel) meio).descricaoAuditoria();
        }

        // 4) Registramos a transacao (trilha de auditoria).
        Transacao transacao = new Transacao(
                usuario, tipo, valor,
                resultado.isAprovado(), resultado.getComprovante(), auditoria);
        return transacaoRepository.save(transacao);
    }

    @Override
    public List<Transacao> listarDoUsuario(String usuario) {
        return transacaoRepository.findByUsuarioOrderByCriadoEmDesc(usuario);
    }
}
