package com.agenda.agenda.service;

import com.agenda.agenda.model.Agenda;

import java.util.List;
import java.util.Optional;

public interface AgendaService {
    Agenda saveAgenda(Agenda agenda);
    List<Agenda> getAgendaList();
    Optional<Agenda> getContacto(Long telefono);
    Agenda updateAgenda(Agenda agenda);
    boolean deleteAgenda(Long telefono);
}
