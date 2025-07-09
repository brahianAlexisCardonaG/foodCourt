package com.project.foodCourt.domain.model.modelbasic;

import lombok.Data;
import java.time.LocalDate;

@Data
public class OrderBasicModel {
    private Long id;
    private Long clientId;
    private LocalDate date;
    private String status;
    private Long chefId;
}
