package com.gp.electro.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.electro.store.dtos.CategoryDto;
import com.gp.electro.store.dtos.PageableResponse;
import com.gp.electro.store.payload.ApiResponse;
import com.gp.electro.store.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	// create
@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto categoryDto2 = categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(categoryDto2, HttpStatus.CREATED);

	}

	// update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
			@PathVariable("categoryId") String categoryId) {

		return new ResponseEntity<CategoryDto>(categoryService.updateCategory(categoryDto, categoryId), HttpStatus.OK);

	}

	// delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") String categoryId) {
		categoryService.deleteCategory(categoryId);
		ApiResponse apiResponse = ApiResponse.builder().message("Category with given is deleted successfully")
				.status(true).httpStatus(HttpStatus.OK).build();

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);

	}
	// get all
	
	@GetMapping
	public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
			@RequestParam(value = "pageNumber", defaultValue = "0",required = false) int  pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10",required = false)int  pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title",required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc",required = false) String sortDir
			){
		
		PageableResponse<CategoryDto> pageableResponse = categoryService
				.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<PageableResponse<CategoryDto>>(pageableResponse, HttpStatus.OK);
	}

	// get single
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") String categoryId){
		 
		
		return new ResponseEntity<CategoryDto>(categoryService.getCategory(categoryId), HttpStatus.OK);
	}

}
