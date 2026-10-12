package br.edu.unievangelica.carteira.pagamento;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Implementacao do contrato {@link MeioDePagamento} para pagamentos via Pix.
 *
 * <p>Repare na declaracao: {@code implements MeioDePagamento, Auditavel}. Esta
 * classe assina DOIS contratos ao mesmo tempo (implementacao multipla) e, por
 * isso, e obrigada a fornecer os metodos de ambos.</p>
 */
public class Pix implements MeioDePagamento, Auditavel {

    @Override
    public ResultadoPagamento pagar(BigDecimal valor) {
        // Em um sistema real, aqui chamariamos a API do Pix (Banco Central).
        // Para fins didaticos, geramos um identificador fim-a-fim (E2E) simulado.
        String e2e = "E2E" + UUID.randomUUID().toString().replace("-", "").substring(0, 20);
        return ResultadoPagamento.sucesso("Pix aprovado. Comprovante: " + e2e);
    }

    @Override
    public TipoPagamento getTipo() {
        return TipoPagamento.PIX;
    }

    @Override
    public String descricaoAuditoria() {
        return "Pagamento via Pix (transferencia instantanea)";
    }
}
