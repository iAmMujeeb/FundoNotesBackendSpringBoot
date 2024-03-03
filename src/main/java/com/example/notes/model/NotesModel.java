package com.example.notes.model;

import com.example.notes.dto.NotesDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notes_data")
@ToString
public class NotesModel {

    @Id
    @GeneratedValue
    @Column(name = "notes_id")
    private int notesId;

    private String title;

    private String note;

    private Boolean isArchive;

    private Boolean isTrash;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;

    @ManyToMany
    @JoinTable(name = "notes_label",
    joinColumns = @JoinColumn(name = "notes_id"),
    inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private List<LabelModel> labelModelList;

    public NotesModel(NotesDTO notesDTO, UserModel userModel) {
        this.title = notesDTO.title;
        this.note = notesDTO.note;
        this.isArchive = notesDTO.isArchive;
        this.isTrash = notesDTO.isTrash;
        this.userModel = userModel;
    }

    public NotesModel(NotesDTO notesDTO) {
        this.title = notesDTO.title;
        this.note = notesDTO.note;
        this.isArchive = notesDTO.isArchive;
        this.isTrash = notesDTO.isTrash;
    }

    public NotesModel(NotesModel notesModel, List<LabelModel> labelModelList) {
        this.notesId = notesModel.notesId;
        this.title = notesModel.title;
        this.note = notesModel.note;
        this.isArchive = notesModel.isArchive;
        this.isTrash = notesModel.isTrash;
        this.labelModelList = labelModelList;
        this.userModel = notesModel.userModel;
    }

}
