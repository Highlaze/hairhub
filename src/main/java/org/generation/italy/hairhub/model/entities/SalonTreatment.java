package org.generation.italy.hairhub.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
@Entity
@Table(name = "salon_services")
@Check(constraints = "price > 0")

public class SalonTreatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "sal_id", nullable = false)
    private Salon salon;

    @ManyToOne
    @JoinColumn(name = "serv_id", nullable = false)
    private Treatment treatment;

    private double price;

    public SalonTreatment(){}

    public SalonTreatment(Long id,Salon salon, Treatment treatment, double price) {
        this.id = id;
        this.salon = salon;
        this.treatment = treatment;
        this.price = price;
    }

    public long getId() {
        return id;
    }
    public Salon getSalon() {
        return salon;
    }
    public Treatment getTreatment() {
        return treatment;
    }
    public double getPrice() {
        return price;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setId(long id) {
        this.id = id;
    }
}

