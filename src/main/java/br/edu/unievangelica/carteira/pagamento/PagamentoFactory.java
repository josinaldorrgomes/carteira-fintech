package br.edu.unievangelica.carteira.pagamento;

import org.springframework.stereotype.Component;

/**
 * FACTORY (padrao de projeto Factory Method): cria o meio de pagamento certo a
 * partir do tipo escolhido pelo usuario.
 *
 * <p>Quem chama nao usa {@code new Pix()} espalhado pelo codigo. Pede a fabrica
 * um {@link MeioDePagamento} e recebe a implementacao adequada. O resultado e
 * sempre tratado pelo tipo do contrato (a interface), nunca pelo tipo concreto.</p>
 *
 * <p>Isto ilustra o Principio Aberto-Fechado (o "O" de SOLID): para aceitar um
 * novo meio de pagamento, cria-se uma nova classe e adiciona-se um caso aqui —
 * o restante do sistema (service, controller) nao muda.</p>
 */
@Component
public class PagamentoFactory {

    public MeioDePagamento criar(TipoPagamento tipo) {
        // switch classico: facil de ler para quem esta comecando.
        switch (tipo) {
            case PIX:
                return new Pix();
            case CARTAO:
                return new Cartao();
            case BOLETO:
                return new Boleto();
            default:
                // Protege contra um tipo novo que alguem esqueceu de tratar.
                throw new IllegalArgumentException("Tipo de pagamento nao suportado: " + tipo);
        }
    }
}
