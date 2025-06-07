package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.Product;
import com.charbhujatech.cloudmart.dto.ProductRequestDTO;
import com.charbhujatech.cloudmart.dto.ProductResponseDTO;
import com.charbhujatech.cloudmart.dto.ProductsResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProductService {

    public ProductsResponseDTO getProducts(int page, String sortBy, boolean orderType);

    public Optional<Product> getProductById(Long id);

    public Optional<ProductResponseDTO> getProductRespById(Long id);

    public ProductResponseDTO addProduct(ProductRequestDTO product);
}
