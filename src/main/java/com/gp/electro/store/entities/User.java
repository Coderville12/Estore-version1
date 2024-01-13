package com.gp.electro.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	//@GeneratedValue()
	private String userId;
	@Column(name = "user_name")
	private String name;
	@Column(name = "user_email",unique = true)
	private String email;
	@Column(name = "user_password", length=10)
	private String password;
	@Column(name = "gender")
	private String gender;
	@Column(name = "about", length = 500)
	private String about;
	@Column(name = "user_image_name")
	private String imageName;

}
