package com.example.tanmayee.service;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadServiceImpl {
	Resource loadFile(Path filePath, String fileName);
	String copyFile(MultipartFile file);
}
