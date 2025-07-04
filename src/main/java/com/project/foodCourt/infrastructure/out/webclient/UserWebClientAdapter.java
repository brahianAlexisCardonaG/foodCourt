package com.project.foodCourt.infrastructure.out.webclient;

import com.project.foodCourt.domain.model.feignclient.UserRoleResponse;
import com.project.foodCourt.domain.spi.IUserWebClientPort;
import com.project.foodCourt.infrastructure.out.webclient.mapper.IUserWebClientMapper;
import com.project.foodCourt.infrastructure.out.webclient.response.UserRoleResponseWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserWebClientAdapter implements IUserWebClientPort {

    private final UserWebClient userWebClient;
    private final IUserWebClientMapper userWebClientMapper;

    @Override
    public UserRoleResponse getUserById(Long userId) {
        UserRoleResponseWebClient user = userWebClient.getUserById(userId);
        return userWebClientMapper.toUserRoleResponse(user);
    }
}