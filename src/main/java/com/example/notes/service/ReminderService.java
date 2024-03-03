package com.example.notes.service;

import com.example.notes.model.ReminderModel;

import java.util.List;

public interface ReminderService {

    ReminderModel createReminder(int notesId, String reminder);

    void deleteReminder(int notesId, int reminderId);

    ReminderModel getReminder(int notesId);

    List<ReminderModel> getAllReminder();
}
