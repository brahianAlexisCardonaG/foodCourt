package com.project.foodCourt.infrastructure.out.jpa.adapter;

import com.project.foodCourt.domain.model.DishModel;
import com.project.foodCourt.domain.spi.IDishPersistencePort;
import com.project.foodCourt.infrastructure.out.jpa.entity.DishEntity;
import com.project.foodCourt.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.project.foodCourt.infrastructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public DishModel save(DishModel dishModel) {
        DishEntity dishEntity = dishEntityMapper.toDishEntity(dishModel);
        dishRepository.save(dishEntity);
        return dishEntityMapper.toDishModel(dishEntity);
    }

    @Override
    public Optional<DishModel> findByName(String name) {
        Optional<DishEntity> dishEntity = dishRepository.findByName(name);
        return dishEntity.map(dishEntityMapper::toDishModel);
    }

    @Override
    public Optional<DishModel> findById(Long id) {
        Optional<DishEntity> dishEntity = dishRepository.findById(id);
        return dishEntity.map(dishEntityMapper::toDishModel);
    }

    @Override
    public Page<DishModel> findDishesByRestaurantId(Pageable pageable, Long restaurantId, Long categoryId) {
        Page<DishEntity> dishEntities = dishRepository.findByRestaurantIdAndOptionalCategoryId(
                pageable, restaurantId, categoryId);
        return dishEntities.map(dishEntityMapper::toDishModel);
    }
}
