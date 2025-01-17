package org.generation.italy.hairhub.dto;

import org.generation.italy.hairhub.model.entities.Appointment;
import org.generation.italy.hairhub.model.entities.Review;

import java.time.LocalDate;
import java.util.List;

public class ReviewDto {
    private long id;
    private int rating;
    private String comment;
    private String date;
    private long appointmentId;

    public ReviewDto() {}

    public ReviewDto(long id, int rating, String comment, String date,long appointmentId) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.appointmentId = appointmentId;
    }

    public ReviewDto(Review r){
        this.id = r.getId();
        this.rating = r.getRating();
        this.comment = r.getComment();
        this.date = r.getDate().toString();
        this.appointmentId = r.getAppointment().getId();

    }

    public static List<ReviewDto> fromReviews(List<Review> reviews){
        return reviews.stream().map(ReviewDto::new).toList();
    }

    public static ReviewDto fromReview(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setRating(review.getRating());
        reviewDto.setComment(review.getComment());
        reviewDto.setDate(review.getDate().toString());
        reviewDto.setAppointmentId(review.getAppointment().getId());
        return reviewDto;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public Review toReview() {
        Review review = new Review();
        review.setId(this.id);
        review.setRating(this.rating);
        review.setComment(this.comment);
        review.setDate(LocalDate.parse(this.date));
        return review;


    }
}
