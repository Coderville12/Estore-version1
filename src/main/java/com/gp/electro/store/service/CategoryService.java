package com.gp.electro.store.service;

import java.io.IOException;

import com.gp.electro.store.dtos.CategoryDto;
import com.gp.electro.store.dtos.PageableResponse;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);

	CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);

	void deleteCategory(String categoryId) throws IOException;

	PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	CategoryDto getCategory(String categoryId);
	

}
