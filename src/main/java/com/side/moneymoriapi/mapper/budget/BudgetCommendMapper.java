package com.side.moneymoriapi.mapper.budget;

import com.side.moneymoriapi.vo.budget.Budget;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BudgetCommendMapper {
    void save(Budget budget);
}
