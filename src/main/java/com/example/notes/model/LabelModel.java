package com.example.notes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "label_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelModel {

    @Id
    @GeneratedValue
    private int labelId;

    private String labelName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;

    @JsonIgnore
    @ManyToMany
    private List<NotesModel> notesModelList;

    public LabelModel(String labelName) {
        this.labelName = labelName;
    }

    public LabelModel(String labelName, UserModel userModel) {
        this.userModel = userModel;
        this.labelName = labelName;
    }
}
