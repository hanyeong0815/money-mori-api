package com.side.moneymoriapi.usecase.category;

import com.side.moneymoriapi.vo.category.Category;

public interface SaveCategoryUseCase {
    Category saveCategory(String username, String categoryName);
}
