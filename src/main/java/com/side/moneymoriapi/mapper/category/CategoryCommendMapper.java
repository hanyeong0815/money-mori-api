package com.side.moneymoriapi.mapper.category;

import com.side.moneymoriapi.vo.category.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryCommendMapper {
    void saveCategory(Category category);
}
