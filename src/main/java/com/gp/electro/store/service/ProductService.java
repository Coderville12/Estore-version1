package com.gp.electro.store.service;

import com.gp.electro.store.dtos.PageableResponse;
import com.gp.electro.store.dtos.ProductDto;

public interface ProductService {
	ProductDto createProduct(ProductDto productDto);

	ProductDto update(ProductDto productDto, String productId);

	void deleteProduct(String productID);

	ProductDto get(String producId);

	PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortType);

	PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortType);

	PageableResponse<ProductDto> searchByTitle(int pageNumber, int pageSize, String sortBy, String sortType,
			String subtitle);

	// ProductDto addProductCategory(ProductDto productDto, String category_ID);

	ProductDto createProductWithCategory(ProductDto productDto, String category_ID);

	ProductDto addProductCategory(String product_ID, String category_ID);
	
	PageableResponse<ProductDto> getProductsWithCategory(int pageNumber, int pageSize, String sortBy, String sortType,
			String category_ID);

}
