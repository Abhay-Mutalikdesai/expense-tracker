package com.example.expense_tracker.expenses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class ExpenseModel {
    @Id
    @JsonIgnore
    @GeneratedValue
    private Integer id;
    private String title;
    private Float amount;
    private String description;
    private String category;
    private LocalDateTime timeOfTransaction;
}
