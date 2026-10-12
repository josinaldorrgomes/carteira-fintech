package br.edu.unievangelica.carteira.pagamento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

/**
 * Testes simples do pacote de pagamentos.
 *
 * <p>Mostram, na pratica, os conceitos da aula: a fabrica devolve a estrategia
 * certa, o pagamento e feito pelo tipo do contrato (polimorfismo) e cada
 * estrategia tambem cumpre o contrato Auditavel (implementacao multipla).</p>
 */
class PagamentoFactoryTest {

    private final PagamentoFactory factory = new PagamentoFactory();

    @Test
    void fabricaCriaEstrategiaCorretaParaCadaTipo() {
        assertEquals(TipoPagamento.PIX, factory.criar(TipoPagamento.PIX).getTipo());
        assertEquals(TipoPagamento.CARTAO, factory.criar(TipoPagamento.CARTAO).getTipo());
        assertEquals(TipoPagamento.BOLETO, factory.criar(TipoPagamento.BOLETO).getTipo());
    }

    @Test
    void pagamentoPeloContratoRetornaComprovante() {
        // Tratamos o objeto pelo tipo da INTERFACE, nao pela classe concreta.
        MeioDePagamento meio = factory.criar(TipoPagamento.PIX);
        ResultadoPagamento resultado = meio.pagar(new BigDecimal("50.00"));

        assertTrue(resultado.isAprovado());
        assertNotNull(resultado.getComprovante());
    }

    @Test
    void estrategiasTambemSaoAuditaveis() {
        // Implementacao multipla: um MeioDePagamento tambem e Auditavel.
        MeioDePagamento meio = factory.criar(TipoPagamento.CARTAO);
        assertTrue(meio instanceof Auditavel);
    }
}
