package com.agenda.agenda.repository;

import com.agenda.agenda.model.Agenda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AgendaRepositoryTest {

    @Autowired
    AgendaRepository agendaRepository;

    private Agenda agenda;

    @BeforeEach
    void setup() {
        agenda = Agenda.builder()
                .telefono(6356875643L)
                .nombre("Joel")
                .direccion("Calle magnolias, colonia centro")
                .build();
    }

    @DisplayName("Test para guardar en agenda")
    @Test
    void testSaveAgenda() {
        //given - dado o condición previa o configuración
        Agenda agenda = Agenda.builder()
                .telefono(4555678967L)
                .nombre("memo")
                .direccion("El centro")
                .build();

        //when - acción o el comportamiento que vamos a probar
        Agenda agendaGuadada = agendaRepository.save(agenda);

        //then - verificar la salida

        assertThat(agendaGuadada).isNotNull();
        assertThat(agendaGuadada.getTelefono()).isGreaterThan(0);
    }

    @DisplayName("Test para listar agenda")
    @Test
    void testGetAgendaList() {
        //given - dado o condición previa o configuración
        Agenda agenda1 = Agenda.builder()
                .telefono(4555678967L)
                .nombre("memo")
                .direccion("El centro")
                .build();
        agendaRepository.save(agenda1);
        agendaRepository.save(agenda);
        //when - acción o el comportamiento que vamos a probar
        List<Agenda> listaAgenda = agendaRepository.findAll();
        //then
        assertThat(listaAgenda).isNotNull();
        assertThat(listaAgenda.size()).isEqualTo(2);
    }

    @DisplayName("Obtener contacto por id - telefono")
    @Test
    void getContactoById() {
        //given
        agendaRepository.save(agenda);
        //when - comportamiento o accion que vamos a probar
        Agenda agenda1 = agendaRepository.findById(agenda.getTelefono()).get();
        //then
        assertThat(agenda).isNotNull();
    }

    @DisplayName("Test para actualizar la agenda")
    @Test
    void testActualizarAgenda() {
        //given
        agendaRepository.save(agenda);
        //when
        Agenda contactoGuardado = agendaRepository.findById(agenda.getTelefono()).get();
        contactoGuardado.setNombre("erick");
        contactoGuardado.setDireccion("las cabañas, n5, colonia reyes");
        Agenda contactoActualizado = agendaRepository.save(contactoGuardado);

        //then
        assertThat(contactoActualizado.getNombre()).isEqualTo("erick");
        assertThat(contactoActualizado.getDireccion()).isEqualTo("las cabañas, n5, colonia reyes");
    }

    @DisplayName("Test para eliminar un contacto")
    @Test
    void testEliminarContacto(){
        //given
        agendaRepository.save(agenda);
        //when
        agendaRepository.deleteById(agenda.getTelefono());
        Optional<Agenda> contactoOptional = agendaRepository.findById(agenda.getTelefono());
        //then
        assertThat(contactoOptional).isEmpty();
    }
}