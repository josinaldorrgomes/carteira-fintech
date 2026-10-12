package br.edu.unievangelica.carteira.pagamento;

/**
 * Tipos de meio de pagamento aceitos pela carteira.
 *
 * <p>Um enum e uma lista fixa e segura de opcoes. Usar enum (em vez de texto
 * solto como "pix" ou "PIX") evita erros de digitacao e deixa claro, para quem
 * le o codigo, exatamente quais sao as opcoes possiveis.</p>
 */
public enum TipoPagamento {
    PIX,
    CARTAO,
    BOLETO
}
