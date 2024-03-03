package com.example.notes.controller;

import com.example.notes.dto.ResponseDTO;
import com.example.notes.model.LabelModel;
import com.example.notes.model.NotesModel;
import com.example.notes.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    //creating label
    @PostMapping("/createlabel/{labelName}/{token}")
    public ResponseEntity<ResponseDTO> createLabel(@PathVariable("labelName") String labelName, @PathVariable("token") String token){
        LabelModel labelModel = labelService.createLabel(labelName, token);
        ResponseDTO responseDTO = new ResponseDTO("Label Created Successfully", labelModel);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }

    //getting all labeled notes
    @GetMapping("/getlabelnotes")
    public ResponseEntity<ResponseDTO> getAllLabelNotes(){
        List<LabelModel> labelModelList = labelService.getAllLabelNotes();
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", labelModelList);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }

    //getting labels by user
    @GetMapping("/getlabelsbyuser/{token}")
    public ResponseEntity<ResponseDTO> getAllLabelsByUserId(@PathVariable("token") String token){
        List<LabelModel> labelModelList = labelService.getAllLabelsByUserId(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", labelModelList);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }

    //deleting label by label id
    @DeleteMapping("/deletelabelbyid/{labelId}")
    public ResponseEntity<ResponseDTO> deleteLabelById(@PathVariable("labelId") int labelId){
        labelService.deleteLabelById(labelId);
        ResponseDTO responseDTO = new ResponseDTO("Deleted Data Successfully", "Delete id - "+labelId);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //editing label
    @PutMapping("/editlabelbyid/{labelId}/{labelName}")
    public ResponseEntity<ResponseDTO> editLabelById(@PathVariable("labelId") int labelId, @PathVariable("labelName") String labelName){
        LabelModel labelModel = labelService.editLabelById(labelId, labelName);
        ResponseDTO responseDTO = new ResponseDTO("Edited Data Successfully", labelModel);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //getting label notes by label id
    @GetMapping("/getalllabelnotesbylabelid/{labelId}/{token}")
    public ResponseEntity<ResponseDTO> getAllLabelNotesByLabelId(@PathVariable("labelId") int labelId, @PathVariable("token") String token){
        List<NotesModel> notesModelList = labelService.getAllLabelNotesByLabelId(labelId, token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", notesModelList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //getting label notes by label id corresponding to the user
    @GetMapping("/getcurrentlabel/{labelId}/{token}")
    public  ResponseEntity<ResponseDTO> getCurrentLabel(@PathVariable("labelId") int labelId, @PathVariable("token") String token) {
        String labelName = labelService.getCurrentLabel(labelId, token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", labelName);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

}
