package com.example.notes.service;

import com.example.notes.dto.NotesDTO;
import com.example.notes.exception.NotesException;
import com.example.notes.model.LabelModel;
import com.example.notes.model.NotesModel;
import com.example.notes.model.UserModel;
import com.example.notes.repository.*;
import com.example.notes.token.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotesServiceImp implements NotesService{

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private NotesRespository notesRespository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private JwtToken jwtToken;

    @Override
    public NotesModel createNotesData(NotesDTO notesDTO, String token) {
        int userId = jwtToken.decodeToken(token);
        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new NotesException(" User not found"));
        NotesModel notesModel = new NotesModel(notesDTO, userModel);
        return notesRespository.save(notesModel);
    }

    @Override
    public List<NotesModel> getNotesData() {
        return notesRespository.findAllNotes();
    }

    @Override
    public List<NotesModel> getArchiveNotes() {
        return notesRespository.findArchiveNotes();
    }

    @Override
    public List<NotesModel> getTrashNotes() {
        return notesRespository.findTrashNotes();
    }

    @Override
    public NotesModel setNoteToArchive(int notesId) {
        notesRespository.setNoteToArchive(notesId);
        return null;
    }

    @Override
    public NotesModel setNoteToUnArchive(int notesId) {
        notesRespository.setNoteToUnArchive(notesId);
        return null;
    }

    @Override
    public NotesModel setNoteToTrash(int notesId) {
        notesRespository.setNoteToTrash(notesId);
        return null;
    }

    @Override
    public NotesModel setNoteToUnTrash(int notesId) {
        notesRespository.setNoteToUnTrash(notesId);
        return null;
    }

    @Override
    public void deleteNotesById(int notesId) {
        imageRepository.deleteByNotesId(notesId);
        reminderRepository.deleteByNotesId(notesId);
        notesRespository.deleteById(notesId);
    }

    @Override
    public List<NotesModel> getAllNotesByUserId(String token) {
        int userId = jwtToken.decodeToken(token);
        return notesRespository.findAllNotesByUserId(userId);
    }

    @Override
    public List<NotesModel> getAllArchiveNotesByUserId(String token) {
        int userId = jwtToken.decodeToken(token);
        return notesRespository.findAllArchiveNotesByUserId(userId);
    }

    @Override
    public List<NotesModel> getAllTrashNotesByUserId(String token) {
        int userId = jwtToken.decodeToken(token);
        return notesRespository.findAllTrashNotesByUserId(userId);
    }

    @Override
    public NotesModel addNotesToLabel(int notesId, List<String> labelNameList) {
        NotesModel notesModel = notesRespository.findById(notesId).orElseThrow(() -> new NotesException("Notes Data found"));
        List<LabelModel> labelModelList = new ArrayList<>();
        for (String labelName : labelNameList) {
            LabelModel labelModel = labelRepository.findByLabelName(labelName);
            if (labelModel==null){
                throw new NotesException("Label Data not found");
            }
            if (labelModelList.contains(labelModel)){
                continue;
            }
            labelModelList.add(labelModel);
        }
        System.out.println(labelModelList);
        NotesModel notesModel1 = new NotesModel(notesModel, labelModelList);
        return notesRespository.save(notesModel1);
    }

    @Override
    public List<String> getLabelNamesByNoteId(int notesId) {
        NotesModel notesModel = notesRespository.findById(notesId).orElseThrow(() -> new NotesException("Notes Data Not found"));
        List<Integer> labelIdList = notesRespository.findAllLabels(notesId);
        List<String> labelNameList = new ArrayList<>();
        List<LabelModel> labelModelList = labelRepository.findAllById(labelIdList);
        for (LabelModel labelModel: labelModelList) {
            labelNameList.add(labelModel.getLabelName());
        }
        return labelNameList;
    }

    @Override
    public NotesModel removeNotesfromLabel(int notesId, String labelName) {
        NotesModel notesModel = notesRespository.findById(notesId).orElseThrow(() -> new NotesException("Notes Data Not found"));
        LabelModel labelModel = labelRepository.findByLabelName(labelName);
        notesModel.getLabelModelList().remove(labelModel);
        NotesModel notesModel1 = new NotesModel(notesModel, notesModel.getLabelModelList());
        return notesRespository.save(notesModel1);
    }

    @Override
    public NotesModel updateNote(int notesId, String token, NotesDTO notesDTO) {
        int userId = jwtToken.decodeToken(token);
        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new NotesException("User Data Not found"));
        NotesModel notesModel = notesRespository.findById(notesId).orElseThrow(() -> new NotesException("Notes Data Not found"));
        if (notesModel != null && userModel != null) {
            notesModel.setTitle(notesDTO.title);
            notesModel.setNote(notesDTO.note);
            return notesRespository.save(notesModel);
        }else {
            return null;
        }
    }

}
