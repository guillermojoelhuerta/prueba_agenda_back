package com.agenda.agenda.controller;

import com.agenda.agenda.exception.BindingResultException;
import com.agenda.agenda.model.Agenda;
import com.agenda.agenda.service.AgendaService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="agenda")
public class AgendaController {

    @Autowired
    AgendaService agendaService;
    @GetMapping(value = "get-agenda-list")
    public List<Agenda> getAgendaList() {
        return agendaService.getAgendaList();
    }

    @GetMapping(value = "get-contacto-by-id/{id}")
    public Optional<Agenda> getContacto(@PathVariable("id") Long id) {
        return agendaService.getContacto(id);
    }

    @PostMapping(value = "save-agenda")
    public Agenda saveAgenda(@Valid @RequestBody Agenda agenda,  BindingResult result)  {
        if(result.hasErrors()){
            throw new BindingResultException(result);
        }
        return agendaService.saveAgenda(agenda);
    }
    @PutMapping(value = "update-agenda")
    public Agenda updateAgenda(@Valid @RequestBody Agenda agenda,  BindingResult result)  {
        if(result.hasErrors()){
            throw new BindingResultException(result);
        }
        return agendaService.updateAgenda(agenda);
    }
    @DeleteMapping(value = "delete-agenda-by-id/{id}")
    public boolean deleteAgenda(@PathVariable("id") Long telefono){
        return agendaService.deleteAgenda(telefono);
    }
}
