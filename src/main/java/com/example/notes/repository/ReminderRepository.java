package com.example.notes.repository;

import com.example.notes.model.ReminderModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReminderRepository extends JpaRepository<ReminderModel, Integer> {

    @Transactional
    @Modifying
    @Query(value = "delete from reminder_data where notes_id = :notesId and reminder_id = :reminderId", nativeQuery = true)
    void deleteByNotesId(int notesId, int reminderId);

    @Transactional
    @Modifying
    @Query(value = "delete from reminder_data where notes_id = :notesId", nativeQuery = true)
    void deleteByNotesId(int notesId);

    @Query(value = "select * from reminder_data where notes_id = :notesId", nativeQuery = true)
    ReminderModel findByNotes(int notesId);
}
