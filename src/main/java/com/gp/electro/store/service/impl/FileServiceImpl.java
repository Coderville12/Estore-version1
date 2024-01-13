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
import com.gp.electro.store.service.FileService;
@Service
public class FileServiceImpl implements FileService {

	private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

	@Override
	public String uploadImage(MultipartFile file, String path) throws IOException {

		String originalFileName = file.getOriginalFilename();
		logger.info(originalFileName);
		String extention = originalFileName.substring(originalFileName.lastIndexOf("."));
		String randomeName = UUID.randomUUID().toString();
		String fileNameWithExtention = randomeName + extention;

		String fullPathWithFileName = path + File.separator + fileNameWithExtention;
		if (extention.equalsIgnoreCase(".jpg") || extention.equalsIgnoreCase(".png")
				|| extention.equalsIgnoreCase(".jpeg")) {
			File folder = new File(path);

			if (!folder.exists()) {

				folder.mkdirs();
			}
			
			Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
			return fileNameWithExtention;

		} else {
			throw new BadRequest("Bad request... File with this extention not allowed!");
		}

	}

	@Override
	public InputStream getRersouce(String path, String name) throws FileNotFoundException {
		
		
		String fullFileName = path +File.separator + name;
		InputStream inputStream = new FileInputStream(fullFileName);
		return inputStream;
	
		
	}

}
