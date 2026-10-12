package br.edu.unievangelica.carteira.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.edu.unievangelica.carteira.pagamento.TipoPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Registro de um pagamento processado (trilha de auditoria).
 *
 * <p>Guardamos o valor como {@link BigDecimal} — NUNCA {@code double} para
 * dinheiro, pois double gera erros de arredondamento (0.1 + 0.2 != 0.3). Esta e
 * uma boa pratica obrigatoria em sistemas financeiros.</p>
 */
@Entity
@Table(name = "transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoPagamento tipo;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private boolean aprovado;

    @Column(nullable = false, length = 255)
    private String comprovante;

    @Column(nullable = false, length = 120)
    private String auditoria;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    public Transacao() {
        // Construtor vazio exigido pelo JPA.
    }

    public Transacao(String usuario, TipoPagamento tipo, BigDecimal valor,
                     boolean aprovado, String comprovante, String auditoria) {
        this.usuario = usuario;
        this.tipo = tipo;
        this.valor = valor;
        this.aprovado = aprovado;
        this.comprovante = comprovante;
        this.auditoria = auditoria;
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public TipoPagamento getTipo() {
        return tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public String getComprovante() {
        return comprovante;
    }

    public String getAuditoria() {
        return auditoria;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
