package com.oga.productsmanagment.service;

import com.oga.productsmanagment.dtos.CategoryDTO;
import com.oga.productsmanagment.dtos.ProductDTO;
import com.oga.productsmanagment.entity.Category;
import com.oga.productsmanagment.entity.Product;
import com.oga.productsmanagment.mapper.CategoryMapper;
import com.oga.productsmanagment.mapper.ProductMapper;
import com.oga.productsmanagment.repository.CategoryRepository;
import com.oga.productsmanagment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class CategoryService {

    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    public List<CategoryDTO> getAll (){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryMapper::entityToDto).toList();
    }


    public CategoryDTO findById (Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("entity not found"));
        return CategoryMapper.entityToDto(category);
    }


    public CategoryDTO create (CategoryDTO categoryDTO){
        Category category = CategoryMapper.dtoToEntity(categoryDTO);
        LocalDate date = LocalDate.now();
        category.setDateDeCreation(date);
        category.setDateDeModification(date);
        return CategoryMapper.entityToDto(categoryRepository.save(category));
    }


    public CategoryDTO update (CategoryDTO categoryDTO){
       var  category = CategoryMapper.dtoToEntity(categoryDTO);
       if (category== null) {



           throw new IllegalArgumentException("categorycannot be null");
       }
        LocalDate date = LocalDate.now();
        category.setDateDeModification(date);

        return CategoryMapper.entityToDto(categoryRepository.saveAndFlush( category));
    }

    public void delete(long id){
        Category category = categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("entity not found"));
        categoryRepository.delete(category);

    }
    public List<ProductDTO> getAllListProductCategory(Long id){
        List<Product> products  = productRepository.findAllByCategoryId(id);

        return products.stream().map(ProductMapper::entityToDto).toList();


}}
