package com.gp.electro.store.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gp.electro.store.dtos.CategoryDto;
import com.gp.electro.store.dtos.ImageResponse;
import com.gp.electro.store.dtos.PageableResponse;

import com.gp.electro.store.payload.ApiResponse;
import com.gp.electro.store.service.CategoryService;
import com.gp.electro.store.service.FileService;
import com.gp.electro.store.service.ImageService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private FileService fileService;

	@Value("${category.profile.image.path}")
	private String imagePath;

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
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") String categoryId) throws IOException {
		categoryService.deleteCategory(categoryId);
		ApiResponse apiResponse = ApiResponse.builder().message("Category with given is deleted successfully")
				.status(true).httpStatus(HttpStatus.OK).build();

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);

	}
	// get all

	@GetMapping
	public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

		PageableResponse<CategoryDto> pageableResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy,
				sortDir);

		return new ResponseEntity<PageableResponse<CategoryDto>>(pageableResponse, HttpStatus.OK);
	}

	// get single
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") String categoryId) {

		return new ResponseEntity<CategoryDto>(categoryService.getCategory(categoryId), HttpStatus.OK);
	}

	@PostMapping("/image/{categoryId}")
	public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestParam("categoryImage") MultipartFile categoryImage,
			@PathVariable("categoryId") String categoryId) throws IOException {

	//String image = fileService.uploadImage(categoryImage, imagePath);
	String image = imageService.uploadImage(categoryImage, imagePath);
		ImageResponse imageResponse = ImageResponse.builder().message("Image Uploaded successfully").status(true)
				.imageName(image).build();

		CategoryDto categoryDto = categoryService.getCategory(categoryId);
		categoryDto.setCoverImage(image);
		categoryService.updateCategory(categoryDto, categoryId);

		return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);
	}

	@GetMapping("/image/{categoryId}")
	public void getCategoryImage(@PathVariable("categoryId") String categoryId, HttpServletResponse httpServletResponse)
			throws IOException {
		CategoryDto categoryDto = categoryService.getCategory(categoryId);

		InputStream inputStream = imageService.getImage(imagePath, categoryDto.getCoverImage());
		httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());

	}

}

//	@PostMapping("{/image/categoryId}")
//	
//	public RequestEntity<String> uploadCategoryImage(@RequestParam("CategoryImage") MultipartFile image , String CategoryID){
//		
//		
//		 return "hello";
//		
//		
//	}
//	
