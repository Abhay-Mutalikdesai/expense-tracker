package com.example.expense_tracker.categories;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class CategoryModel {
    @Id
    private String id;
    private String category;
    private String description;
}
