package org.generation.italy.hairhub.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.processing.CheckHQL;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "services")
@Check(constraints = "type in (1,2)")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serv_id")
    private long id;

    private String name;

    private String description;

    @Column(name = "img_url")
    private String imgUrl;

    private int type; //1 = taglio capelli, 2 = barba

    @ManyToMany(mappedBy = "treatments")
    private List<Appointment> appointments = new ArrayList<>();


    @OneToMany(mappedBy = "treatment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalonTreatment> salonTreatments;

    // Getters and Setters

    public Treatment(){}

    public Treatment(long id, String name, String description, String imgUrl, int type, List<SalonTreatment> salonTreatments,List<Appointment> appointments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.type = type;
        this.salonTreatments = salonTreatments;
        this.appointments = appointments;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public int getType() {
        return type;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<SalonTreatment> getSalonTreatments() {
        return salonTreatments;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public void setType(int type) {
        this.type = type;
    }
    public void setSalonTreatments(List<SalonTreatment> salonTreatments) {
        this.salonTreatments = salonTreatments;
    }
}