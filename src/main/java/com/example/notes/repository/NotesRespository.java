package com.example.notes.repository;

import com.example.notes.model.LabelModel;
import com.example.notes.model.NotesModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRespository extends JpaRepository<NotesModel, Integer> {

    @Query(value = "select * from notes_data where is_archive = 0 and is_trash = 0", nativeQuery = true)
    List<NotesModel> findAllNotes();

    @Query(value = "select * from notes_data where is_archive = 0 and is_trash = 0 and user_id = :userId", nativeQuery = true)
    List<NotesModel> findAllNotesByUserId(int userId);

    @Query(value = "select * from notes_data where is_archive = 1 and is_trash = 0", nativeQuery = true)
    List<NotesModel> findArchiveNotes();

    @Query(value = "select * from notes_data where is_archive = 1 and is_trash = 0 and user_id = :userId", nativeQuery = true)
    List<NotesModel> findAllArchiveNotesByUserId(int userId);

    @Query(value = "select * from notes_data where is_trash = 1 and is_archive = 0 or is_trash = 1 and is_archive = 1", nativeQuery = true)
    List<NotesModel> findTrashNotes();

    @Query(value = "select * from notes_data where is_trash = 1 and is_archive = 0 or is_trash = 1 and is_archive = 1  and user_id = :userId", nativeQuery = true)
    List<NotesModel> findAllTrashNotesByUserId(int userId);

    @Transactional
    @Modifying
    @Query(value = "update notes_data set is_archive = 1 where notes_id = :notesId", nativeQuery = true)
    void setNoteToArchive(int notesId);

    @Transactional
    @Modifying
    @Query(value = "update notes_data set is_archive = 0 where notes_id = :notesId", nativeQuery = true)
    void setNoteToUnArchive(int notesId);

    @Transactional
    @Modifying
    @Query(value = "update notes_data set is_trash = 1 where notes_id = :notesId", nativeQuery = true)
    void setNoteToTrash(int notesId);

    @Transactional
    @Modifying
    @Query(value = "update notes_data set is_trash = 0 where notes_id = :notesId", nativeQuery = true)
    void setNoteToUnTrash(int notesId);

    @Query(value = "select label_id from notes_label where notes_id = :notesId", nativeQuery = true)
    List<Integer> findAllLabels(int notesId);

//    @Transactional
//    @Modifying
//    @Query(value = "update notes_data set is_trash = :labelModelList where notes_id = :notesId", nativeQuery = true)
//    void addNotesToLabel(int notesId, String labelName);

}
