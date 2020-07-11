package com.example.tanmayee.controller;


import java.io.IOException;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;
import com.example.tanmayee.message.ResponseMessage;
import com.example.tanmayee.service.FileUploadService;


@RestController
public class FileUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	
    @Autowired
    private FileUploadService docService;
      
    @PostMapping("/uploadFile")
    public ResponseEntity<ResponseMessage>  uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) {
        
    	docService.copyFile(file);
    	return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(file.getOriginalFilename() +" Uploaded Successfully!!", file.getSize()));
    	
        //return ResponseEntity.ok(docDownloadUri);
        
    }
	
    
    @PostMapping("/uploadMultipleFiles")   
    public List<ResponseEntity<ResponseMessage>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
    	return Arrays.asList(files)
                .stream()
                .map(file -> uploadToLocalFileSystem(file))
                .collect(Collectors.toList());
    }
    	    

    @GetMapping("/downloadFile")    
    public ResponseEntity<Resource> downloadFile(@RequestParam("filePath") Path filePath, @RequestParam("fileName")  String fileName,HttpServletRequest request) {
       
    	// Load file as a resource
        Resource resource = docService.loadFile(filePath, fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
