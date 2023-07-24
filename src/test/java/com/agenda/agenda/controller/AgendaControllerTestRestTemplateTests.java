package com.agenda.agenda.controller;

import com.agenda.agenda.model.Agenda;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.annotation.Order;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AgendaControllerTestRestTemplateTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
    void testGuardarEmpleado(){
        Agenda agenda = Agenda.builder()
                .telefono(8734475999L)
                .nombre("Memo")
                .direccion("Calle serrano, colonia las fuentes #13")
                .build();

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8085/app-agenda/agenda/save-agenda";

        //ResponseEntity<Agenda> respuesta = testRestTemplate.postForEntity("http://localhost:8085/app-agenda/agenda/save-agenda",agenda,Agenda.class);
        //Agenda respuesta = restTemplate.postForObject(url, agenda, Agenda.class);
        ResponseEntity<Agenda> respuesta = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(agenda), Agenda.class);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());

        Agenda agendaCreado = respuesta.getBody();
        assertNotNull(agendaCreado);

        assertEquals(8734475999L,agendaCreado.getTelefono());
        assertEquals("Memo",agendaCreado.getNombre());
        assertEquals("Calle serrano, colonia las fuentes #13",agendaCreado.getDireccion());
    }

    @Test
    @Order(2)
    void testListarEmpleados(){
        String url = "http://localhost:8085/app-agenda/agenda/get-agenda-list";
        ResponseEntity<Agenda[]> respuesta = testRestTemplate.getForEntity(url,Agenda[].class);
        List<Agenda> contactos = Arrays.asList(respuesta.getBody());

        assertEquals(HttpStatus.OK,respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON,respuesta.getHeaders().getContentType());

        assertEquals(1,contactos.size());
        assertEquals(8734475999L,contactos.get(0).getTelefono());
        assertEquals("Memo",contactos.get(0).getNombre());
        assertEquals("Calle serrano, colonia las fuentes #13",contactos.get(0).getDireccion());
    }
    @Test
    @Order(3)
    void testObtenerContacto(){
        String url = "http://localhost:8085/app-agenda/agenda/get-contacto-by-id/8734475999";
        ResponseEntity<Agenda> respuesta = testRestTemplate.getForEntity(url, Agenda.class);
        Agenda contacto = respuesta.getBody();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON,respuesta.getHeaders().getContentType());

        assertNotNull(contacto);
        assertEquals(8734475999L, contacto.getTelefono());
        assertEquals("Memo",contacto.getNombre());
        assertEquals("Calle serrano, colonia las fuentes #13",contacto.getDireccion());
    }

    @Test
    @Order(4)
    void testEliminarEmpleado(){
        ResponseEntity<Agenda[]> respuesta = testRestTemplate.getForEntity("http://localhost:8085/app-agenda/agenda/get-agenda-list",Agenda[].class);
        List<Agenda> contactos = Arrays.asList(respuesta.getBody());
        assertEquals(1,contactos.size());

        Map<String,Long> pathVariables = new HashMap<>();
        pathVariables.put("telefono",1L);
        ResponseEntity<Void> exchange = testRestTemplate.exchange("http://localhost:8085/app-agenda/agenda/delete-agenda-by-id/8734475999", HttpMethod.DELETE,null,Void.class,pathVariables);

        assertEquals(HttpStatus.OK,exchange.getStatusCode());
        assertFalse(exchange.hasBody());

        respuesta = testRestTemplate.getForEntity("http://localhost:8085/app-agenda/agenda/get-agenda-list",Agenda[].class);
        contactos = Arrays.asList(respuesta.getBody());
        assertEquals(0,contactos.size());

        ResponseEntity<Agenda> respuestaDetalle = testRestTemplate.getForEntity("http://localhost:8085/app-agenda/agenda/get-contacto-by-id/8734475999",Agenda.class);
        assertEquals(HttpStatus.NOT_FOUND,respuestaDetalle.getStatusCode());
        assertFalse(respuestaDetalle.hasBody());
    }
}
