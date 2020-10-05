package br.com.teste.teste.repository;

import br.com.teste.teste.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);
}
