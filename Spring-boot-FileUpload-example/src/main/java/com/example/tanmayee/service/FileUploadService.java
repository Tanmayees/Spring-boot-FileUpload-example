package com.example.tanmayee.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.tanmayee.exception.FileUploadException;
import com.example.tanmayee.exception.MaximumSizeException;
import com.example.tanmayee.model.File;

@Service
public class FileUploadService implements FileUploadServiceImpl{
	
	private final Path docUploadLocation;

    @Autowired
    public FileUploadService(File doc) {
        this.docUploadLocation = Paths.get(doc.getUploadPath())
                .toAbsolutePath().normalize();

        try {        	
            Files.createDirectories(this.docUploadLocation);
        } catch (Exception ex) {
            throw new FileUploadException("Could not create the directory where the uploaded files will be stored.", ex);
            
        }
    }

    public String copyFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileUploadException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
           Path targetLocation = this.docUploadLocation.resolve(fileName);            

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } 
        catch (IOException ex) {
            throw new FileUploadException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public Resource loadFile(Path filePath, String fileName) {
        try {
  
            filePath = filePath.resolve(fileName).normalize();
            
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileUploadException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileUploadException("File not found " + fileName, ex);
        }
    }
}
