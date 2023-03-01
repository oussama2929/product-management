package com.oga.productsmanagment.mapper;

import com.oga.productsmanagment.dtos.CategoryDTO;
import com.oga.productsmanagment.entity.Category;


public class CategoryMapper {
    private CategoryMapper() {

    }

    public static CategoryDTO entityToDto (Category category){
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId(category.getId());
        categoryDTO.setQte(category.getQte());
        categoryDTO.setName(category.getName());
        categoryDTO.setDateDeCreation(category.getDateDeCreation());
        categoryDTO.setDateDeModification(category.getDateDeModification());
        if (category.getProducts() != null) {
            categoryDTO.setProducts(category.getProducts().stream().map(ProductMapper::entityToDto).toList());
        }
        return categoryDTO;
    }


    public static Category dtoToEntity (CategoryDTO categoryDTO){
        Category category = new Category();

        category.setId(categoryDTO.getId());
        category.setQte(categoryDTO.getQte());
        category.setName(categoryDTO.getName());
        category.setDateDeCreation(categoryDTO.getDateDeCreation());
        category.setDateDeModification(categoryDTO.getDateDeModification());
        if (categoryDTO.getProducts() != null) {
            category.setProducts(categoryDTO.getProducts().stream().map(ProductMapper::dtoToEntity).toList());
        }
        return category;
    }
}
