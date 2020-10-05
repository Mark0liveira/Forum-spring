package br.com.teste.teste.controller.dto;

public class TokenDto {
    private final String bearer;
    private final String token;

    public TokenDto(String token, String bearer) {
        this.token = token;
        this.bearer = bearer;
    }

    public String getBearer() {
        return bearer;
    }

    public String getToken() {
        return token;
    }
}
