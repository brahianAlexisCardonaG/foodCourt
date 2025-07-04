package com.project.foodCourt.infrastructure.out.webclient.mapper;

import com.project.foodCourt.domain.model.feignclient.UserRoleResponse;
import com.project.foodCourt.infrastructure.out.webclient.response.UserRoleResponseWebClient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserWebClientMapper {
    UserRoleResponse toUserRoleResponse(UserRoleResponseWebClient userRoleResponseWebClient);
}
