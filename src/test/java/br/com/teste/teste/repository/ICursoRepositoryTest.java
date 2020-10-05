package br.com.teste.teste.repository;

import br.com.teste.teste.model.Curso;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ICursoRepositoryTest {

    /* Quando o spring.datasource.initialization-mode=never no application.properties
       utiliza esta injeção para criar o dado antes */
    @Autowired
    private TestEntityManager em;

    @Autowired
    public ICursoRepository iCursoRepository;

    @Test
    public void deveriaCarregarUmCursoAoBuscarPeloSeuNOme() {
        String nomeCurso = "HTML 5";

        Curso html = new Curso();
        html.setNome(nomeCurso);
        html.setCategoria("Programação");
        em.persist(html);

        Curso curso = iCursoRepository.findByNome(nomeCurso);
        Assert.assertNotNull(curso);
        Assert.assertEquals(nomeCurso, curso.getNome());
    }
}