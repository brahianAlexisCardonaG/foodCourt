package com.project.foodCourt.domain.spi;

import com.project.foodCourt.domain.model.feignclient.UserRoleResponse;

public interface IUserWebClientPort {
    UserRoleResponse getUserById(Long userId);
}