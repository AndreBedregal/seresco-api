package com.github.andrebedregal.seresco;

import com.github.andrebedregal.seresco.model.Alumno;
import com.github.andrebedregal.seresco.model.GeneroEnum;
import com.github.andrebedregal.seresco.repository.AlumnoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AlumnoRepositoryIntegrationTest {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Test
    public void whenFindById_thenReturnAlumno() {
        // given
        Alumno alumno = new Alumno();
        alumno.setNombres("André");
        alumno.setApellidos("Bedregal");
        alumno.setFechaNacimiento(new Date());
        alumnoRepository.save(alumno);

        // when
        Optional<Alumno> found = alumnoRepository.findById(1L);

        // then
        assert found.isPresent();

        assertThat(found.get().getNombres())
                .isEqualTo(alumno.getNombres());
    }

    @Test
    public void whenFindAll_thenReturnAll() {
        // given
        Alumno alumno = new Alumno();
        alumno.setNombres("André");
        alumno.setApellidos("Bedregal");
        alumno.setFechaNacimiento(new Date());
        alumnoRepository.save(alumno);

        // when
        List<Alumno> all = alumnoRepository.findAll();

        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void whenCreated_thenAlumnoIsPresent() {
        Alumno alumno = new Alumno();
        alumno.setNombres("André");
        alumno.setApellidos("Bedregal");
        alumno.setFechaNacimiento(new Date());
        alumno.setGenero(GeneroEnum.M);

        // create
        Alumno alumnoNew_ = alumnoRepository.save(alumno);

        Optional<Alumno> byId = alumnoRepository.findById(alumnoNew_.getId());

        assertThat(byId).isPresent();
    }

    @Test
    public void whenDeleted_thenReturnEmpty() {
        Alumno alumno = new Alumno();
        alumno.setNombres("André");
        alumno.setApellidos("Bedregal");
        alumno.setFechaNacimiento(new Date());
        alumno.setGenero(GeneroEnum.M);

        Alumno alumnoNew_ = alumnoRepository.save(alumno);

        // delete
        alumnoRepository.delete(alumnoNew_);

        Optional<Alumno> byId = alumnoRepository.findById(alumnoNew_.getId());

        assertThat(byId).isEmpty();
    }

}
