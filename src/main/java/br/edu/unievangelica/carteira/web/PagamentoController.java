package br.edu.unievangelica.carteira.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.unievangelica.carteira.model.Transacao;
import br.edu.unievangelica.carteira.pagamento.TipoPagamento;
import br.edu.unievangelica.carteira.service.TransacaoService;
import br.edu.unievangelica.carteira.web.dto.PagamentoForm;
import jakarta.validation.Valid;

/**
 * Recebe o formulario de pagamento e aciona a regra de negocio.
 *
 * <p>Fluxo: valida a entrada -> chama {@link TransacaoService} -> mostra o
 * comprovante. O controller nao conhece Pix, Cartao nem Boleto: ele so fala com
 * a interface do servico.</p>
 */
@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final TransacaoService transacaoService;

    public PagamentoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public String pagar(@Valid @ModelAttribute("pagamentoForm") PagamentoForm form,
                        BindingResult resultado,
                        Principal principal,
                        Model model,
                        RedirectAttributes flash) {

        // Se a validacao falhou, volta para o painel mostrando os erros.
        if (resultado.hasErrors()) {
            model.addAttribute("transacoes", transacaoService.listarDoUsuario(principal.getName()));
            model.addAttribute("tipos", TipoPagamento.values());
            return "index";
        }

        Transacao transacao = transacaoService.processarPagamento(
                principal.getName(), form.getTipo(), form.getValor());

        // Mensagem de sucesso que sobrevive ao redirect (padrao PRG).
        flash.addFlashAttribute("mensagem", transacao.getComprovante());
        return "redirect:/";
    }
}
