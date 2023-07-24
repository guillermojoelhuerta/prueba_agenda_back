package com.agenda.agenda.controller;

import com.agenda.agenda.model.Agenda;
import com.agenda.agenda.service.AgendaService;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@WebMvcTest
public class AgendaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AgendaService agendaService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGuardarContacto() throws Exception {
        //given
        Agenda agenda2 = Agenda.builder()
                .telefono(8783475649L)
                .nombre("memo")
                .direccion("Calle serrano, colonia las fuentes")
                .build();

        /*
        Configura el comportamiento esperado de una llamada al método saveAgenda del objeto agendaService.

        La línea given(agendaService.saveAgenda(any(Agenda.class))) establece la expectativa de que se
        llamará al método saveAgenda del objeto agendaService con cualquier instancia de la clase Agenda.
        El método any(Agenda.class) se utiliza para capturar cualquier instancia de Agenda que se pase como
        argumento en la llamada al método.

        La siguiente parte .willAnswer((invocation) -> invocation.getArgument(0)) especifica la acción
        a realizar cuando se invoque el método saveAgenda. En este caso, se utiliza un bloque lambda para
        obtener el primer argumento de la invocación (invocation.getArgument(0)) y devolverlo como
        resultado de la llamada al método.
        */
        given(agendaService.saveAgenda(any(Agenda.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        /*
        mockMvc.perform(post("/agenda/save-agenda") indica que se va a realizar una solicitud HTTP POST
        a la ruta "/agenda/save-agenda". Esto simula una llamada a tu controlador para el endpoint
        correspondiente.
        */
        ResultActions response = mockMvc.perform(post("/agenda/save-agenda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agenda2)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.telefono",is(agenda2.getTelefono())))
                    .andExpect(jsonPath("$.nombre",is(agenda2.getNombre())))
                    .andExpect(jsonPath("$.direccion",is(agenda2.getDireccion())));
    }

    @Test
    void testListarAgenda() throws Exception{
        //given
        List<Agenda> listaAgenda = new ArrayList<>();
        listaAgenda.add(Agenda.builder().telefono(5869483758L).nombre("Memo").direccion("calle burgos").build());
        listaAgenda.add(Agenda.builder().telefono(4859685748L).nombre("Joel").direccion("calle san andres").build());
        listaAgenda.add(Agenda.builder().telefono(9283948596L).nombre("Andres").direccion("calle lagos").build());
        listaAgenda.add(Agenda.builder().telefono(9038495849L).nombre("Juan").direccion("calle san fernando").build());
        listaAgenda.add(Agenda.builder().telefono(1728394857L).nombre("Ana").direccion("calle domingo díez").build());
        given(agendaService.getAgendaList()).willReturn(listaAgenda);

        //when
        ResultActions response = mockMvc.perform(get("/agenda/get-agenda-list"));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(listaAgenda.size())));
    }


    @Test
    void testObtenerContactoPorId() throws Exception{
        //given
        long contactoId = 1L;
        Agenda agenda2 = Agenda.builder()
                .telefono(8783475649L)
                .nombre("memo")
                .direccion("Calle serrano, colonia las fuentes")
                .build();
        given(agendaService.getContacto(contactoId)).willReturn(Optional.of(agenda2));

        //when
        ResultActions response = mockMvc.perform(get("/agenda/get-contacto-by-id/{id}",contactoId));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.telefono",is(agenda2.getTelefono())))
                .andExpect(jsonPath("$.nombre",is(agenda2.getNombre())))
                .andExpect(jsonPath("$.direccion",is(agenda2.getDireccion())));
    }

    @Test
    void testObtenerContactoNoEncontrado() throws Exception{
        //given
        long contactoId = 1L;
        Agenda agenda2 = Agenda.builder()
                .telefono(8783475649L)
                .nombre("memo")
                .direccion("Calle serrano, colonia las fuentes")
                .build();
        given(agendaService.getContacto(contactoId)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(get("/agenda/get-contacto-by-id/{id}",contactoId));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testActualizarContacto() throws Exception{
        //given
        long contactoId = 1L;
        Agenda contactoGuardado = Agenda.builder()
                .telefono(8783475649L)
                .nombre("memo")
                .direccion("Calle serrano, colonia las fuentes")
                .build();

        Agenda contactoActualizado = Agenda.builder()
                .telefono(8783475649L)
                .nombre("memo Joel")
                .direccion("Calle serrano, colonia las fuentes #13")
                .build();

        given(agendaService.getContacto(contactoId)).willReturn(Optional.of(contactoGuardado));
        given(agendaService.updateAgenda(any(Agenda.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/agenda/update-agenda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactoActualizado)));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.telefono",is(contactoActualizado.getTelefono())))
                .andExpect(jsonPath("$.nombre",is(contactoActualizado.getNombre())))
                .andExpect(jsonPath("$.direccion",is(contactoActualizado.getDireccion())));
    }

    @Test
    void testEliminarContacto() throws Exception{
        //given
        long contactoId = 1L;
        //willDoNothing().given(agendaService).deleteAgenda(contactoId);
        Mockito.when(agendaService.deleteAgenda(contactoId)).thenReturn(true);

        //when
        ResultActions response = mockMvc.perform(delete("/agenda/delete-agenda-by-id/{id}",contactoId));

        //then
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
