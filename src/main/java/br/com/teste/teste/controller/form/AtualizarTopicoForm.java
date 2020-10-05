package br.com.teste.teste.controller.form;

import br.com.teste.teste.model.Topico;
import br.com.teste.teste.repository.ITopicoRepository;
import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class AtualizarTopicoForm {

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String titulo;
    @NotNull @NotEmpty @Length(min = 5)
    private String mensagem;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Topico atualizarTopico(Long id, ITopicoRepository iTopicoRepository) {
        Topico topico = iTopicoRepository.getOne(id);

        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);

        return topico;
    }
}
