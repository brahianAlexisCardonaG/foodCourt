package com.project.foodCourt.application.mapper.order;

import com.project.foodCourt.application.dto.request.order.OrderRequestDto;
import com.project.foodCourt.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {
    @Mapping(source = "restaurantId", target = "restaurant.id")
    @Mapping(source = "items", target = "orderDishes")
    OrderModel toOrderDishModel(OrderRequestDto orderRequestDto);
}
