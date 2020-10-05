package br.com.teste.teste.controller;

import br.com.teste.teste.controller.dto.DetalheDoTopicoDto;
import br.com.teste.teste.controller.form.AtualizarTopicoForm;
import br.com.teste.teste.controller.form.TopicoForm;
import br.com.teste.teste.controller.dto.TopicoDto;
import br.com.teste.teste.model.Topico;
import br.com.teste.teste.repository.ICursoRepository;
import br.com.teste.teste.repository.ITopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    public ITopicoRepository iTopicoRepository;

    @Autowired
    public ICursoRepository iCursoRepository;

    @GetMapping
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDto> Lista(@RequestParam (required = false) String nomeCurso,
                                 @PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC, sort = "id")
                                 Pageable paginacao)
    {
        Page<Topico> topicos;
        if (nomeCurso == null) {
            topicos = iTopicoRepository.findAll(paginacao);
        } else {
            topicos = iTopicoRepository.findByCursoNome(nomeCurso, paginacao);
        }
        return TopicoDto.converter(topicos);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> Cadastrar(@Valid @RequestBody TopicoForm form,
                                               UriComponentsBuilder uriComponentsBuilder)
    {
        Topico topico = form.converter(iCursoRepository);
        iTopicoRepository.save(topico);

        URI uri = uriComponentsBuilder
                    .path("/topicos/{id}")
                    .buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalheDoTopicoDto> Detalhar(@PathVariable Long id)
    {
        Optional<Topico> topico = iTopicoRepository.findById(getId(id));
        return topico.map(value ->
                    ResponseEntity.ok(new DetalheDoTopicoDto(value))
                ).orElseGet(() ->
                    ResponseEntity.notFound().build()
                );

    }

    @PutMapping("{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> Atualizar(@PathVariable Long id,
                                               @Valid @RequestBody AtualizarTopicoForm form)
    {
        Optional<Topico> topico = iTopicoRepository.findById(getId(id));
        if (topico.isPresent()) {
            Topico newTopico = form.atualizarTopico(getId(id), iTopicoRepository);
            return ResponseEntity.ok(new TopicoDto(newTopico));
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<?> Deletar(@PathVariable Long id)
    {
        Optional<Topico> topico = iTopicoRepository.findById(getId(id));
        if (topico.isPresent()) {
            iTopicoRepository.deleteById(getId(id));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private Long getId(@PathVariable Long id)
    {
        return id;
    }

}
