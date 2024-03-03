package com.example.notes.controller;

import com.example.notes.dto.NotesDTO;
import com.example.notes.dto.ResponseDTO;
import com.example.notes.model.ImageModel;
import com.example.notes.model.LabelModel;
import com.example.notes.model.NotesModel;
import com.example.notes.repository.NotesRespository;
import com.example.notes.service.ImageService;
import com.example.notes.service.NotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.Multipart;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    ImageService imageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotesService notesService;

    @Autowired
    private NotesRespository notesRespository;

    //creating notes
    @PostMapping("/create/{token}")
    public ResponseEntity<?> createNotesData(@Valid @RequestBody NotesDTO notesDTO, @PathVariable("token") String token){
        NotesModel notesModel = notesService.createNotesData(notesDTO, token);
        ResponseDTO responseDTO = new ResponseDTO("Note Data Created Successfully",notesModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //getting all notes which are not archive and trash by user
    @GetMapping("/getallnotesbyuserid/{token}")
    public ResponseEntity<ResponseDTO> getAllNotesByUserId(@PathVariable("token") String token){
        List<NotesModel> notesModelList = notesService.getAllNotesByUserId(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", notesModelList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //getting archive notes by user
    @GetMapping("/getallarchivenotesbyuserid/{token}")
    public ResponseEntity<ResponseDTO> getAllArchiveNotesByUserId(@PathVariable("token") String token){
        List<NotesModel> notesModelList = notesService.getAllArchiveNotesByUserId(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successfull", notesModelList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //getting trash notes by user
    @GetMapping("/getalltrashnotesbyuserid/{token}")
    public ResponseEntity<ResponseDTO> getAllTrashNotesByUserId(@PathVariable("token") String token){
        List<NotesModel> notesModelList = notesService.getAllTrashNotesByUserId(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successfull", notesModelList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //getting all notes
    @GetMapping("/get")
    public ResponseEntity<ResponseDTO> getNotesData(){
        List<NotesModel> notesModelList = notesService.getNotesData();
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successfull", notesModelList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //getting all archive notes
    @GetMapping("/getarchivenotes")
    public ResponseEntity<ResponseDTO> getArchiveNotes(){
        List<NotesModel> notesModelList = notesService.getArchiveNotes();
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successfull", notesModelList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //getting all trash notes
    @GetMapping("/gettrashnotes")
    public ResponseEntity<ResponseDTO> getTrashNotes(){
        List<NotesModel> notesModelList = notesService.getTrashNotes();
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successfull", notesModelList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //setting note to archive
    @PutMapping("/setarchive/{notesId}")
    public ResponseEntity<ResponseDTO> setNotesToArchive(@PathVariable("notesId") int notesId){
        NotesModel notesModel = notesService.setNoteToArchive(notesId);
        ResponseDTO responseDTO = new ResponseDTO("Note Archived", notesModel);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //setting note to unarchive
    @PutMapping("/setunarchive/{notesId}")
    public ResponseEntity<ResponseDTO> setNotesToUnArchive(@PathVariable("notesId") int notesId){
        NotesModel notesModel = notesService.setNoteToUnArchive(notesId);
        ResponseDTO responseDTO = new ResponseDTO("Note Unarchived", notesModel);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //setting note to trash
    @PutMapping("/settrash/{notesId}")
    public ResponseEntity<ResponseDTO> setNotesToTrash(@PathVariable("notesId") int notesId){
        NotesModel notesModel = notesService.setNoteToTrash(notesId);
        ResponseDTO responseDTO = new ResponseDTO("Note Trashed", notesModel);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //setting note to untrash
    @PutMapping("/setuntrash/{notesId}")
    public ResponseEntity<ResponseDTO> setNotesToUnTrash(@PathVariable("notesId") int notesId){
        NotesModel notesModel = notesService.setNoteToUnTrash(notesId);
        ResponseDTO responseDTO = new ResponseDTO("Note Untrashed", notesModel);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //setting note labels
    @PutMapping("/setlabel/{notesId}")
    public ResponseEntity<ResponseDTO> addNotesToLabel(@PathVariable("notesId") int notesId, @RequestBody List<String> labelNameList) {
        NotesModel notesModel = notesService.addNotesToLabel(notesId, labelNameList);
        ResponseDTO responseDTO = new ResponseDTO("Label Altered", notesModel);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //remove label from note
    @PutMapping("/removelabel/{notesId}/{labelName}")
    public ResponseEntity<ResponseDTO> removeNotesfromLabel(@PathVariable("notesId") int notesId, @PathVariable("labelName") String labelName) {
        NotesModel notesModel = notesService.removeNotesfromLabel(notesId, labelName);
        ResponseDTO responseDTO = new ResponseDTO("Label Altered", notesModel);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //getting labels by note Id
    @GetMapping("/getlabelname/{notesId}")
    public  ResponseEntity<ResponseDTO> getLabelNamesByNoteId(@PathVariable("notesId") int notesId){
        List<String> labelNameList = notesService.getLabelNamesByNoteId(notesId);
        ResponseDTO responseDTO = new ResponseDTO("Get call successful", labelNameList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //deleting note
    @DeleteMapping("/deletebyid/{notesId}")
    public ResponseEntity<ResponseDTO> deleteNotesById(@PathVariable("notesId") int notesId){
        notesService.deleteNotesById(notesId);
        ResponseDTO responseDTO = new ResponseDTO("Deleted Data Successfully", "Delete id - "+notesId);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //updating note by note id corresponding to the user
    @PutMapping("/updatenote/{notesId}/{token}")
    public ResponseEntity<ResponseDTO> updateNote(@PathVariable("notesId") int notesId, @PathVariable("token") String token, @RequestBody NotesDTO notesDTO) {
        NotesModel notesModel = notesService.updateNote(notesId, token, notesDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated Data Successfully", notesModel);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

}
