package org.generation.italy.hairhub.dto;

import java.util.List;

public class AvailableDatesDto {
    private List<String> dates;

    public AvailableDatesDto(List<String> dates) {
        this.dates = dates;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }
}
