package com.example.notes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageModel {

    @Id
    @GeneratedValue
    private int imageId;

    private String name;

    private String type;

    @Lob
    @Column(name = "image", length = 10000)
    private byte[] imageData;

    @OneToOne
    @JoinColumn(name = "notes_id")
    private NotesModel notesModel;

}
