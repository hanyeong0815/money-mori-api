package com.side.moneymoriapi.usecase.budget;

import com.side.moneymoriapi.dto.budget.SaveBudgetDto.SaveBudgetRequestDto;
import com.side.moneymoriapi.vo.budget.Budget;

public interface CreateBudgetUseCase {
    Budget createBudget(SaveBudgetRequestDto dto);
}
