package com.oga.productsmanagment.mapper;

import com.oga.productsmanagment.dtos.ProductDTO;
import com.oga.productsmanagment.entity.Product;

public class ProductMapper {
    private ProductMapper(){

    }

    public static ProductDTO entityToDto (Product product){
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setCategoryId(product.getCategoryId());
        productDTO.setQte(product.getQte());
        productDTO.setName(product.getName());
        productDTO.setDisponibilite(product.isDisponibilite());
        productDTO.setDateDeCreation(product.getDateDeCreation());
        productDTO.setDateDeModification(product.getDateDeModification());


        return productDTO;
    }


    public static Product dtoToEntity (ProductDTO productDTO){
        Product product = new Product();

        product.setId(productDTO.getId());
        product.setQte(productDTO.getQte());
        product.setName(productDTO.getName());
        product.setDisponibilite(productDTO.isDisponibilite());
        product.setDateDeCreation(productDTO.getDateDeCreation());
        product.setDateDeModification(productDTO.getDateDeModification());
        if (productDTO.getCategory()!= null){
        product.setCategory(CategoryMapper.dtoToEntity(productDTO.getCategory()));}

        return product;
    }
}
