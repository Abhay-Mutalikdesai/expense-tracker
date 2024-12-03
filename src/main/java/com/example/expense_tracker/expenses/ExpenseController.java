package com.example.expense_tracker.expenses;

import com.example.expense_tracker.utility.SuccessResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping()
    public List<ExpenseModel> getExpenses(@RequestParam(required = false) String category) {
        return expenseService.getAllExpenses(category);
    }

    @GetMapping("/{expenseId}")
    public ExpenseModel getExpense(@PathVariable Integer expenseId) {
        return expenseService.getExpense(expenseId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SuccessResponse> createExpense(@Valid @RequestBody ExpenseModel expense) {
        SuccessResponse response = expenseService.createExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<SuccessResponse> updateExpense(
            @PathVariable Integer expenseId,
            @Valid @RequestBody ExpenseModel expense) {
        SuccessResponse response = expenseService.updateExpense(expenseId, expense);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<SuccessResponse> deleteExpense(@PathVariable Integer expenseId) {
        SuccessResponse response = expenseService.deleteExpense(expenseId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
