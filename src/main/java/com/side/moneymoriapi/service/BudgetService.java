package com.side.moneymoriapi.service;

import com.side.moneymoriapi.dto.budget.SaveBudgetDto.SaveBudgetRequestDto;
import com.side.moneymoriapi.mapper.budget.BudgetCommendMapper;
import com.side.moneymoriapi.usecase.budget.CreateBudgetUseCase;
import com.side.moneymoriapi.vo.budget.Budget;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetService implements CreateBudgetUseCase {
    private final BudgetCommendMapper commendMapper;

    // TODO need to validation
    @Override
    public Budget createBudget(SaveBudgetRequestDto dto) {
        Budget newBudget = Budget.builder()
                .memberId(dto.memberId())
                .month(dto.month())
                .amount(dto.amount())
                .build();

        commendMapper.save(newBudget);

        return newBudget;
    }
}
