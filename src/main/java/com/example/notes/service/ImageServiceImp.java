package com.example.notes.service;

import com.example.notes.exception.NotesException;
import com.example.notes.model.ImageModel;
import com.example.notes.model.NotesModel;
import com.example.notes.repository.ImageRepository;
import com.example.notes.repository.NotesRespository;
import com.example.notes.util.ImageUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    private NotesRespository notesRespository;

    @Autowired
    private ImageRepository imageRepository;

    public ImageModel uploadImage(MultipartFile file) throws IOException {

        return imageRepository.save(ImageModel.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes())).build());

    }

    @Override
    public ImageModel uploadImage(int notesId, MultipartFile file) throws IOException {
        NotesModel notesModel = notesRespository.findById(notesId).orElseThrow(() -> new NotesException("Notes Data Not Found"));
        return imageRepository.save(ImageModel.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .notesModel(notesModel)
                .imageData(ImageUtil.compressImage(file.getBytes())).build());
    }

    @Transactional
    public ImageModel getInfoByImageName(String name) {
        Optional<ImageModel> dbImage = imageRepository.findByName(name);

        return ImageModel.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .imageData(ImageUtil.decompressImage(dbImage.get().getImageData())).build();

    }

    @Transactional
    public byte[] getImage(String name) {
        Optional<ImageModel> dbImage = imageRepository.findByName(name);
        byte[] image = ImageUtil.decompressImage(dbImage.get().getImageData());
        return image;
    }

    @Override
    public byte[] getImageByNotesId(int notesId) {
//        Optional<ImageModel> dbImage = imageRepository.findImageByNotesId(notesId);
        byte[] dbImage = imageRepository.findImageByNotesId(notesId);
        byte[] image = ImageUtil.decompressImage(dbImage);
        return image;
    }

    @Override
    public List<ImageModel> getAllImage() {
        return imageRepository.findAll();
    }

    @Override
    public void deleteImageByNotesId(int notesId) {
        ImageModel imageModel = imageRepository.findImageDataByNotesId(notesId);
        if (imageModel != null) {
            imageRepository.deleteByNotesId(notesId);
        } else {
            throw new NotesException("Image Data not found");
        }
    }

}
