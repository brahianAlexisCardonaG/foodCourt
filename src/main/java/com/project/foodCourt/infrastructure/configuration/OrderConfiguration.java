package com.project.foodCourt.infrastructure.configuration;

import com.project.foodCourt.domain.api.IOrderServicePort;
import com.project.foodCourt.domain.spi.IDishPersistencePort;
import com.project.foodCourt.domain.spi.IOrderPersistencePort;
import com.project.foodCourt.domain.spi.IRestaurantPersistencePort;
import com.project.foodCourt.domain.spi.IUserWebClientPort;
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
            IUserWebClientPort userWebClientPort
    ) {
        return new OrderUseCase(
                restaurantPersistencePort,
                dishPersistencePort,
                genericValidation,
                orderPersistencePort,
                userWebClientPort
        );
    }
}