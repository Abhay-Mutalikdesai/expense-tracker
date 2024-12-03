package com.example.expense_tracker.categories;

import com.example.expense_tracker.utility.SuccessResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryModel> getCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public CategoryModel getCategory(@PathVariable String categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createCategory(@Valid @RequestBody CategoryModel category) {
        SuccessResponse response = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse> updateCategory(
            @PathVariable String categoryId,
            @RequestBody CategoryModel category) {
        SuccessResponse response = categoryService.updateCategoryDescription(categoryId, category);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse> deleteCategory(@PathVariable String categoryId) {
        SuccessResponse response = categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
