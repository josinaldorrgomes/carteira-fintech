package br.edu.unievangelica.carteira.pagamento;

/**
 * Contrato pequeno e separado: "sei descrever o que fiz para a auditoria".
 *
 * <p>Manter este contrato separado de {@link MeioDePagamento} e um exemplo do
 * <b>Principio da Segregacao de Interfaces (ISP)</b>: contratos pequenos e
 * coesos, em vez de uma unica interface gigante. Uma classe implementa apenas
 * os papeis que realmente exerce.</p>
 *
 * <p>Como uma classe pode implementar varias interfaces, Pix, Cartao e Boleto
 * sao, ao mesmo tempo, {@code MeioDePagamento} e {@code Auditavel} — este e o
 * exemplo de <b>implementacao multipla</b> da aula.</p>
 */
public interface Auditavel {

    /** Texto curto, sem dados sensiveis, para registrar na trilha de auditoria. */
    String descricaoAuditoria();
}
