package com.agenda.agenda.repository;

import com.agenda.agenda.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    Optional<Agenda> findByNombre(String email);
    Optional<Agenda> findByTelefono(Long telefono);
}
