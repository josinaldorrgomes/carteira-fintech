package br.edu.unievangelica.carteira.pagamento;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementacao do contrato {@link MeioDePagamento} para boleto bancario.
 *
 * <p>Mesmo contrato, comportamento totalmente diferente de Pix e Cartao:
 * aqui "pagar" significa emitir um boleto com uma linha digitavel.</p>
 */
public class Boleto implements MeioDePagamento, Auditavel {

    @Override
    public ResultadoPagamento pagar(BigDecimal valor) {
        long bloco = ThreadLocalRandom.current().nextLong(10000000000L, 99999999999L);
        String linhaDigitavel = "34191." + (bloco % 100000) + " 00000.000000 00000.000000 1 00000000000000";
        return ResultadoPagamento.sucesso("Boleto emitido. Linha digitavel: " + linhaDigitavel);
    }

    @Override
    public TipoPagamento getTipo() {
        return TipoPagamento.BOLETO;
    }

    @Override
    public String descricaoAuditoria() {
        return "Pagamento via boleto bancario";
    }
}
