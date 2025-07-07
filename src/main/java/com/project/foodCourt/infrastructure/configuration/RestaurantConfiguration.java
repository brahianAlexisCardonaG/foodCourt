package com.project.foodCourt.infrastructure.configuration;

import com.project.foodCourt.domain.api.IRestaurantServicePort;
import com.project.foodCourt.domain.spi.IRestaurantPersistencePort;
import com.project.foodCourt.domain.spi.IUserWebClientPort;
import com.project.foodCourt.domain.usecase.RestaurantUseCase;
import com.project.foodCourt.utils.GenericValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantConfiguration {
    
    @Bean
    public IRestaurantServicePort restaurantServicePort(
            IRestaurantPersistencePort restaurantPersistencePort,
            IUserWebClientPort userWebClientPort,
            GenericValidation genericValidation
    )
    {
        return new RestaurantUseCase(
                restaurantPersistencePort,
                userWebClientPort,
                genericValidation
        );
    }
}
