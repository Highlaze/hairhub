package org.generation.italy.hairhub.dto;

import org.generation.italy.hairhub.model.AppointmentWithPrices;
import org.generation.italy.hairhub.model.entities.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CreateAppointmentDto {
    private long id;
    private String userName;
    private long userId;
    private long barberId;
    private String barberName;
    private String date;
    private String startTime;
    private String endTime;
    private String status;
    private List<Long> treatments;


    public CreateAppointmentDto(){}
    public CreateAppointmentDto(long id, long userId, long barberId, String barberName, String date, String startTime, String endTime, String status,List<Long> treatments) {
        this.id = id;
        this.userId = userId;
        this.barberId = barberId;
        this.barberName = barberName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.treatments = treatments;
    }

    public Appointment toAppointment() {
        LocalDate parsedDate = LocalDate.parse(this.date);
        LocalTime parsedStartTime = LocalTime.parse(this.startTime);
        LocalTime parsedEndTime = LocalTime.parse(this.endTime);

        Appointment appointment = new Appointment();
        appointment.setId(this.id);
        appointment.setDate(parsedDate);
        appointment.setStartTime(parsedStartTime);
        appointment.setEndTime(parsedEndTime);
        appointment.setStatus(this.status);

        return appointment;
    }

    public long getId() {
        return id;
    }
    public long getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public long getBarberId() {
        return barberId;
    }
    public String getBarberName() {
        return barberName;
    }
    public String getDate() {
        return date;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public String getStatus() {
        return status;
    }

    public List<Long> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Long> treatments) {
        this.treatments = treatments;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setBarberId(long barberId) {
        this.barberId = barberId;
    }
    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
