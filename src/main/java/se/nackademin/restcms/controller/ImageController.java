package se.nackademin.restcms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import se.nackademin.restcms.entities.ImageFile;
import se.nackademin.restcms.service.ImageFileStorageServiceImpl;

@RestController
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final ImageFileStorageServiceImpl imageFileStorageService;

    public ImageController(ImageFileStorageServiceImpl imageFileStorageService) {
        this.imageFileStorageService = imageFileStorageService;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        ImageFile imageFile = imageFileStorageService.storeImageFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(imageFile.getId())
                .toUriString();
	
	    return ResponseEntity.ok()
			    .contentType(MediaType.TEXT_HTML)
			    .body(fileDownloadUri);
    }

    //ta inte bort
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        ImageFile imageFile = imageFileStorageService.storeImageFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(imageFile.getId())
                .toUriString();
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(fileDownloadUri);

    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        ImageFile imageFile = imageFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageFile.getFileName() + "\"")
                .body(new ByteArrayResource(imageFile.getData()));
    }

}
