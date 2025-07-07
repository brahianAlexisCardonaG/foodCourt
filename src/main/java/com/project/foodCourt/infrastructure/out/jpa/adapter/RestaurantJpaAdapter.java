package com.project.foodCourt.infrastructure.out.jpa.adapter;

import com.project.foodCourt.domain.model.RestaurantModel;
import com.project.foodCourt.domain.spi.IRestaurantPersistencePort;
import com.project.foodCourt.infrastructure.out.jpa.entity.RestaurantEntity;
import com.project.foodCourt.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.project.foodCourt.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public RestaurantModel save(RestaurantModel restaurantModel) {
        RestaurantEntity restaurantEntity = restaurantEntityMapper.toRestaurantEntity(restaurantModel);
        restaurantRepository.save(restaurantEntity);
        return restaurantEntityMapper.toRestaurantModel(restaurantEntity);
    }

    @Override
    public Optional<RestaurantModel> getRestaurantByName(String name) {
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findByName(name);
        return restaurantEntity.map(restaurantEntityMapper::toRestaurantModel);
    }

    @Override
    public Optional<RestaurantModel> getRestaurantByNit(String nit) {
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findByNit(nit);
        return restaurantEntity.map(restaurantEntityMapper::toRestaurantModel);
    }

    @Override
    public Optional<RestaurantModel> getRestaurantById(Long id) {
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
        return restaurantEntity.map(restaurantEntityMapper::toRestaurantModel);
    }

    @Override
    public Page<RestaurantModel> getAllRestaurants(Pageable pageable) {
        Page<RestaurantEntity> restaurantEntities = restaurantRepository.findAll(pageable);
        return restaurantEntities.map(restaurantEntityMapper::toRestaurantModel);
    }
}
