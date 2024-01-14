package com.gp.electro.store.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	
	
	String uploadImage (MultipartFile file, String imageUploadPath)throws IOException;
	InputStream getImage(String path, String name)throws FileNotFoundException;

}
