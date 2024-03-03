package com.example.notes.repository;

import com.example.notes.model.LabelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<LabelModel, Integer> {

    @Query(value = "select * from label_data where user_id = :userId", nativeQuery = true)
    List<LabelModel> findAllLabelsByUserId(int userId);

    @Query(value = "SELECT * FROM notes_label where label_id = :labelId", nativeQuery = true)
    List<Integer> findAllNotesIdByLabelId(int labelId);

    @Query(value = "select * from label_data where label_name = :labelName", nativeQuery = true)
    LabelModel findByLabelName(String labelName);

    @Query(value = "select * from label_data where user_id = :userId and label_id = :labelId", nativeQuery = true)
    LabelModel findLabelByUserId(int userId, int labelId);

}
