package br.com.teste.teste.controller.form;

import br.com.teste.teste.model.Curso;
import br.com.teste.teste.model.Topico;
import br.com.teste.teste.repository.ICursoRepository;
import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class TopicoForm {
    @NotNull @NotEmpty @Length(min = 5)
    private String titulo;
    @NotNull @NotEmpty @Length(min = 5)
    private String mensagem;
    @NotNull @NotEmpty @Length(min = 5)
    private String nomeCurso;

    public Topico converter(ICursoRepository iCursoRepository) {
        Curso curso = iCursoRepository.findByNome(nomeCurso);
        return new Topico(titulo, mensagem, curso);
    }

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

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }
}
