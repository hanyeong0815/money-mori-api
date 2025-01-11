package com.side.moneymoriapi.api.budget;

import com.side.moneymoriapi.aop.jwt.JwtSecured;
import com.side.moneymoriapi.dto.budget.SaveBudgetDto.SaveBudgetRequestDto;
import com.side.moneymoriapi.usecase.budget.CreateBudgetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/budget")
@RequiredArgsConstructor
public class BudgetCommendApi {
    private final CreateBudgetUseCase createBudgetUseCase;

    @JwtSecured(roles = {"USER"})
    @PostMapping("")
    public ResponseEntity<?> createBudget(@RequestBody SaveBudgetRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createBudgetUseCase.createBudget(dto));
    }
}
