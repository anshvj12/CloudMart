package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.Images;
import com.charbhujatech.cloudmart.Model.Product;
import com.charbhujatech.cloudmart.dao.ProductRepository;
import com.charbhujatech.cloudmart.dto.ProductRequestDTO;
import com.charbhujatech.cloudmart.dto.ProductsResponseDTO;
import com.charbhujatech.cloudmart.exception.ResourceNotFoundException;
import com.charbhujatech.cloudmart.mapper.ProductMapper;
import com.charbhujatech.cloudmart.dto.ImageResponseDTO;
import com.charbhujatech.cloudmart.dto.ProductResponseDTO;
import com.charbhujatech.cloudmart.util.ConstantsConfig;
import com.charbhujatech.cloudmart.util.ConstantsString;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public ProductsResponseDTO getProducts(int page, String sortBy, boolean orderType) {
        int pageSize = ConstantsConfig.PAGE_SIDE;
        Sort sort = orderType ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, pageSize, sort);
        Page<Product> allProduct = productRepository.findAll(pageable);
        ProductsResponseDTO responseDTO = new ProductsResponseDTO();
        if( allProduct != null)
            ProductMapper.mapToProductsReponseDTO(allProduct,responseDTO);
        else
            throw new ResourceNotFoundException(ConstantsString.PRODUCT_NOT_FOUND);
        return responseDTO;
    }

    @Override
    public Optional<Product> getProductById(Long id)
    {
        return productRepository.findById(id);
    }

    @Override
    public Optional<ProductResponseDTO> getProductRespById(Long id) {
        ProductResponseDTO respProduct = new ProductResponseDTO();
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product getProduct = optionalProduct.get();
            ProductMapper.mapToProductResponseDTO(getProduct, respProduct);
            Set<Images> productImage = getProduct.getProductImage();
            if (productImage != null)
            {
                List<ImageResponseDTO> imageResponseDTOList = new ArrayList<>();
                for( Images image: productImage)
                {
                    ImageResponseDTO imageResponseDTO = new ImageResponseDTO();
                    imageResponseDTO.setProductId(getProduct.getProductId());
                    if (image.getImageId() != null)
                        imageResponseDTO.setImageId(image.getImageId());
                    if(image.getImageUrl() != null && !image.getImageUrl().isEmpty())
                    {
                        imageResponseDTO.setImageUrl(image.getImageUrl());
                    }
                    imageResponseDTOList.add(imageResponseDTO);
                }
                respProduct.setImagesList(imageResponseDTOList);
            }
        }
        else
        {
            throw new ResourceNotFoundException(ConstantsString.PRODUCT_NOT_FOUND);
        }
        return Optional.of(respProduct);
    }

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {

        Product saveProduct = new Product();
        ProductResponseDTO respProduct = new ProductResponseDTO();
        Product savedProduct = null;

        if (productRequestDTO != null) {
            ProductMapper.mapToProduct(productRequestDTO, saveProduct);
            savedProduct = productRepository.save(saveProduct);
        }

        if (savedProduct != null) {
            ProductMapper.mapToProductResponseDTO(savedProduct, respProduct);
        }
        return respProduct;
    }




}
