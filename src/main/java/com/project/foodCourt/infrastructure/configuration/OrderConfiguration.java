package com.project.foodCourt.infrastructure.configuration;

import com.project.foodCourt.domain.api.IOrderServicePort;
import com.project.foodCourt.domain.spi.*;
import com.project.foodCourt.domain.usecase.order.OrderUseCase;
import com.project.foodCourt.utils.GenericValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {
    
    @Bean
    public IOrderServicePort orderServicePort(
            IRestaurantPersistencePort restaurantPersistencePort,
            IDishPersistencePort dishPersistencePort,
            GenericValidation genericValidation,
            IOrderPersistencePort orderPersistencePort,
            IUserWebClientPort userWebClientPort,
            IOrderDishPersistencePort iOrderDishPersistencePort,
            ISmsNotificationPort smsNotificationPort
    ) {
        return new OrderUseCase(
                restaurantPersistencePort,
                dishPersistencePort,
                genericValidation,
                orderPersistencePort,
                userWebClientPort,
                iOrderDishPersistencePort,
                smsNotificationPort
        );
    }
}