package br.edu.unievangelica.carteira.pagamento;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementacao do contrato {@link MeioDePagamento} para cartao de credito.
 *
 * <p>Boa pratica de seguranca demonstrada aqui: o numero do cartao NUNCA e
 * exibido ou registrado por inteiro — mostramos apenas os 4 ultimos digitos
 * ("mascaramento"). Em producao, o numero completo sequer trafegaria pela
 * aplicacao (seria tokenizado por um provedor PCI-DSS).</p>
 */
public class Cartao implements MeioDePagamento, Auditavel {

    private final String ultimosQuatroDigitos;

    public Cartao() {
        // Simulacao: sorteia 4 digitos apenas para o comprovante de exemplo.
        this.ultimosQuatroDigitos = String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
    }

    @Override
    public ResultadoPagamento pagar(BigDecimal valor) {
        String autorizacao = "AUT" + ThreadLocalRandom.current().nextInt(100000, 999999);
        return ResultadoPagamento.sucesso(
                "Cartao final " + ultimosQuatroDigitos + " aprovado. Autorizacao: " + autorizacao);
    }

    @Override
    public TipoPagamento getTipo() {
        return TipoPagamento.CARTAO;
    }

    @Override
    public String descricaoAuditoria() {
        // Apenas o final do cartao entra na trilha de auditoria (dado nao sensivel).
        return "Pagamento via cartao final " + ultimosQuatroDigitos;
    }
}
