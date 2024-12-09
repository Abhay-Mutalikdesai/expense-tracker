package com.example.expense_tracker.expenses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseSummaryModel {
    private Double totalExpense;
    private List<ExpenseModel> expenseList;
}
