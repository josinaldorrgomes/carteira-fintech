package br.edu.unievangelica.carteira.pagamento;

import java.math.BigDecimal;

/**
 * CONTRATO central da aula (Semana 11): o que todo meio de pagamento precisa
 * saber fazer — sem dizer COMO faz.
 *
 * <p>Esta e uma <b>interface</b>. Ela lista apenas as assinaturas dos metodos;
 * cada classe que a implementa (Pix, Cartao, Boleto) fornece a sua propria
 * logica. Assim o resto do sistema depende do contrato, e nao de uma
 * implementacao concreta.</p>
 *
 * <p>No padrao de projeto <b>Strategy</b>, esta interface e a "estrategia":
 * cada implementacao e uma forma diferente de executar a mesma tarefa
 * (processar um pagamento), e podem ser trocadas livremente.</p>
 */
public interface MeioDePagamento {

    /**
     * Processa um pagamento do valor informado.
     *
     * @param valor quantia a ser paga (use BigDecimal para dinheiro, nunca double)
     * @return o resultado, com o comprovante ou o motivo da recusa
     */
    ResultadoPagamento pagar(BigDecimal valor);

    /** Identifica qual meio de pagamento e este. */
    TipoPagamento getTipo();

    /**
     * Metodo DEFAULT (recurso do Java 8): ja vem com um corpo-padrao.
     *
     * <p>Toda classe que implementa a interface ganha este metodo de graca e
     * pode sobrescreve-lo se quiser. Isso permite evoluir o contrato sem
     * quebrar as classes que ja o implementam. (Detalhado no PDF complementar.)</p>
     */
    default String rotulo() {
        return "Pagamento via " + getTipo();
    }
}
