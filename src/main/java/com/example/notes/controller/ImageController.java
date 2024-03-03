package com.example.notes.controller;


import com.example.notes.dto.ResponseDTO;
import com.example.notes.model.ImageModel;
import com.example.notes.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    //uploading image
    @PostMapping(value = "/{notesId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> uploadImage(@PathVariable("notesId") int notesId, @RequestParam("image") MultipartFile file) throws IOException {
        ImageModel imageModel = imageService.uploadImage(notesId, file);
        ResponseDTO responseDTO = new ResponseDTO("Image Uploaded Successfully", imageModel);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //getting all images
    @GetMapping("/getallimage")
    public ResponseEntity<ResponseDTO> getAllImage() {
        List<ImageModel> imageModelList = imageService.getAllImage();
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", imageModelList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //getting image info by image name
    @GetMapping("/info/{name}")
    public ResponseEntity<?> getImageInfoByName(@PathVariable("name") String name) {
        ImageModel imageModel = imageService.getInfoByImageName(name);

        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", imageModel);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //getting image by name
    @GetMapping("/{name}")
    public ResponseEntity<?> getImageByName(@PathVariable("name") String name) {
        byte[] image = imageService.getImage(name);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }

    //getting image by note
    @GetMapping("/getimagebynotesid/{notesId}")
    public ResponseEntity<?> getImageByNotesId(@PathVariable("notesId") int notesId) {
        byte[] image = imageService.getImageByNotesId(notesId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }

    //Deleting image by note
    @DeleteMapping("/deleteimagebynotesid/{notesId}")
    public ResponseEntity<ResponseDTO> deleteImageByNotesId(@PathVariable("notesId") int notesId) {
        imageService.deleteImageByNotesId(notesId);
        ResponseDTO responseDTO = new ResponseDTO("Deleted image Successfully","with Note Id - "+notesId);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }


}
