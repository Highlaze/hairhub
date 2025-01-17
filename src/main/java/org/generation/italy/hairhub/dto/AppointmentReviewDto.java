package org.generation.italy.hairhub.dto;

import org.generation.italy.hairhub.model.AppointmentReviewInfo;
import org.generation.italy.hairhub.model.AppointmentWithPrices;

import java.util.List;

public class AppointmentReviewDto {
    private long id;
    private String userName;
    private long userId;
    private long barberId;
    private long salonId;
    private String salonName;
    private String barberName;
    private String date;
    private String startTime;
    private String endTime;
    private String status;
    private List<TreatmentDto> treatments;

    private boolean canReview;


    public AppointmentReviewDto(){}

    public AppointmentReviewDto(long id, String userName, long userId, long barberId, long salonId, String salonName, String barberName, String date, String startTime, String endTime, String status, List<TreatmentDto> treatments, boolean canReview) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.barberId = barberId;
        this.salonId = salonId;
        this.salonName = salonName;
        this.barberName = barberName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.treatments = treatments;
        this.canReview = canReview;
    }

    public static AppointmentReviewDto fromAppointmentWithPrice(AppointmentReviewInfo app) {
        return new AppointmentReviewDto(
                app.getAppointment().getId(),
                app.getAppointment().getUser().getNickname(),
                app.getAppointment().getUser().getId(),
                app.getAppointment().getBarber().getId(),
                app.getAppointment().getBarber().getSalon().getId(),
                app.getAppointment().getBarber().getSalon().getName(),
                app.getAppointment().getBarber().getFirstname() + " " + app.getAppointment().getBarber().getLastname(),
                app.getAppointment().getDate().toString(),
                app.getAppointment().getStartTime().toString(),
                app.getAppointment().getEndTime().toString(),
                app.getAppointment().getStatus(),
                app.getAppointment().getTreatments().stream().map(TreatmentDto::new).toList(),
                app.isCanReview()
        );
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBarberId() {
        return barberId;
    }

    public void setBarberId(long barberId) {
        this.barberId = barberId;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isCanReview() {
        return canReview;
    }

    public void setCanReview(boolean canReview) {
        this.canReview = canReview;
    }

    public List<TreatmentDto> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<TreatmentDto> treatments) {
        this.treatments = treatments;
    }

    public long getSalonId() {
        return salonId;
    }

    public void setSalonId(long salonId) {
        this.salonId = salonId;
    }

    public String getSalonName() {
        return salonName;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
