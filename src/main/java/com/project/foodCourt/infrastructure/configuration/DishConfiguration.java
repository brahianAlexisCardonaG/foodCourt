package com.project.foodCourt.infrastructure.configuration;

import com.project.foodCourt.domain.api.IDishServicePort;
import com.project.foodCourt.domain.model.modelbasic.mapper.ICategoryBasicModelMapper;
import com.project.foodCourt.domain.model.modelbasic.mapper.IRestaurantBasicModelMapper;
import com.project.foodCourt.domain.spi.ICategoryPersistencePort;
import com.project.foodCourt.domain.spi.IDishPersistencePort;
import com.project.foodCourt.domain.spi.IRestaurantPersistencePort;
import com.project.foodCourt.domain.usecase.dish.DishUseCase;
import com.project.foodCourt.utils.GenericValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DishConfiguration {
    @Bean
    public IDishServicePort dishServicePort(
            IRestaurantPersistencePort restaurantPersistencePort,
            IDishPersistencePort dishPersistencePort,
            GenericValidation genericValidation,
            IRestaurantBasicModelMapper restaurantBasicModelMapper,
            ICategoryPersistencePort iCategoryPersistencePort,
            ICategoryBasicModelMapper iCategoryBasicModelMapper
    )
    {
        return new DishUseCase(
                restaurantPersistencePort,
                dishPersistencePort,
                genericValidation,
                restaurantBasicModelMapper,
                iCategoryPersistencePort,
                iCategoryBasicModelMapper
        );
    }
}
