package com.gp.electro.store.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gp.electro.store.entities.Product;

public interface ProductRepo extends JpaRepository<Product, String> {

	
	Page<Product> findByTitleContaining(String subTitle, Pageable pageable);
	
	Page<Product> findByLive(Pageable pageable, boolean isLive);
	//List<Product> findByLiveTrue(boolean isLive);
	
	
	
	
	
}
