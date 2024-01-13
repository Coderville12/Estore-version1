package com.gp.electro.store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import validate.ImageNameValid;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

	private String userId;

	@Size(min = 2, max = 20, message = "Invalid Name either too short or too long")
	private String name;

	//@NotBlank
	//@Email(message = "Invalid Email!!")
	@Pattern(regexp = "^[a-z0-9][A-Za-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid email entered")
	private String email;

	@NotBlank(message = "Password is required")
	private String password;

	@Size(min = 4, max = 6, message = "Invalid Gender ")
	private String gender;

	@NotBlank(message = "Write something about yourself")
	private String about;

	@ImageNameValid
	private String imageName;
}
