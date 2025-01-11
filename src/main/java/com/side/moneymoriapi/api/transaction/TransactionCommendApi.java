package com.side.moneymoriapi.api.transaction;

import com.side.moneymoriapi.aop.jwt.JwtSecured;
import com.side.moneymoriapi.usecase.category.SaveCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/transaction")
@RequiredArgsConstructor
public class TransactionCommendApi {
    private final SaveCategoryUseCase saveCategoryUseCase;

    @JwtSecured(roles = {"USER"})
    @PostMapping("category/{username}/{categoryName}")
    public ResponseEntity<?> saveCategory(@PathVariable String username, @PathVariable String categoryName) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saveCategoryUseCase.saveCategory(username, categoryName));
    }
}
