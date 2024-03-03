package com.example.notes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reminder_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReminderModel {

    @Id
    @GeneratedValue
    private int reminderId;

    private String reminder;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notes_id")
    private NotesModel notesModel;

    public ReminderModel(NotesModel notesModel, String reminder) {
        this.notesModel = notesModel;
        this.reminder = reminder;
    }
}
