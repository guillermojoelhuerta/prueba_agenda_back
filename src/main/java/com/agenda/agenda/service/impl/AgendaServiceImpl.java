package com.agenda.agenda.service.impl;

import com.agenda.agenda.exception.ResourceNotFoundException;
import com.agenda.agenda.model.Agenda;
import com.agenda.agenda.repository.AgendaRepository;
import com.agenda.agenda.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaServiceImpl implements AgendaService {
    @Autowired
    AgendaRepository agendaRepository;
    @Override
    public Agenda saveAgenda(Agenda agenda) {
        Optional<Agenda> contactoGuardado = agendaRepository.findByTelefono(agenda.getTelefono());
        if(contactoGuardado.isPresent()){
            throw new ResourceNotFoundException("El contacto con ese teléfono ya existe : " + agenda.getTelefono());
        }
        return agendaRepository.save(agenda);
    }
    @Override
    public List<Agenda> getAgendaList() {
        List<Agenda> agenda = agendaRepository.findAll();
        if (agenda.isEmpty()) {
            throw new ResourceNotFoundException("No existe ningún contacto.");
        }
        return agenda;
    }

    @Override
    public Optional<Agenda> getContacto(Long telefono) {
        return agendaRepository.findById(telefono);
    }
    @Override
    public Agenda updateAgenda(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    @Override
    public boolean deleteAgenda(Long telefono) {
        try {
            agendaRepository.deleteById(telefono);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
