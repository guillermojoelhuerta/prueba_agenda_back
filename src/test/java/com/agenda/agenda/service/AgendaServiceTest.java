package com.agenda.agenda.service;

import com.agenda.agenda.model.Agenda;
import com.agenda.agenda.repository.AgendaRepository;
import com.agenda.agenda.service.impl.AgendaServiceImpl;
/*
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


import com.agenda.agenda.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {
    @Mock
    private AgendaRepository agendaRepository;
    @InjectMocks
    private AgendaServiceImpl agendaService;
    private Agenda agenda;

    @BeforeEach
    void setup() {
        agenda = Agenda.builder()
                .telefono(8978675649L)
                .nombre("Joel")
                .direccion("Calle magnolias, colonia centro")
                .build();
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarAgenda(){
        //given
        //given(agendaRepository.findByNombre(agenda.getNombre())).willReturn(Optional.empty());
        given(agendaRepository.save(agenda)).willReturn(agenda);
        //when
        Agenda contactoGuardado = agendaService.saveAgenda(agenda);
        //then
        assertThat(contactoGuardado).isNotNull();
    }

    @DisplayName("Test para guardar un empleado con Throw Exception")
    @Test
    void testGuardarAgendaConThrowException(){
        //given
        given(agendaRepository.findByTelefono(agenda.getTelefono())).willReturn(Optional.of(agenda));
        //when
        assertThrows(ResourceNotFoundException.class,() -> {
            agendaService.saveAgenda(agenda);
        });
        //then
        verify(agendaRepository,never()).save(any(Agenda.class));
    }

    @DisplayName("Test para listar agenda")
    @Test
    void testListarAgenda(){
        //given
        Agenda agenda2 = Agenda.builder()
                .telefono(8783475649L)
                .nombre("memo")
                .direccion("Calle serrano, colonia las fuentes")
                .build();
        given(agendaRepository.findAll()).willReturn(List.of(agenda,agenda2));

        //when
        List<Agenda> contactos = agendaService.getAgendaList();

        //then
        assertThat(contactos).isNotNull();
        assertThat(contactos.size()).isEqualTo(2);
    }

    @DisplayName("Test para retornar una lista vacia")
    @Test
    void testListarColeccionContactosVacia(){
        //given
        Agenda agenda2 = Agenda.builder()
                .telefono(5683443649L)
                .nombre("memo")
                .direccion("Calle serrano, colonia las fuentes")
                .build();
        given(agendaRepository.findAll()).willReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            List<Agenda> listaAgenda = agendaService.getAgendaList();
            assertThat(listaAgenda).isEmpty();
            assertThat(listaAgenda.size()).isEqualTo(0);
        });
    }

    @DisplayName("Test para obtener un contacto por ID")
    @Test
    void testObtenerAgendaPorTelefono(){
        //given
        given(agendaRepository.findById(8978675649L)).willReturn(Optional.of(agenda));
        //when
        Agenda agendaGuardado = agendaService.getContacto(agenda.getTelefono()).get();

        //then
        assertThat(agendaGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar un contacto")
    @Test
    void testActualizarAgenda(){
        //given
        given(agendaRepository.save(agenda)).willReturn(agenda);
        agenda.setNombre("el joe");
        agenda.setDireccion("esta es la nueva dirección");

        //when
        Agenda contactoActualizado  = agendaService.updateAgenda(agenda);

        //then
        assertThat(contactoActualizado.getNombre()).isEqualTo("el joe");
        assertThat(contactoActualizado.getDireccion()).isEqualTo("esta es la nueva dirección");
    }

    @DisplayName("Test para eliminar un contacto")
    @Test
    void testEliminarContacto(){
        //given
        long contactoId = 9999L;
        willDoNothing().given(agendaRepository).deleteById(contactoId);

        //when
        agendaService.deleteAgenda(contactoId);

        //then
        verify(agendaRepository,times(1)).deleteById(contactoId);
    }

}
