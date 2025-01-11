package com.side.moneymoriapi.dto.budget;

import java.util.UUID;

public record SaveBudgetDto() {
    public record SaveBudgetRequestDto(
            UUID memberId,
            String month,
            Integer amount
    ) {}
}
