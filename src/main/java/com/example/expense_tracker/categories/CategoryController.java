package com.example.expense_tracker.categories;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository repo;

    public CategoryController(CategoryRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<CategoryModel> getCategories() {
        return repo.findAll();
    }

    @GetMapping("/{category}")
    public CategoryModel getCategory(@PathVariable String category) {
        return repo.findById(category).orElse(null);
    }

    @PostMapping
    public String createCategory(@RequestBody CategoryModel category) {
        repo.save(category);
        return "Created";
    }

    @PutMapping("/{category}")
    public String updateCategory(
            @PathVariable String category,
            @RequestBody CategoryModel categoryBody) {
        repo.save(categoryBody);
        return "Updated";
    }

    @DeleteMapping("/{category}")
    public String deleteCategory(@PathVariable String category) {
        repo.deleteById(category);
        return "Deleted";
    }
}
