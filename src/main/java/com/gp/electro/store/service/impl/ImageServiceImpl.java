package com.gp.electro.store.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gp.electro.store.exceptions.BadRequest;
import com.gp.electro.store.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);


	@Override
	public String uploadImage(MultipartFile file, String imageUploadPath) throws IOException {
		String orginalName = file.getOriginalFilename();
		logger.info(orginalName);
		
		logger.info("imageUploadPath: {}", imageUploadPath);
		String extention = orginalName.substring(orginalName.lastIndexOf("."));
		String randomName = UUID.randomUUID().toString();
		String imageNameWithExtension = randomName + extention;
		String fullImageUploadPath = imageUploadPath+ imageNameWithExtension;
		
		
		logger.info("Full path for image uploade is : {}" ,fullImageUploadPath);
		//+ File.separator 
		if (extention.equalsIgnoreCase(".jpg") || extention.equalsIgnoreCase(".jpeg")
				|| extention.equalsIgnoreCase(".png")) {
			File imageFile = new File(imageUploadPath);

			if (!imageFile.exists()) {
				imageFile.mkdirs();
			}

			Files.copy(file.getInputStream(), Paths.get(fullImageUploadPath));
			return  imageNameWithExtension;
		} else {
			throw new BadRequest("Bad request... image with this extension not allowed!");
		}
	}

	@Override
	public InputStream getImage(String path, String name) throws FileNotFoundException {

		String imageLocationFullPath = path + File.separator + name;
		InputStream inputStream = new FileInputStream(imageLocationFullPath);
		return inputStream;

	}}
//
//	@Override
//	public String uploadImage(MultipartFile file, String imageUploadPath) throws IOException {
//		String orginalName = file.getOriginalFilename();
//
//		String extention = orginalName.substring(orginalName.lastIndexOf("."));
//
//		String randomName = UUID.randomUUID().toString();
//		String imageNameWithExtension = randomName + extention;
//
//		String fullImageUploadPath = imageUploadPath + File.separator + imageNameWithExtension;
//		if (extention.equalsIgnoreCase(".jpg") || extention.equalsIgnoreCase(".jpeg")
//				|| extention.equalsIgnoreCase(".png")) {
//			File imageFolder = new File(fullImageUploadPath);
//
//			if (!imageFolder.exists()) {
//				imageFolder.mkdirs();
//			}
//			
//			
//			Files.copy(file.getInputStream(),Paths.get(fullImageUploadPath));
//			return "Image Uploaded successfully " + imageNameWithExtension;
//		}else {
//			throw new BadRequest("Bad request... image with this extention not allowed!");
//		}
//
//		
//	}
//


