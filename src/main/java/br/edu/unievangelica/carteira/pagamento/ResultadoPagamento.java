package br.edu.unievangelica.carteira.pagamento;

/**
 * Resultado da tentativa de pagamento: se deu certo e o comprovante gerado.
 *
 * <p>Esta classe e <b>imutavel</b>: os campos sao {@code final} e so tem
 * leitura (getters), sem setters. Objetos imutaveis sao mais simples e seguros,
 * porque ninguem consegue altera-los depois de criados — uma boa pratica
 * importante em sistemas financeiros.</p>
 *
 * <p>Os metodos {@link #sucesso(String)} e {@link #falha(String)} sao
 * "fabricas estaticas": dao nomes claros as duas formas de criar o objeto,
 * deixando o codigo que chama mais legivel (Clean Code).</p>
 */
public final class ResultadoPagamento {

    private final boolean aprovado;
    private final String comprovante;

    private ResultadoPagamento(boolean aprovado, String comprovante) {
        this.aprovado = aprovado;
        this.comprovante = comprovante;
    }

    public static ResultadoPagamento sucesso(String comprovante) {
        return new ResultadoPagamento(true, comprovante);
    }

    public static ResultadoPagamento falha(String motivo) {
        return new ResultadoPagamento(false, motivo);
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public String getComprovante() {
        return comprovante;
    }
}
