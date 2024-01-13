package com.gp.electro.store.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;



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
import com.gp.electro.store.dtos.UserDto;
import com.gp.electro.store.payload.ApiResponse;
import com.gp.electro.store.payload.UserIndividualData;
import com.gp.electro.store.service.FileService;
import com.gp.electro.store.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Value("${user.profile.image.path}")
	private String imagePath;

//	@Value("${user.profile.image.path}")
//	private String imagePath;

	// create
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
		UserDto createdUser = userService.createUser(user);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId, @RequestBody UserDto updatedUser) {

		// return userService.updateUser(updatedUser, userId);
		return new ResponseEntity<UserDto>(userService.updateUser(updatedUser, userId), HttpStatus.ACCEPTED);

	}

	// delete
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") String userId) {
		
		userService.deleteUser(userId);
		// return userService.updateUser(updatedUser, userId);
		ApiResponse response = ApiResponse.builder().message("User deleted successfully.").status(true)
				.httpStatus(HttpStatus.ACCEPTED).build();
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// get all
	@GetMapping
	public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,

			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,

			@RequestParam(value = "sortType", defaultValue = "asc", required = false) String sortType

	) {
		PageableResponse<UserDto> allUsers = userService.getAllUsers(pageNumber, pageSize, sortBy, sortType);
		return new ResponseEntity<PageableResponse<UserDto>>(allUsers, HttpStatus.OK);

	}

	// get single
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId) {
		UserDto user = userService.getUserById(userId);
		return new ResponseEntity<UserDto>(user, HttpStatus.OK);
	}

	// get by email
	@GetMapping("/email/{userEmail}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable("userEmail") String userEmail) {
		UserDto user = userService.getUserById(userEmail);
		return new ResponseEntity<UserDto>(user, HttpStatus.OK);
	}

	// search users
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<UserDto>> serachUsers(@PathVariable("keyword") String keyword) {
		List<UserDto> users = userService.searchUsers(keyword);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/all_emails")
	public ResponseEntity<List<UserIndividualData>> getEmails() {
		List<String> usersEmail = userService.allUserEmail();
		List<UserIndividualData> userIndividualDatas = usersEmail.stream()
				.map(e -> UserIndividualData.builder().email(e).build()).collect(Collectors.toList());

		return new ResponseEntity<>(userIndividualDatas, HttpStatus.OK);
	}

	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image,
			@PathVariable("userId") String userId) throws IOException {

		String imageName = fileService.uploadImage(image, imagePath);
		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName)
				.message("image uploaded successfully").httpStatus(HttpStatus.CREATED).status(true).build();

		UserDto user = userService.getUserById(userId);
		user.setImageName(imageName);
		userService.updateUser(user, userId);
		return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);

	}

	@GetMapping("/image/{userId}")
	public void getUserImage(@PathVariable("userId") String userId, HttpServletResponse httpServletResponse)
			throws IOException {
		UserDto userDto = userService.getUserById(userId);

		InputStream inputStream = fileService.getRersouce(imagePath, userDto.getImageName());
		httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());

	}

//	@PostMapping("/image/{userId}")
//	public ResponseEntity<ImageResponse> uploadUserImage(
//	        @RequestParam("userImage") MultipartFile image,
//	        @PathVariable("userId") String userId) throws IOException {
//
//	    String imageName = fileService.uploadImage(image, imagePath);
//
//	    ImageResponse imageResponse = ImageResponse.builder()
//	            .message(imageName)
//	            .httpStatus(HttpStatus.CREATED)
//	            .status(true)
//	            .build();
//
//	    UserDto user = userService.getUserById(userId);
//	    user.setImageName(imageName);
//	    userService.updateUser(user, userId);
//
//	    return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
//	}
//	

}
