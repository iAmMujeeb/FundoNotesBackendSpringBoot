package com.example.notes.service;

import com.example.notes.model.ImageModel;
import com.example.notes.model.NotesModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    ImageModel uploadImage(MultipartFile file) throws IOException;

    ImageModel uploadImage(int notesId, MultipartFile file) throws IOException;

    ImageModel getInfoByImageName(String name);

    byte[] getImage(String name);

    byte[] getImageByNotesId(int notesId);

    List<ImageModel> getAllImage();

    void deleteImageByNotesId(int notesId);
}
