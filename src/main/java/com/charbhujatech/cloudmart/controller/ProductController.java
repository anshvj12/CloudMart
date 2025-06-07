package com.charbhujatech.cloudmart.controller;

import com.charbhujatech.cloudmart.dto.ProductRequestDTO;
import com.charbhujatech.cloudmart.dto.ProductResponseDTO;
import com.charbhujatech.cloudmart.dto.ProductsResponseDTO;
import com.charbhujatech.cloudmart.exception.ResourceNotFoundException;
import com.charbhujatech.cloudmart.service.ProductService;
import com.charbhujatech.cloudmart.util.ConstantsString;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ProductsResponseDTO getProducts(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "price") String sortBy,
                                     @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.getProducts(page,sortBy,ascending);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO product) {
        ProductResponseDTO respProduct = productService.addProduct(product);
        if (respProduct != null) {
            return new ResponseEntity<>(respProduct, HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(respProduct, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("products/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long productId) {
        Optional<ProductResponseDTO> product = productService.getProductRespById(productId);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(),HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException(ConstantsString.PRODUCT_NOT_FOUND);
        }
    }

}
