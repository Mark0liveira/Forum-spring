package br.com.teste.teste.config.security;

import br.com.teste.teste.model.Usuario;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${teste.jwt.expiration}")
    private String expiration;

    @Value("${teste.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication)
    {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API Teste")
                .setSubject(usuarioLogado.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validarToken(String token)
    {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long recuperarUsuarioId(String token) {
        Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(body.getSubject());
    }
}
