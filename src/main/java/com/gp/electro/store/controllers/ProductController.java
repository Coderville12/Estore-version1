package com.gp.electro.store.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.gp.electro.store.dtos.ImageResponse;
import com.gp.electro.store.dtos.PageableResponse;
import com.gp.electro.store.dtos.ProductDto;
import com.gp.electro.store.payload.ApiResponse;
import com.gp.electro.store.service.ImageService;
import com.gp.electro.store.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ImageService imageService;

	@Value("${product.profile.image.path}")
	private String imagePath;

	@PostMapping()
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {

		return new ResponseEntity<ProductDto>(productService.createProduct(productDto), HttpStatus.CREATED);

	}

	@PostMapping("category/{category_ID}")
	public ResponseEntity<ProductDto> createProductWithCategory(@RequestBody ProductDto productDto,
			@PathVariable("category_ID") String category_ID) {

		return new ResponseEntity<ProductDto>(productService.createProductWithCategory(productDto, category_ID),
				HttpStatus.CREATED);

	}

	@PutMapping("addCategory/{product_ID}/{category_ID}")
	public ResponseEntity<ProductDto> addProductCategory(@PathVariable("product_ID") String product_ID,
			@PathVariable("category_ID") String category_ID) {

	

		return new ResponseEntity<ProductDto>(productService.addProductCategory(product_ID, category_ID),
				HttpStatus.OK);
	}

	@GetMapping("/{productID}")
	public ResponseEntity<ProductDto> getProductByID(@PathVariable("productID") String productID) {

		return new ResponseEntity<ProductDto>(productService.get(productID), HttpStatus.OK);

	}

	@PostMapping("/{productID}")
	public ResponseEntity<ProductDto> updateProductByID(@PathVariable("productID") String productID,
			@RequestBody ProductDto productDto) {

		return new ResponseEntity<ProductDto>(productService.update(productDto, productID), HttpStatus.ACCEPTED);

	}

	@DeleteMapping("/{productID}")
	public ResponseEntity<ApiResponse> deleteProductByID(@PathVariable("productID") String productID) {

		productService.deleteProduct(productID);
		ApiResponse response = ApiResponse.builder().message("Product for given ID deleted successfully ").status(true)
				.httpStatus(HttpStatus.OK).build();

		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pagesize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortType", defaultValue = "0", required = false) String sortType) {
		PageableResponse<ProductDto> response = productService.getAll(pageNumber, pageSize, sortBy, sortType);

		return new ResponseEntity<PageableResponse<ProductDto>>(response, HttpStatus.OK);
	}

	@GetMapping("/live")
	public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pagesize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortType", defaultValue = "0", required = false) String sortType) {
		PageableResponse<ProductDto> response = productService.getAllLive(pageNumber, pageSize, sortBy, sortType);

		return new ResponseEntity<PageableResponse<ProductDto>>(response, HttpStatus.OK);
	}

	@GetMapping("/search/{query}")
	public ResponseEntity<PageableResponse<ProductDto>> searchProduct(@PathVariable("query") String subtitle,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pagesize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortType", defaultValue = "0", required = false) String sortType) {
		PageableResponse<ProductDto> response = productService.searchByTitle(pageNumber, pageSize, sortBy, sortType,
				subtitle);

		return new ResponseEntity<PageableResponse<ProductDto>>(response, HttpStatus.OK);
	}

	@PostMapping("/image/{productID}")
	public ResponseEntity<ImageResponse> uploadProductImage(@RequestParam("image") MultipartFile image,
			@PathVariable("productID") String productID) throws IOException {

		String imageName = imageService.uploadImage(image, imagePath);

		ProductDto productDto = productService.get(productID);
		productDto.setImageName(imageName);
		productService.update(productDto, productID);
		ImageResponse response = ImageResponse.builder().imageName(imageName).message("Image Uploaded successfully")
				.status(true).build();

		return new ResponseEntity<ImageResponse>(response, HttpStatus.OK);

	}


	@GetMapping("/image/{productID}")
	public void getProductImage(@PathVariable("productID") String productID, HttpServletResponse httpServletResponse)
			throws IOException {

		ProductDto productDto = productService.get(productID);
		String imageName = productDto.getImageName();
		InputStream inputStream = imageService.getImage(imagePath, imageName);
		httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());
	}

}
