package org.generation.italy.hairhub.controllers;

import org.generation.italy.hairhub.dto.AppointmentDto;
import org.generation.italy.hairhub.dto.ReviewDto;
import org.generation.italy.hairhub.model.AppointmentWithPrices;
import org.generation.italy.hairhub.model.entities.Appointment;
import org.generation.italy.hairhub.model.entities.Review;
import org.generation.italy.hairhub.model.exceptions.EntityNotFoundException;
import org.generation.italy.hairhub.model.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/", allowedHeaders = "*")
@RestController
@RequestMapping("/review")
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsBySalonId(@PathVariable long id) {
        List<Review> reviews = reviewService.getReviewsBySalonId(id);
        return ResponseEntity.ok(ReviewDto.fromReviews(reviews));
    }


    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewDto reviewDto, UriComponentsBuilder uriBuilder) {

        Review review = reviewDto.toReview();
        try {
            Review rev = reviewService.createReview(review, reviewDto.getAppointmentId());
            URI location = uriBuilder.path("/review/{id}").buildAndExpand(rev.getId()).toUri();
            return ResponseEntity.created(location).body(ReviewDto.fromReview(rev));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getFullMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
