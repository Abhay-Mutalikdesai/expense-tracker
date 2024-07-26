package com.example.expense_tracker.expenses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseRepository repo;

    public ExpenseController(ExpenseRepository repo) {
        this.repo = repo;
    }

    @GetMapping()
    public List<ExpenseModel> getExpenses(@RequestParam(required = false) String category) {
        if (category != null) {
            return repo.findAllByCategory(category);
        }
        return repo.findAll();
    }

    @GetMapping("/{expenseId}")
    public ExpenseModel getExpense(@PathVariable Integer expenseId) {
        return repo.findById(expenseId).orElse(null);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseModel createExpense(@RequestBody ExpenseModel expense) {
        return repo.save(expense);
    }


    @PutMapping("/{expenseId}")
    public String updateExpense(
            @PathVariable Integer expenseId,
            @RequestBody ExpenseModel expense) {
        repo.save(expense);
        return "Updated";
    }

    @DeleteMapping("/{expenseId}")
    public String deleteExpense(@PathVariable Integer expenseId) {
        repo.deleteById(expenseId);
        return "deleted";
    }
}
