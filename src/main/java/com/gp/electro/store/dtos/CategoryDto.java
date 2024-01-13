package com.gp.electro.store.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

	private String categoryId;

	@NotBlank
	@Min(value = 4, message = "Title must be of minimum 4 characters!")
	private String title;

	@NotBlank(message = "Description is required....!!")
	private String discription;
	
	@NotBlank(message = "Cover Image is required....!")
	private String coverImage;

}
