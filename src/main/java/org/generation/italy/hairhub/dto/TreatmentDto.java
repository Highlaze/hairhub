package org.generation.italy.hairhub.dto;

import org.generation.italy.hairhub.model.TreatmentWithPrice;
import org.generation.italy.hairhub.model.entities.SalonTreatment;

import java.math.BigDecimal;
import java.util.List;

public class TreatmentDto {
    private long id;
    private String name;
    private String description;
    private String imgUrl;
    private int type; //1 = taglio capelli, 2 = barba
    private double price;

    public TreatmentDto(){}
    public TreatmentDto(long id, String name, String description, String imgUrl, int type, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.type = type;
        this.price = price;
    }

    public TreatmentDto(TreatmentWithPrice t){
        this.id = t.getTreatment().getId();
        this.name = t.getTreatment().getName();
        this.description = t.getTreatment().getDescription();
        this.imgUrl = t.getTreatment().getImgUrl();
        this.type = t.getTreatment().getType();
        this.price = t.getPrice();
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
    public double getPrice() {
        return price;
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
    public void setPrice(double price) {
        this.price = price;
    }
}
