package com.example.notes.service;

import com.example.notes.model.LabelModel;
import com.example.notes.model.NotesModel;
import com.example.notes.model.UserModel;

import java.util.List;

public interface LabelService {

    LabelModel createLabel(String labelName, String token);

    List<LabelModel> getAllLabelNotes();

    List<LabelModel> getAllLabelsByUserId(String token);

    void deleteLabelById(int labelId);

    LabelModel editLabelById(int labelId, String labelName);

    List<NotesModel> getAllLabelNotesByLabelId(int labelId, String token);

    String getCurrentLabel(int labelId, String token);
}
