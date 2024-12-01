package com.example.expense_tracker.expenses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class ExpenseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @NotEmpty
    private String title;
    @Positive
    private Float amount;
    private String description;
    private String category;
    @JsonFormat(pattern = "dd-MM-yyyy' 'HH:mm:ss")
    private LocalDateTime timeOfTransaction = LocalDateTime.now();
}
