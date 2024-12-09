package com.example.expense_tracker.expenses;

import com.example.expense_tracker.categories.CategoryService;
import com.example.expense_tracker.exception.IllegalOperationException;
import com.example.expense_tracker.exception.ResourceNotFoundException;
import com.example.expense_tracker.utility.SuccessResponse;
import com.example.expense_tracker.utility.Constants;
import io.micrometer.common.util.StringUtils;
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

    public SuccessResponse createExpense(ExpenseModel expense) {
        if (StringUtils.isBlank(expense.getCategory())) expense.setCategory(Constants.CATEGORY_OTHER);
        if (!categoryService.isCategoryExists(expense.getCategory()))
            throw new IllegalOperationException("Category " + expense.getCategory() + " does not exists.");

        ExpenseModel newExpense = expenseRepository.save(expense);
        return new SuccessResponse(newExpense.getId().toString(), "Created");
    }

    public SuccessResponse updateExpense(Integer expenseId, ExpenseModel expense) {
        ExpenseModel existingExpense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + expenseId));

        if (categoryService.isCategoryExists(expense.getCategory()))
            throw new IllegalOperationException("Category " + expense.getCategory() + " does not exists.");

        existingExpense.setTitle(expense.getTitle());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setCategory(expense.getCategory());
        existingExpense.setTimeOfTransaction(expense.getTimeOfTransaction());

        expenseRepository.save(existingExpense);
        return new SuccessResponse(expenseId.toString(), "Updated");
    }

    public SuccessResponse deleteExpense(Integer expenseId) {
        expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + expenseId));
        expenseRepository.deleteById(expenseId);
        return new SuccessResponse(expenseId.toString(), "Deleted");
    }

    public ExpenseModel getExpense(Integer expenseId) {
        expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + expenseId));
        return expenseRepository.findById(expenseId).orElse(null);
    }

    public List<ExpenseModel> getAllExpenses(String category) {
        if (StringUtils.isNotBlank(category)) return expenseRepository.findAllByCategory(category.toLowerCase());
        return expenseRepository.findAll();
    }

    public ExpenseSummaryModel getExpenseSummary(String category) {
        List<ExpenseModel> expenseList;
        if (StringUtils.isNotBlank(category)) {
            if (!categoryService.isCategoryExists(category.toLowerCase()))
                throw new IllegalOperationException("Category " + category + " does not exists.");
            expenseList = expenseRepository.findAllByCategory(category.toLowerCase());
        } else expenseList = expenseRepository.findAll();

        ExpenseSummaryModel expenseSummary = new ExpenseSummaryModel();
        if (expenseList.isEmpty()) {
            expenseSummary.setTotalExpense(0.0);
            expenseSummary.setExpenseList(expenseList);
        } else {
            expenseSummary.setTotalExpense(expenseList.stream().mapToDouble(ExpenseModel::getAmount).sum());
            expenseSummary.setExpenseList(expenseList);
        }
        return expenseSummary;
    }
}
