package com.project.foodCourt.infrastructure.out.jpa.mapper;

import com.project.foodCourt.domain.model.OrderDishModel;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderDishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishEntityMapper {
    OrderDishModel toOrderDishModel(OrderDishEntity orderDishEntity);
}
