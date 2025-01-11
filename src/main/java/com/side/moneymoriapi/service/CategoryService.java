package com.side.moneymoriapi.service;

import com.side.moneymoriapi.exception.member.MemberErrorCode;
import com.side.moneymoriapi.mapper.category.CategoryCommendMapper;
import com.side.moneymoriapi.mapper.member.MemberQueryMapper;
import com.side.moneymoriapi.usecase.category.SaveCategoryUseCase;
import com.side.moneymoriapi.vo.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService implements SaveCategoryUseCase {
    private final CategoryCommendMapper commendMapper;

    private final MemberQueryMapper memberQueryMapper;

    @Override
    public Category saveCategory(String username, String categoryName) {
        UUID memberId = memberQueryMapper.findIdByUsername(username)
                .orElseThrow(
                        MemberErrorCode.NO_SUCH_USER::defaultException
                );

        Category newCategory = Category.builder()
                .memberId(memberId)
                .category(categoryName)
                .build();

        commendMapper.saveCategory(newCategory);

        return newCategory;
    }
}
