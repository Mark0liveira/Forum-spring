package br.com.teste.teste.repository;

import br.com.teste.teste.model.Curso;
import br.com.teste.teste.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNome(String nome);
}
