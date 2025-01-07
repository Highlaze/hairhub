package org.generation.italy.hairhub.model;

import jakarta.persistence.*;
import org.generation.italy.hairhub.model.entities.Barber;
import org.generation.italy.hairhub.model.entities.Treatment;
import org.generation.italy.hairhub.model.entities.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentWithPrices {
    private long id;
    private User user;
    private Barber barber;
    private List<TreatmentWithPrice> treatments = new ArrayList<>();
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;

    public AppointmentWithPrices() {}

    public AppointmentWithPrices(long id, User user, Barber barber, List<TreatmentWithPrice> treatments, LocalDate date, LocalTime startTime, LocalTime endTime, String status) {
        this.id = id;
        this.user = user;
        this.barber = barber;
        this.treatments = treatments;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Barber getBarber() {
        return barber;
    }

    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    public List<TreatmentWithPrice> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<TreatmentWithPrice> treatments) {
        this.treatments = treatments;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

