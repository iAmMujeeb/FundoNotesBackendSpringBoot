package com.example.notes.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotesDTO {

    public String title;

    public String note;

    public Boolean isArchive;

    public Boolean isTrash;

}
