package com.example.notes.service;

import com.example.notes.dto.NotesDTO;
import com.example.notes.model.LabelModel;
import com.example.notes.model.NotesModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NotesService {

    NotesModel createNotesData(NotesDTO notesDTO, String token);

    List<NotesModel> getNotesData();

    List<NotesModel> getArchiveNotes();

    List<NotesModel> getTrashNotes();

    NotesModel setNoteToArchive(int notesId);

    NotesModel setNoteToUnArchive(int notesId);

    NotesModel setNoteToTrash(int notesId);

    NotesModel setNoteToUnTrash(int notesId);

    void deleteNotesById(int notesId);

    List<NotesModel> getAllNotesByUserId(String token);

    List<NotesModel> getAllArchiveNotesByUserId(String token);

    List<NotesModel> getAllTrashNotesByUserId(String token);

    NotesModel addNotesToLabel(int notesId, List<String> labelNameList);

    List<String> getLabelNamesByNoteId(int notesId);

    NotesModel removeNotesfromLabel(int notesId, String labelName);

    NotesModel updateNote(int notesId, String token, NotesDTO notesDTO);
}
