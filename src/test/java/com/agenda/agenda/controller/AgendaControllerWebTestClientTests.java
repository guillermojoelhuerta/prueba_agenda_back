package com.agenda.agenda.controller;


import com.agenda.agenda.model.Agenda;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.hamcrest.Matchers.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AgendaControllerWebTestClientTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void testGuardarContacto(){
        //given
        Agenda agenda = Agenda.builder()
                .telefono(8783475649L)
                .nombre("memo")
                .direccion("Calle serrano, colonia las fuentes")
                .build();

        //when
        webTestClient.post().uri("http://localhost:8085/app-agenda/agenda/save-agenda")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(agenda)
                .exchange() //envia el request

                //then
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.telefono").isEqualTo(agenda.getTelefono())
                .jsonPath("$.nombre").isEqualTo(agenda.getNombre())
                .jsonPath("$.direccion").isEqualTo(agenda.getDireccion());
    }

    @Test
    @Order(2)
    void testObtenerContactoPorId(){
        webTestClient.get().uri("http://localhost:8085/app-agenda/agenda/get-contacto-by-id/8783475649").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.telefono").isEqualTo(8783475649L)
                .jsonPath("$.nombre").isEqualTo("memo")
                .jsonPath("$.direccion").isEqualTo("Calle serrano, colonia las fuentes");
    }

    @Test
    @Order(3)
    void testListarEmpleados(){
        webTestClient.get().uri("http://localhost:8085/app-agenda/agenda/get-agenda-list").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].telefono").isEqualTo(8783475649L)
                .jsonPath("$[0].nombre").isEqualTo("memo")
                .jsonPath("$[0].direccion").isEqualTo("Calle serrano, colonia las fuentes")
                .jsonPath("$").isArray()
                .jsonPath("$").value(hasSize(1));
    }

    @Test
    @Order(4)
    void testObtenerListadoDeAgenda(){
        webTestClient.get().uri("http://localhost:8085/app-agenda/agenda/get-agenda-list").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Agenda.class)
                .consumeWith(response -> {
                    List<Agenda> contactos = response.getResponseBody();
                    Assertions.assertEquals(1, contactos.size());
                    Assertions.assertNotNull(contactos);
                });
    }
    @Test
    @Order(5)
    void testActualizarContacto(){
        Agenda agendaActualizar = Agenda.builder()
                .telefono(8783475649L)
                .nombre("memo huerta")
                .direccion("Calle arcane, colonia amatitlan")
                .build();

        webTestClient.put().uri("http://localhost:8085/app-agenda/agenda/update-agenda")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(agendaActualizar)
                .exchange() //envia el request

                //then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(6)
    void testEliminarContacto(){
        webTestClient.get().uri("http://localhost:8085/app-agenda/agenda/get-agenda-list").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Agenda.class)
                .hasSize(2);

        webTestClient.delete().uri("http://localhost:8085/app-agenda/agenda/delete-agenda-by-id/8783475649")
                .exchange()
                .expectStatus().isOk();

        webTestClient.get().uri("http://localhost:8085/app-agenda/agenda/get-agenda-list").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Agenda.class)
                .hasSize(1);

        webTestClient.get().uri("http://localhost:8085/app-agenda/agenda/get-contacto-by-id/8783475649").exchange()
                .expectStatus().is4xxClientError();
    }

}
