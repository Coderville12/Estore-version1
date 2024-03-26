package com.gp.electro.store.entities;

import java.util.Date;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

	@Id
	@Column(name = "id")
	private String productID;

	private String title;

	@Column(length = 10000)
	private String description;

	private int price;

	private int discountedPrice;

	private int quantity;

	private Date addedDate;

	private boolean live;

	private boolean stock;

	private String imageName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_ID")
	private Category category;

}
