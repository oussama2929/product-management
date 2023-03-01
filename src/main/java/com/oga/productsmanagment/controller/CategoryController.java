package com.oga.productsmanagment.controller;

import com.oga.productsmanagment.dtos.CategoryDTO;
import com.oga.productsmanagment.dtos.ProductDTO;
import com.oga.productsmanagment.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDTO>> getAll(){
        List<CategoryDTO> categoryDTOS = categoryService.getAll();
        return ResponseEntity.ok().body(categoryDTOS);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO category = categoryService.create(categoryDTO);
        return ResponseEntity.ok().body(category);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id){
        CategoryDTO categoryDTO = categoryService.findById(id);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO categoryDTO1 = categoryService.update(categoryDTO);
        return ResponseEntity.ok().body(categoryDTO1);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteCategory(@PathVariable("id") Long id){
        categoryService.delete(id);
    }


    @GetMapping(path = "/getProdCat/{id}")
    public List<ProductDTO> getAllConsumers(@PathVariable("id") Long id){
        return categoryService.getAllListProductCategory(id);

    }

}
