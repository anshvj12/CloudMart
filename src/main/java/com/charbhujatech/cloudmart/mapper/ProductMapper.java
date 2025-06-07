package com.charbhujatech.cloudmart.mapper;

import com.charbhujatech.cloudmart.Model.Product;
import com.charbhujatech.cloudmart.dto.ProductRequestDTO;
import com.charbhujatech.cloudmart.dto.ProductResponseDTO;
import com.charbhujatech.cloudmart.dto.ProductsResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Iterator;

public class ProductMapper {

    public static void mapToProductResponseDTO(Product saveProduct, ProductResponseDTO respProduct) {
        if (saveProduct.getProductId() != null)
            respProduct.setProductId(saveProduct.getProductId());
        if(saveProduct.getProductName() != null && !saveProduct.getProductName().isEmpty())
            respProduct.setProductName(saveProduct.getProductName());
        if (saveProduct.getProductDate() != null)
            respProduct.setProductDate(saveProduct.getProductDate());
        if (saveProduct.getPrice() != null)
            respProduct.setPrice(saveProduct.getPrice());
        if (saveProduct.getAvailableQuantity() != null)
            respProduct.setAvailableQuantity(saveProduct.getAvailableQuantity());
        if (saveProduct.getProductDescription() != null)
            respProduct.setProductDescription(saveProduct.getProductDescription());
    }

    public static void mapToProduct(ProductRequestDTO productRequestDTO, Product saveProduct) {
        if (productRequestDTO.getProductName() != null && !productRequestDTO.getProductName().isEmpty())
            saveProduct.setProductName(productRequestDTO.getProductName());

        if (productRequestDTO.getProductDate() != null)
            saveProduct.setProductDate(productRequestDTO.getProductDate());

        if (productRequestDTO.getPrice() != null)
            saveProduct.setPrice(productRequestDTO.getPrice());

        if (productRequestDTO.getAvailableQuantity() != null)
            saveProduct.setAvailableQuantity(productRequestDTO.getAvailableQuantity());

        if(productRequestDTO.getProductDescription() != null)
            saveProduct.setProductDescription(productRequestDTO.getProductDescription());
    }

    public static void mapToProductsReponseDTO(Page<Product> allProduct, ProductsResponseDTO responseDTO) {

        responseDTO.setTotalElements(allProduct.getTotalElements());
        responseDTO.setTotalPages(allProduct.getTotalPages());
        responseDTO.setPageSize(allProduct.getSize());
        responseDTO.setPageNumber(allProduct.getNumber());

        final Iterator<Product> iterator = allProduct.stream().iterator();
        while(iterator.hasNext())
        {
            Product product = iterator.next();
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            mapToProductResponseDTO(product,productResponseDTO);
            responseDTO.getResponseDTOList().add(productResponseDTO);
        }
    }
}
