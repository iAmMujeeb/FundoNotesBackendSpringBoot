package com.example.notes.controller;

import com.example.notes.dto.ResponseDTO;
import com.example.notes.model.ReminderModel;
import com.example.notes.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/reminder")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    //creating reminder
    @PostMapping("/createreminder/{notesId}/{reminder}")
    public ResponseEntity<ResponseDTO> createReminder(@PathVariable("notesId") int notesId,@PathVariable("reminder") String reminder) {
        ReminderModel reminderModel = reminderService.createReminder(notesId, reminder);
        ResponseDTO responseDTO = new ResponseDTO("Reminder Created Successfully", reminderModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //getting reminder by note id
    @GetMapping("/getreminder/{notesId}")
    public ResponseEntity<ResponseDTO> getReminder(@PathVariable("notesId") int notesId) {
        ReminderModel reminderModel = reminderService.getReminder(notesId);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successfully", reminderModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //getting all reminder
    @GetMapping("/getallreminder")
    public ResponseEntity<ResponseDTO> getAllReminder() {
        List<ReminderModel> reminderModels = reminderService.getAllReminder();
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successfully", reminderModels);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //deleting the reminder
    @DeleteMapping("/deletereminder/{notesId}/{reminderId}")
    public ResponseEntity<ResponseDTO> deleteReminder(@PathVariable("notesId") int notesId, @PathVariable("reminderId") int reminderId) {
        reminderService.deleteReminder(notesId, reminderId);
        ResponseDTO responseDTO = new ResponseDTO("Reminder Deleted Successfully", "reminder Id = "+reminderId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
