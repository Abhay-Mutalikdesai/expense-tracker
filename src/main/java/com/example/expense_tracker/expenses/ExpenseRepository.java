package com.example.expense_tracker.expenses;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseModel, Integer> {
    List<ExpenseModel> findAllByCategory(String category);
}
