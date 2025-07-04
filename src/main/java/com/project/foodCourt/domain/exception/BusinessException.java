package com.project.foodCourt.domain.exception;

import com.project.foodCourt.utils.ErrorCatalog;

public class BusinessException extends BaseException {
    public BusinessException(ErrorCatalog errorCatalog) {
        super(errorCatalog);
    }
}