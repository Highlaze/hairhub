package org.generation.italy.hairhub.controllers;

import org.generation.italy.hairhub.dto.AppointmentDto;
import org.generation.italy.hairhub.model.entities.Appointment;
import org.generation.italy.hairhub.model.exceptions.EntityNotFoundException;
import org.generation.italy.hairhub.model.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private AppointmentService appointmentService;
    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable long id) {
        Optional<Appointment> oa = appointmentService.update(id);
        if (oa.isPresent()) {
            return ResponseEntity.noContent().build(); //noContent significa che è andato tutto bene ma non c'è nulla da mandare(codice 204)
        }
        return ResponseEntity.notFound().build(); //notFound è quando proprio non trova nulla
    }
    @PostMapping("/create")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDto appDto, UriComponentsBuilder uriBuilder) {
        Appointment app = appDto.toAppointment();
        try {
            appointmentService.create(app, appDto.getBarberId(), appDto.getTreatmentId(), appDto.getUserId());
            URI location = uriBuilder.path("/appointment/{id}").buildAndExpand(app.getId()).toUri();
            return ResponseEntity.created(location).body(AppointmentDto.fromAppointment(app));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getFullMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/future/{userId}")
    public ResponseEntity<List<AppointmentDto>> getFutureAppointmentsByUserId(@PathVariable long userId){
        List<AppointmentDto> appointmentDtos = appointmentService.getFuturAppointmentByUserId(userId, LocalDate.now());
        return ResponseEntity.ok(appointmentDtos);
    }
    @GetMapping("/past/{userId}")
    public ResponseEntity<List<AppointmentDto>> getPastAppointmentsByUserId(@PathVariable long userId) {
       List<AppointmentDto> appointmentDtos = appointmentService.getPastAppointmentByUserId(userId, LocalDate.now());
       return ResponseEntity.ok(appointmentDtos);
    }
}
