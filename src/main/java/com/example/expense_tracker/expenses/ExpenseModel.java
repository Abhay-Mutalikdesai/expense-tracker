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
    @NotBlank
    private String title;
    @Positive @NotBlank
    private Double amount;
    private String description;
    private String category;
    @JsonFormat(pattern = "dd-MM-yyyy' 'HH:mm:ss")
    @NotBlank @PastOrPresent
    private LocalDateTime timeOfTransaction = LocalDateTime.now();
}
