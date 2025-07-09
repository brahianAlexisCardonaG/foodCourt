package com.project.foodCourt.application.mapper.order;

import com.project.foodCourt.application.dto.response.order.OrderResponseDto;
import com.project.foodCourt.domain.model.OrderModel;
import com.project.foodCourt.domain.model.orderresponse.OrderResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {
    OrderResponseDto toOrderResponseDto(OrderResponseModel orderResponseModel);
}
