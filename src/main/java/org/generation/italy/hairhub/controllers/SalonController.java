package org.generation.italy.hairhub.controllers;

import org.generation.italy.hairhub.dto.BarberDto;
import org.generation.italy.hairhub.dto.SalonDto;
import org.generation.italy.hairhub.dto.TreatmentDto;
import org.generation.italy.hairhub.model.TreatmentWithPrice;
import org.generation.italy.hairhub.model.entities.Appointment;
import org.generation.italy.hairhub.model.entities.Barber;
import org.generation.italy.hairhub.model.entities.Salon;
import org.generation.italy.hairhub.model.entities.Treatment;
import org.generation.italy.hairhub.model.services.AppointmentService;
import org.generation.italy.hairhub.model.services.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins="http://localhost:4200/", allowedHeaders = "*")
@RestController
@RequestMapping("/salon")
public class SalonController {
    private SalonService salonService;

    @Autowired
    public SalonController(SalonService salonService) {
        this.salonService = salonService;
    }

    @GetMapping("/bestSalons")
    public ResponseEntity<List<SalonDto>> searchBestSalons(@RequestParam(required = false) Integer type,
                                                           @RequestParam(required = false) Integer num) {
        int numResult = num != null && num > 0 && num < 11 ? num : 5;
        List<Salon> salons = salonService.getTopSalons(type, numResult);
        return ResponseEntity.ok(SalonDto.fromSalons(salons));
    }

    @GetMapping
    public ResponseEntity<List<SalonDto>> searchSalons(@RequestParam(required = false) String name) {
        List<Salon> salons = salonService.getSalonsByNameOrAddress(name);
        return ResponseEntity.ok(SalonDto.fromSalons(salons));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalonDto> getSalonById(@PathVariable long id) {
        Optional<Salon> oS = salonService.getSalonById(id);
        return oS.map(salon -> ResponseEntity.ok(new SalonDto(salon)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/treatments")
    public ResponseEntity<List<TreatmentDto>> getTreatmentsBySalon(@PathVariable long id) {
        List<TreatmentWithPrice> treatments = salonService.getTreatmentBySalon(id);
        List<TreatmentDto> dtos = treatments.stream().map(TreatmentDto::new).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{salonId}/barbers")
    public ResponseEntity<List<BarberDto>> getBarbersBySalon(@PathVariable long salonId) {
        List<Barber> barbers = salonService.getAllBarbersBySalonId(salonId);
        List<BarberDto> dtos = barbers.stream().map(BarberDto::new).toList();
        return ResponseEntity.ok(dtos);

    }


}
