package com.alisa.amazon.clone.backend.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNoneEmpty;

public class CommonUtils {
    public static PageRequest getPageRequest(Integer pageNumber, Integer pageSize, List<String> sortBy, String sortOrder) {
        Sort pageSort = (isNoneEmpty(sortBy.get(0)) && isNoneEmpty(sortOrder)) ? Sort.by(new Sort.Order(Sort.Direction.valueOf(sortOrder.toUpperCase()), sortBy.get(0))) : null;
        if (pageSort == null) {
            return (pageNumber != null && pageSize != null) ? PageRequest.of(pageNumber, pageSize) : null;
        }
        return (pageNumber != null && pageSize != null) ? PageRequest.of(pageNumber, pageSize, pageSort) : null;
    }
}