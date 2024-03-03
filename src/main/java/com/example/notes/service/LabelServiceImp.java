package com.example.notes.service;

import com.example.notes.exception.NotesException;
import com.example.notes.model.LabelModel;
import com.example.notes.model.NotesModel;
import com.example.notes.model.UserModel;
import com.example.notes.repository.LabelRepository;
import com.example.notes.repository.NotesRespository;
import com.example.notes.repository.UserRepository;
import com.example.notes.token.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabelServiceImp implements LabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private NotesRespository notesRespository;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserRepository userRepository;

    @Override
    public LabelModel createLabel(String labelName, String token) {
        Boolean isPresent = false;
        int userId = jwtToken.decodeToken(token);
        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new NotesException("User not found"));
        LabelModel labelModel = labelRepository.findByLabelName(labelName);
        if (labelModel == null) {
            LabelModel labelModel1 = new LabelModel(labelName, userModel);
            return labelRepository.save(labelModel1);
        }else {
            throw new NotesException("Label Already Exist");
        }
    }

    @Override
    public List<LabelModel> getAllLabelNotes() {
        List<LabelModel> labelModellist =  labelRepository.findAll();
        List<List<NotesModel>> listOfNotesModelList = labelModellist.stream().map(LabelModel::getNotesModelList).toList();
        return labelModellist;
    }

    @Override
    public List<LabelModel> getAllLabelsByUserId(String token) {
        int userId = jwtToken.decodeToken(token);
        return labelRepository.findAllLabelsByUserId(userId);
    }

    @Override
    public void deleteLabelById(int labelId) {
        labelRepository.findById(labelId).orElseThrow(() -> new NotesException("Label Data not found"));
        labelRepository.deleteById(labelId);
    }

    @Override
    public LabelModel editLabelById(int labelId, String labelName) {
        LabelModel labelModel = labelRepository.findById(labelId).orElseThrow(() -> new NotesException("Label Data not found"));
        labelModel.setLabelName(labelName);
        return labelRepository.save(labelModel);
    }

    @Override
    public List<NotesModel> getAllLabelNotesByLabelId(int labelId, String token) {
        int userId = jwtToken.decodeToken(token);
        List<NotesModel> notesModelList = new ArrayList<>();
        LabelModel labelModel = labelRepository.findLabelByUserId(userId, labelId);
        if (labelModel != null) {
            List<Integer> notesIdList = labelRepository.findAllNotesIdByLabelId(labelId);
            System.out.println(notesIdList);
            for (int i = 0; i < notesIdList.size(); i++) {
                notesRespository.findById(notesIdList.get(i)).ifPresent(notesModelList::add);
            }
            System.out.println(notesModelList);
            return notesModelList;
        }else {
            throw new NotesException("Label Data not found");
        }
    }

    @Override
    public String getCurrentLabel(int labelId, String token) {
        LabelModel labelModel = labelRepository.findById(labelId).orElseThrow(() -> new NotesException("Label Data Not Found"));
        if (labelModel != null) {
            return labelModel.getLabelName();
        } else {
            return null;
        }
    }

}
