package com.oga.productsmanagment.controller;


import com.oga.productsmanagment.dtos.ProductDTO;
import com.oga.productsmanagment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (path = "api/v1/product")
@CrossOrigin(origins = "*")
public class ProductController {

    ProductService productService;
    @Autowired
    public ProductController (ProductService productService){
        this.productService = productService;
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDTO>> getAll(){
        List<ProductDTO> productDTOS = productService.getAll();
        return ResponseEntity.ok().body(productDTOS);
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO,@PathVariable Long id){
        ProductDTO product = productService.create(productDTO,id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id){
        ProductDTO productDTO = productService.findById(id);
        return ResponseEntity.ok().body(productDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO){
        ProductDTO productDTO1 = productService.update(productDTO);
        return ResponseEntity.ok().body(productDTO1);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
