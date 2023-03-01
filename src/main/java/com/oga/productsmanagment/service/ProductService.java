package com.oga.productsmanagment.service;

import com.oga.productsmanagment.dtos.ProductDTO;
import com.oga.productsmanagment.entity.Category;
import com.oga.productsmanagment.entity.Product;
import com.oga.productsmanagment.mapper.ProductMapper;
import com.oga.productsmanagment.repository.CategoryRepository;
import com.oga.productsmanagment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;



@Service
public class ProductService {
    ProductRepository productRepository;
    Category category;
   @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAll (){
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper::entityToDto).toList();
    }

    public ProductDTO findById (Long id){
        Product product = productRepository.findById(id).orElseThrow(RuntimeException::new);
        return ProductMapper.entityToDto(product);
    }


    public ProductDTO create (ProductDTO productDTO,Long id){
        category = categoryRepository.findById(id).orElse(null);
        Product product = ProductMapper.dtoToEntity(productDTO);
        LocalDate date = LocalDate.now();
        product.setDateDeCreation(date);
        product.setDateDeModification(date);
        product.setCategory(category);
        return ProductMapper.entityToDto(productRepository.save(product));
    }


    public ProductDTO update (ProductDTO productDTO){
        category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);
        Product product = ProductMapper.dtoToEntity(productDTO);
        LocalDate date = LocalDate.now();
        product.setDateDeModification(date);
        product.setCategory(category);
        product.setCategoryId(product.getCategoryId());
        return ProductMapper.entityToDto(productRepository.save(product));
    }


    public void delete(long id){
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("entity not found"));
        productRepository.delete(product);
    }


}
