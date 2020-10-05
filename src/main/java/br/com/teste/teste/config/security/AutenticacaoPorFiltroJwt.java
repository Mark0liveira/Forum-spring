package br.com.teste.teste.config.security;

import br.com.teste.teste.model.Usuario;
import br.com.teste.teste.repository.IUsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoPorFiltroJwt extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final IUsuarioRepository iUsuarioRepository;

    public AutenticacaoPorFiltroJwt(JwtService jwtService, IUsuarioRepository iUsuarioRepository)
    {
        this.jwtService = jwtService;
        this.iUsuarioRepository = iUsuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        String token = recuperarToken(httpServletRequest);
        boolean valido = jwtService.validarToken(token);
        if (valido) {
            autenticarCliente(token);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void autenticarCliente(String token) {
        Long usuarioId = jwtService.recuperarUsuarioId(token);
        Usuario usuario = iUsuarioRepository.findById(usuarioId).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest httpServletRequest)
    {
        String authorizarion = httpServletRequest.getHeader("Authorization");
        if (authorizarion == null || authorizarion.isEmpty() || !authorizarion.startsWith("Bearer"))
            return null;

        return authorizarion.substring(7);
    }
}
