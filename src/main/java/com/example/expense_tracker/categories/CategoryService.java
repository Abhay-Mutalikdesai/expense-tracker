package com.example.expense_tracker.categories;

import com.example.expense_tracker.exception.IllegalOperationException;
import com.example.expense_tracker.exception.ResourceNotFoundException;
import com.example.expense_tracker.expenses.ExpenseRepository;
import com.example.expense_tracker.utility.Constants;
import com.example.expense_tracker.utility.MessageUtil;
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
    private final ExpenseRepository expenseRepository;
    private final MessageUtil messageUtil;

    public CategoryService(CategoryRepository categoryRepository, ExpenseRepository expenseRepository, MessageUtil messageUtil) {
        this.categoryRepository = categoryRepository;
        this.expenseRepository = expenseRepository;
        this.messageUtil = messageUtil;
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
            throw new IllegalOperationException(messageUtil.getMessage(Constants.INVALID_CATEGORY));
        String categoryId = category.getCategory().replace(" ", "").toLowerCase();
        if (categoryRepository.findById(categoryId).isPresent())
            throw new IllegalOperationException(messageUtil.getMessage(Constants.CATEGORY_ALREADY_PRESENT, categoryId));

        CategoryModel newCategory = new CategoryModel();
        newCategory.setId(categoryId);
        newCategory.setCategory(category.getCategory());
        if (category.getDescription() != null) newCategory.setDescription(category.getDescription());
        categoryRepository.save(newCategory);
        return new SuccessResponse(newCategory.getId(), messageUtil.getMessage(Constants.CATEGORY_CREATED));
    }

    public SuccessResponse updateCategoryDescription(String categoryId, CategoryModel category) {
        CategoryModel existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage(Constants.CATEGORY_ID_NOT_FOUND, categoryId)));

        if (isDefaultCategory(categoryId))
            throw new IllegalOperationException(messageUtil.getMessage(Constants.DEFAULT_CATEGORY_EDIT));

        if (category.getDescription() != null) existingCategory.setDescription(category.getDescription());
        categoryRepository.save(existingCategory);
        return new SuccessResponse(categoryId, messageUtil.getMessage(Constants.CATEGORY_UPDATED));
    }

    public SuccessResponse deleteCategory(String categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage(Constants.CATEGORY_ID_NOT_FOUND, categoryId)));
        if (isDefaultCategory(categoryId))
            throw new IllegalOperationException(messageUtil.getMessage(Constants.DEFAULT_CATEGORY_DELETE));
        else if (!expenseRepository.findAllByCategory(categoryId).isEmpty())
            throw new IllegalOperationException(messageUtil.getMessage(Constants.CATEGORY_IN_USE, categoryId));
        categoryRepository.deleteById(categoryId);
        return new SuccessResponse(categoryId, messageUtil.getMessage(Constants.CATEGORY_DELETED));
    }

    public CategoryModel getCategory(String categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage(Constants.CATEGORY_ID_NOT_FOUND, categoryId)));
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
