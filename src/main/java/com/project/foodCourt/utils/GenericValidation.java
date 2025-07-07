package com.project.foodCourt.utils;

import com.project.foodCourt.domain.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class GenericValidation {
    public void validateCondition(boolean condition, ErrorCatalog errorCatalog) {
        if (condition) {
            throw new BusinessException(errorCatalog);
        }
    }
}
