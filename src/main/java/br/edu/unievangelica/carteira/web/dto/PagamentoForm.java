package br.edu.unievangelica.carteira.web.dto;

import java.math.BigDecimal;

import br.edu.unievangelica.carteira.pagamento.TipoPagamento;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * Dados do formulario de pagamento (DTO - Data Transfer Object).
 *
 * <p>As anotacoes de validacao (Bean Validation) garantem que entradas
 * invalidas sejam barradas ANTES de chegar a regra de negocio — boa pratica de
 * seguranca: "nunca confie na entrada do usuario".</p>
 */
public class PagamentoForm {

    @NotNull(message = "Escolha um meio de pagamento.")
    private TipoPagamento tipo;

    @NotNull(message = "Informe o valor.")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
    @DecimalMax(value = "50000.00", message = "O valor maximo por transacao e R$ 50.000,00.")
    private BigDecimal valor;

    public TipoPagamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamento tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
