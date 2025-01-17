package org.generation.italy.hairhub.model.services;

import org.generation.italy.hairhub.model.entities.Review;
import org.generation.italy.hairhub.model.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> getReviewsBySalonId(long salonId);
    Review createReview(Review review,long appId) throws EntityNotFoundException;
}
