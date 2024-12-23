package org.generation.italy.hairhub.controllers;

import org.generation.italy.hairhub.dto.TreatmentDto;
import org.generation.italy.hairhub.model.TreatmentWithPrice;
import org.generation.italy.hairhub.model.entities.Treatment;
import org.generation.italy.hairhub.model.services.TreatmentService;
import org.generation.italy.hairhub.model.services.TreatmentServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins="http://localhost:4200/", allowedHeaders = "*")
@RestController
@RequestMapping("/treatment")
public class TreatmentController {
    TreatmentService treatmentService;
    @Autowired
    public TreatmentController(TreatmentServiceJpa treatmentServiceJpa) {
        this.treatmentService = treatmentServiceJpa;
    }
    @GetMapping("/{id}/treatments")
    public ResponseEntity<List<TreatmentDto>> getTreatmentsBySalon(@PathVariable long id){
        List<TreatmentWithPrice> treatments = treatmentService.getTreatmentBySalon(id);
        List<TreatmentDto> dtos = treatments.stream().map(TreatmentDto::new).toList();
        return ResponseEntity.ok(dtos);
    }
}
