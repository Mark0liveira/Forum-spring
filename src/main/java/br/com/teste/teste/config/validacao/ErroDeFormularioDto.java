package br.com.teste.teste.config.validacao;

public class ErroDeFormularioDto {

    private final String campo;
    private final String erro;

    public String getErro() {
        return erro;
    }

    public String getCampo() {
        return campo;
    }

    public ErroDeFormularioDto(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }
}
