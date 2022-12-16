package com.example.helpfy.utils;

import com.example.helpfy.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class EntitiesUtil {
    public static <T> T findById(Long id, JpaRepository<T, Long> repository, String errorMessage) {
        return repository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(errorMessage);
        });
    }

}
