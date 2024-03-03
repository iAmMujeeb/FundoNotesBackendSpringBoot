package com.example.notes.service;

import com.example.notes.exception.NotesException;
import com.example.notes.model.NotesModel;
import com.example.notes.model.ReminderModel;
import com.example.notes.repository.NotesRespository;
import com.example.notes.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReminderServiceImp implements ReminderService {

    @Autowired
    private NotesRespository notesRespository;

    @Autowired
    private ReminderRepository reminderRepository;

    @Override
    public ReminderModel createReminder(int notesId, String reminder) {
        NotesModel notesModel = notesRespository.findById(notesId).orElseThrow(() -> new NotesException("Notes Data Not Found"));
        ReminderModel reminderModel = new ReminderModel(notesModel, reminder);
        return reminderRepository.save(reminderModel);
    }

    @Override
    public void deleteReminder(int notesId, int reminderId) {
        NotesModel notesModel = notesRespository.findById(notesId).orElseThrow(() -> new NotesException("Notes Data Not Found"));
        ReminderModel reminderModel = reminderRepository.findById(reminderId).orElseThrow(() -> new NotesException("Reminder Data Not Found"));
        if ((notesModel != null) && (reminderModel != null)) {
            reminderRepository.deleteByNotesId(notesId, reminderId);
        }
    }

    @Override
    public ReminderModel getReminder(int notesId) {
        NotesModel notesModel = notesRespository.findById(notesId).orElseThrow(() -> new NotesException("Notes Data Not Found"));
        if (notesModel != null) {
            return reminderRepository.findByNotes(notesId);
        } else {
            return null;
        }
    }

    @Override
    public List<ReminderModel> getAllReminder() {
        return reminderRepository.findAll();
    }

}
