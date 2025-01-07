package org.generation.italy.hairhub.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "appointments")
@Check(constraints = "status in ('Confirmed', 'Cancelled')") //dà un vincolo sul database allo status, può essere solo confirmed o cancelled
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "us_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "barb_id", nullable = false)
    private Barber barber;

    @ManyToMany
    @JoinTable(
            name = "appointment_services",
            joinColumns = @JoinColumn(name = "app_id"),
            inverseJoinColumns = @JoinColumn(name = "serv_id")
    )
    private List<Treatment> treatments = new ArrayList<>();



    private LocalDate date;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    private String status;

    // Getters and Setters

    public Appointment(){}

    public Appointment(long id, User user, Barber barber, List<Treatment> treatments, LocalDate date, LocalTime startTime, LocalTime endTime, String status) {
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
    public User getUser() {
        return user;
    }
    public Barber getBarber() {
        return barber;
    }
    public List<Treatment> getTreatments() {
        return treatments;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public String getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setBarber(Barber barber) {
        this.barber = barber;
    }
    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public void setStatus(String status) {
        this.status = status;
    }


}