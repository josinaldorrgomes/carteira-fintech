package br.edu.unievangelica.carteira.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.unievangelica.carteira.pagamento.TipoPagamento;
import br.edu.unievangelica.carteira.service.TransacaoService;
import br.edu.unievangelica.carteira.web.dto.PagamentoForm;

/**
 * Controla as paginas de login e do painel (dashboard).
 *
 * <p>Depende da INTERFACE {@link TransacaoService}, nunca da classe concreta
 * que a implementa — "programar para a interface".</p>
 */
@Controller
public class HomeController {

    private final TransacaoService transacaoService;

    public HomeController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        // principal.getName() = usuario autenticado (vindo do Spring Security).
        model.addAttribute("transacoes", transacaoService.listarDoUsuario(principal.getName()));
        model.addAttribute("tipos", TipoPagamento.values());
        if (!model.containsAttribute("pagamentoForm")) {
            model.addAttribute("pagamentoForm", new PagamentoForm());
        }
        return "index";
    }
}
