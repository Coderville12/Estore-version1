package com.gp.electro.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.electro.store.dtos.CategoryDto;
import com.gp.electro.store.dtos.PageableResponse;
import com.gp.electro.store.dtos.ProductDto;
import com.gp.electro.store.entities.Category;
import com.gp.electro.store.entities.Product;
import com.gp.electro.store.exceptions.ResourceNotFoundException;
import com.gp.electro.store.helper.Helper;
import com.gp.electro.store.repositories.CategoryRepo;
import com.gp.electro.store.repositories.ProductRepo;
import com.gp.electro.store.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public ProductDto createProduct(ProductDto productDto) {
	// Product product = mapper.map(productDto, Product.class);
		String productID = UUID.randomUUID().toString();
		productDto.setProductID(productID);

		productDto.setAddedDate(new Date());
		Product product = mapper.map(productDto, Product.class);
		Product savedProduct = productRepo.save(product);

		return mapper.map(savedProduct, ProductDto.class);

	}

	@Override
	public ProductDto update(ProductDto productDto, String productID) {

		Product prod = productRepo.findById(productID)
				.orElseThrow(() -> new ResourceNotFoundException("Product with given Id is not present"));

		prod.setTitle(productDto.getTitle());
		prod.setDescription(productDto.getDescription());
		prod.setDiscountedPrice(productDto.getDiscountedPrice());
		prod.setLive(productDto.isLive());
		prod.setPrice(productDto.getPrice());
		prod.setQuantity(productDto.getQuantity());
		prod.setStock(productDto.isStock());
		prod.setImageName(productDto.getImageName());
		// CategoryDto categoryDto = productDto.getCategory();
		// String category_ID = categoryDto.getCategoryId();
//		Category category = categoryRepo.findById(category_ID)
//				.orElseThrow(() -> new ResourceNotFoundException("Mapped Category is not present"));
//		prod.setCategory(category);

		return mapper.map(productRepo.save(prod), ProductDto.class);

	}

	@Override
	public void deleteProduct(String productID) {
		Product product = productRepo.findById(productID)
				.orElseThrow(() -> new ResourceNotFoundException("Product with given ID doesnt exits"));

		productRepo.delete(product);
	}

	@Override
	public ProductDto get(String producID) {

		Product prod = productRepo.findById(producID)
				.orElseThrow(() -> new ResourceNotFoundException("Product with given ID not available!"));

		return mapper.map(prod, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortType) {

		Sort sort = (sortType.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Product> page = productRepo.findAll(pageable);
//
//		System.out.println("Before if-condition");
//		if (page != null && page.getContent() != null && page.getContent().get(0) != null) {
//			System.out.println("" + page.getContent().get(0).getQuantity());
//			System.out.println("" + page.getContent().get(0).getDescription());
//		}
//		System.out.println("After if-condition");

		return Helper.getPageableResponse(page, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortType) {
		Sort sort = (sortType.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepo.findByLive(pageable, true);
		return Helper.getPageableResponse(page, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(int pageNumber, int pageSize, String sortBy, String sortType,
			String subtitle) {
		Sort sort = (sortType.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepo.findByTitleContaining(subtitle, pageable);
		return Helper.getPageableResponse(page, ProductDto.class);
	}

	@Override
	public ProductDto createProductWithCategory(ProductDto productDto, String category_ID) {
		String product_ID = UUID.randomUUID().toString();
		productDto.setProductID(product_ID);
		productDto.setAddedDate(new Date());
		CategoryDto categoryFetched = mapper.map(categoryRepo.findById(category_ID)
				.orElseThrow(() -> new ResourceNotFoundException("No such Category present")), CategoryDto.class);
		productDto.setCategory(categoryFetched);
		// productDto.setCategory(categoryFetched);
		Product product = mapper.map(productDto, Product.class);
		return mapper.map(productRepo.save(product), ProductDto.class);

	}

	@Override
	public ProductDto addProductCategory(String product_ID, String category_ID) {
		Category categoryFetched = categoryRepo.findById(category_ID)
				.orElseThrow(() -> new ResourceNotFoundException("No such Category present"));
		Product product = productRepo.findById(product_ID)
				.orElseThrow(() -> new ResourceNotFoundException("Product with given id not present"));
		product.setCategory(categoryFetched);
//		Product product = mapper.map(productDto, Product.class);
		return mapper.map(productRepo.save(product), ProductDto.class);

	}

	@Override
	public PageableResponse<ProductDto> getProductsWithCategory(int pageNumber, int pageSize, String sortBy,
			String sortType, String category_ID) {
		Sort sort = (sortType.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		//Page page = pa
	
		return null;
	}


}
