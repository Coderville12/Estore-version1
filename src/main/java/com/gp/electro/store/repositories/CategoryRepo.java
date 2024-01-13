package com.gp.electro.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gp.electro.store.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, String> {

}
