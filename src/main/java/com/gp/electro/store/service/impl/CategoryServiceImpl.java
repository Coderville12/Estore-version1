package com.gp.electro.store.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.electro.store.dtos.CategoryDto;
import com.gp.electro.store.dtos.PageableResponse;

import com.gp.electro.store.entities.Category;

import com.gp.electro.store.exceptions.ResourceNotFoundException;
import com.gp.electro.store.helper.Helper;
import com.gp.electro.store.repositories.CategoryRepo;
import com.gp.electro.store.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${category.profile.image.path}")
	private String imagePath;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		String categoryId = UUID.randomUUID().toString();
		categoryDto.setCategoryId(categoryId);

		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepo.save(category);
		return modelMapper.map(savedCategory, CategoryDto.class);

	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {

		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with given ID is  not present ..."));
		category.setCoverImage(categoryDto.getCoverImage());
		category.setDiscription(categoryDto.getDiscription());
		category.setTitle(categoryDto.getTitle());
		categoryRepo.save(category);
		CategoryDto updateCategoryDto = modelMapper.map(category, CategoryDto.class);

		return updateCategoryDto;
	}

	@Override
	public void deleteCategory(String categoryId) throws IOException {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot delete category with given ID not present"));
		String fullPath = imagePath + category.getCoverImage();
		Path path = Paths.get(fullPath);
		Files.delete(path);
		categoryRepo.delete(category);
		

	}

	@Override
	public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Category> responePage = categoryRepo.findAll(pageable);

		return Helper.getPageableResponse(responePage, CategoryDto.class);

	}

	@Override
	public CategoryDto getCategory(String categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with given id not present"));

		return modelMapper.map(category, CategoryDto.class);
	}

}
