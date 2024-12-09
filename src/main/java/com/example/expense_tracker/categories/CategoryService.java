package com.example.expense_tracker.categories;

import com.example.expense_tracker.exception.IllegalOperationException;
import com.example.expense_tracker.exception.ResourceNotFoundException;
import com.example.expense_tracker.utility.SuccessResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
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

    public SuccessResponse createCategory(CategoryModel category) {
        if (StringUtils.isBlank(category.getCategory()))
            throw new IllegalOperationException("Please provide valid category name.");
        String categoryId = category.getCategory().replace(" ", "").toLowerCase();
        if (categoryRepository.findById(categoryId).isPresent())
            throw new IllegalOperationException("Category already present with id: " + categoryId);

        CategoryModel newCategory = new CategoryModel();
        newCategory.setId(categoryId);
        newCategory.setCategory(category.getCategory());
        if (category.getDescription() != null) newCategory.setDescription(category.getDescription());
        categoryRepository.save(newCategory);
        return new SuccessResponse(newCategory.getId(), "Created");
    }

    public SuccessResponse updateCategoryDescription(String categoryId, CategoryModel category) {
        CategoryModel existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        if (isDefaultCategory(categoryId))
            throw new IllegalOperationException("Default categories can not be edited");

        if (category.getDescription() != null) existingCategory.setDescription(category.getDescription());
        categoryRepository.save(existingCategory);
        return new SuccessResponse(categoryId, "Updated");
    }

    public SuccessResponse deleteCategory(String categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        if (isDefaultCategory(categoryId))
            throw new IllegalOperationException("Default categories can not be deleted");

        categoryRepository.deleteById(categoryId);
        return new SuccessResponse(categoryId, "Deleted");
    }

    public CategoryModel getCategory(String categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public List<CategoryModel> getAllCategories() {
        return categoryRepository.findAll();
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
            if (categoryId.equals(category.getId())) {
                isDefaultCategory = true;
                break;
            }
        }
        return isDefaultCategory;
    }

    public boolean isCategoryExists(String categoryId) {
        boolean isCategoryExists = false;
        for (CategoryModel category : categoryRepository.findAll()) {
            if (categoryId.equals(category.getId())) {
                isCategoryExists = true;
                break;
            }
        }
        return isCategoryExists;
    }
}
