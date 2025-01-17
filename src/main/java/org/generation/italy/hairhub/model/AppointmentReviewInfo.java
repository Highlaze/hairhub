package org.generation.italy.hairhub.model;

import org.generation.italy.hairhub.model.entities.Barber;
import org.generation.italy.hairhub.model.entities.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentReviewInfo {
    private AppointmentWithPrices appointment;
    private boolean canReview;

    public AppointmentReviewInfo() {
    }

    public AppointmentReviewInfo(AppointmentWithPrices appointment, boolean canReview) {
        this.appointment = appointment;
        this.canReview = canReview;
    }

    public AppointmentWithPrices getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentWithPrices appointment) {
        this.appointment = appointment;
    }

    public boolean isCanReview() {
        return canReview;
    }

    public void setCanReview(boolean canReview) {
        this.canReview = canReview;
    }
}
