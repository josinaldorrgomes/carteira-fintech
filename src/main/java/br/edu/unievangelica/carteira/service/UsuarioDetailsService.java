package br.edu.unievangelica.carteira.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.unievangelica.carteira.model.Usuario;
import br.edu.unievangelica.carteira.repository.UsuarioRepository;

/**
 * Liga o nosso cadastro de usuarios ao login do Spring Security.
 *
 * <p>Mais um exemplo real de interface: {@code UserDetailsService} e um contrato
 * do proprio framework. Ao implementa-lo, ensinamos o Spring Security a carregar
 * um usuario do nosso banco. O framework nao conhece a nossa classe — ele conhece
 * apenas o contrato.</p>
 */
@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado: " + username));

        // Entregamos ao Spring Security o usuario, o HASH da senha e o papel.
        return User.withUsername(usuario.getUsername())
                .password(usuario.getSenha())
                .roles(usuario.getPapel())
                .build();
    }
}
