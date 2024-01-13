package com.gp.electro.store.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.electro.store.dtos.PageableResponse;
import com.gp.electro.store.dtos.UserDto;
import com.gp.electro.store.entities.User;
import com.gp.electro.store.exceptions.ResourceNotFoundException;
import com.gp.electro.store.helper.Helper;
import com.gp.electro.store.repositories.UserRepo;
import com.gp.electro.store.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper mapper;

	@Value("${user.profile.image.path}")
	private String imagePath;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserDto createUser(UserDto userDto) {
		String idRandom = UUID.randomUUID().toString();
		userDto.setUserId(idRandom);
		User userToSave = dtoToEntity(userDto);
		User savedUser = userRepo.save(userToSave);
		UserDto savedDto = entityToDto(savedUser);
		return savedDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user with given id not found"));
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setGender(userDto.getGender());
		user.setImageName(userDto.getImageName());
		user.setPassword(userDto.getPassword());
		userRepo.save(user);
		return entityToDto(user);
	}

	@Override
	public List<String> allUserEmail() {
		List<String> emails = userRepo.getAllEmail();
		List<String> allemail = emails.stream().filter(email -> email != null).collect(Collectors.toList());
		return allemail;
	}

	@Override
	public void deleteUser(String userId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not present"));
		// Delete user image

		String fullPath = imagePath + user.getImageName();

		try {
			Path path = Paths.get(fullPath);
			Files.delete(path);
		} catch (NoSuchFieldError e) {
			logger.info("No image found for  user--- {}", user.getName());
		} catch (IOException e) {

			logger.error("Error deleting image for user {}", user.getName(), e);
		}
		userRepo.delete(user);
	}

	@Override
	public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortType) {

		Sort sort = (sortType.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending())
				: (Sort.by(sortBy).ascending());

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<User> page = userRepo.findAll(pageable);

		PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(page, UserDto.class);

		return pageableResponse;

	}

	@Override
	public UserDto getUserById(String UserId) {
		User user = userRepo.findById(UserId)
				.orElseThrow(() -> new ResourceNotFoundException("User with given id not present"));

		return entityToDto(user);
	}

	@Override
	public UserDto getuserByEmail(String userEmail) {
		User user = userRepo.findByEmail(userEmail)
				.orElseThrow(() -> new ResourceNotFoundException("User with given email is not present"));
		return entityToDto(user);
	}

	@Override
	public List<UserDto> searchUsers(String keyword) {
		// TODO Auto-generated method stub

		List<User> users = userRepo.findByNameContaining(keyword)
				.orElseThrow(() -> new ResourceNotFoundException("User with given keyword is not present"));
		List<UserDto> userDtos = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	private UserDto entityToDto(User savedUser) {
//		UserDto userDto = UserDto .builder()
//		.about(savedUser.getAbout())
//		.email(savedUser.getEmail())
//		.imageName(savedUser.getImageName())
//		.name(savedUser.getName())
//		.userId(savedUser.getUserId())
//		.password(savedUser.getPassword())
//		.gender(savedUser.getGender())
//		.build();
		return mapper.map(savedUser, UserDto.class);
	}

	private User dtoToEntity(UserDto userDto) {
//		User user = User.builder().userId(userDto.getUserId()).name(userDto.getName()).password(userDto.getPassword())
//				.email(userDto.getEmail()).gender(userDto.getGender()).about(userDto.getAbout())
//				.imageName(userDto.getImageName()).build();
		return mapper.map(userDto, User.class);
	}

}
