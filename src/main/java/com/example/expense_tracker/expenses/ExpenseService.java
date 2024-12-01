package com.example.expense_tracker.expenses;

import com.example.expense_tracker.categories.CategoryService;
import com.example.expense_tracker.utility.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;

    public ExpenseService(ExpenseRepository expenseRepository, CategoryService categoryService) {
        this.expenseRepository = expenseRepository;
        this.categoryService = categoryService;
    }

    public APIResponse createExpense(ExpenseModel expense) {
        if (!categoryService.isCategoryExists(expense.getCategory()))
            throw new RuntimeException("Category " + expense.getCategory() + " does not exists.");

        ExpenseModel newExpense = expenseRepository.save(expense);
        return new APIResponse(newExpense.getId().toString(), "Created");
    }

    public APIResponse updateExpense(Integer expenseId, ExpenseModel expense) {
        ExpenseModel existingExpense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + expenseId));

        if (categoryService.isCategoryExists(expense.getCategory()))
            throw new RuntimeException("Category " + expense.getCategory() + " does not exists.");

        existingExpense.setTitle(expense.getTitle());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setCategory(expense.getCategory());
        existingExpense.setTimeOfTransaction(expense.getTimeOfTransaction());

        expenseRepository.save(existingExpense);
        return new APIResponse(expenseId.toString(), "Updated");
    }

    public APIResponse deleteExpense(Integer expenseId) {
        expenseRepository.deleteById(expenseId);
        return new APIResponse(expenseId.toString(), "Deleted");
    }

    public ExpenseModel getExpense(Integer expenseId) {
        return expenseRepository.findById(expenseId).orElse(null);
    }

    public List<ExpenseModel> getAllExpenses(String category) {
        if (category != null) {
            return expenseRepository.findAllByCategory(category);
        }
        return expenseRepository.findAll();
    }
}
