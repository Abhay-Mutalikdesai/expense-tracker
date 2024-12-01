package com.example.expense_tracker.categories;

import com.example.expense_tracker.utility.APIResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    public void initDefaultCategories() {
        try {
            for (CategoryModel category : getDefaultCategoriesList()) {
                if (!categoryRepository.existsById(category.getId())) categoryRepository.save(category);
            }
            log.debug("\nDefault categories initialized successfully.");
        } catch (Exception e) {
            log.error("Unable to initialize default categories.\n Error: {}", e.getMessage(), e);
        }
    }

    public List<CategoryModel> getAllCategories() {
        return categoryRepository.findAll();
    }

    public CategoryModel getCategory(String categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public APIResponse createCategory(CategoryModel category) {
        String categoryId = category.getCategory().replace(" ", "").toLowerCase();
        if (categoryRepository.findById(categoryId).isPresent())
            throw new RuntimeException("Category already present with id: " + categoryId);

        CategoryModel newCategory = new CategoryModel();
        newCategory.setId(categoryId);
        newCategory.setCategory(category.getCategory());
        if (category.getDescription() != null) newCategory.setDescription(category.getDescription());
        categoryRepository.save(newCategory);
        return new APIResponse(newCategory.getId(), "Created");
    }

    public APIResponse updateCategoryDescription(String categoryId, CategoryModel category) {
        CategoryModel existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        if (isDefaultCategory(categoryId))
            throw new RuntimeException("Default categories can not be edited");

        if (category.getDescription() != null) existingCategory.setDescription(category.getDescription());
        categoryRepository.save(existingCategory);
        return new APIResponse(categoryId, "Updated");
    }

    public APIResponse deleteCategory(String categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        if (isDefaultCategory(categoryId))
            throw new RuntimeException("Default categories can not be deleted");

        categoryRepository.deleteById(categoryId);
        return new APIResponse(categoryId, "Deleted");
    }

    public List<CategoryModel> getDefaultCategoriesList() {
        List<CategoryModel> defaultCategoriesList = Collections.emptyList();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream defaultCategories = new ClassPathResource("defaultCategories.json").getInputStream();
            defaultCategoriesList = objectMapper.readValue(defaultCategories, new TypeReference<List<CategoryModel>>() {
            });
            return defaultCategoriesList;
        } catch (Exception e) {
            log.error("Unable to get default category list.\n Error: {}", e.getMessage(), e);
        }
        return defaultCategoriesList;
    }

    public boolean isDefaultCategory(String categoryId) {
        boolean isDefaultCategory = false;
        for (CategoryModel category : getDefaultCategoriesList()) {
            if (categoryId.equalsIgnoreCase(category.getId())) {
                isDefaultCategory = true;
                break;
            }
        }
        return isDefaultCategory;
    }

    public boolean isCategoryExists(String categoryId) {
        boolean isCategoryExists = false;
        for (CategoryModel category : categoryRepository.findAll()) {
            if (categoryId.equalsIgnoreCase(category.getId())) {
                isCategoryExists = true;
                break;
            }
        }
        return isCategoryExists;
    }
}
