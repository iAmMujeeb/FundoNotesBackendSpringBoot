package com.example.notes.repository;

import com.example.notes.model.ImageModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageModel, Integer> {

    @Query(value = "select image from image_data where notes_id = :notesId", nativeQuery = true)
    byte[] findImageByNotesId(int notesId);

    @Query(value = "select * from image_data where notes_id = :notesId", nativeQuery = true)
    ImageModel findImageDataByNotesId(int notesId);

    Optional<ImageModel> findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "delete from image_data where notes_id = :notesId", nativeQuery = true)
    void deleteByNotesId(int notesId);

}
