package org.generation.italy.hairhub.model;

import org.generation.italy.hairhub.model.entities.Treatment;

import java.math.BigDecimal;

public class TreatmentWithPrice {
    private Treatment treatment;
    private double price;

    public TreatmentWithPrice(){}
    public TreatmentWithPrice(Treatment treatment, double price) {
        this.treatment = treatment;
        this.price = price;
    }

    public Treatment getTreatment() {
        return treatment;
    }
    public double getPrice() {
        return price;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
