package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.Images;
import com.charbhujatech.cloudmart.Model.Product;
import com.charbhujatech.cloudmart.dao.ImageRepository;
import com.charbhujatech.cloudmart.dao.ProductRepository;
import com.charbhujatech.cloudmart.dto.ImageRequestDTO;
import com.charbhujatech.cloudmart.dto.ImageResponseDTO;
import com.charbhujatech.cloudmart.exception.ResourceNotFoundException;
import com.charbhujatech.cloudmart.util.ConstantsString;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ImagesServiceImp implements ImagesService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;


    @Override
    public List<ImageResponseDTO> getImages(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.PRODUCT_NOT_FOUND));
        List<ImageResponseDTO> imageResponseDTOS = new ArrayList<>();
        List<Images> byProduct = imageRepository.findByProduct(product);
        for(Images image : byProduct)
        {
            ImageResponseDTO imageResponseDTO = new ImageResponseDTO();
            if(image.getImageUrl() != null)
            {
                imageResponseDTO.setImageUrl(image.getImageUrl());
            }
            imageResponseDTO.setImageId(image.getImageId());
            imageResponseDTO.setProductId(productId);
            imageResponseDTOS.add(imageResponseDTO);
        }
        return imageResponseDTOS;
    }

    @Override
    public ImageResponseDTO addImage(ImageRequestDTO imageRequestDTO) {
        ImageResponseDTO imageResponseDTO = new ImageResponseDTO();
        Images saveImages = new Images();
        if ( imageRequestDTO.getProductId() > 0)
        {
            Optional<Product> byId = productRepository.findById(imageRequestDTO.getProductId());
            if (byId.isPresent()) {
                saveImages.setProduct(byId.get());
            }
            else
            {
                throw new ResourceNotFoundException(ConstantsString.PRODUCT_NOT_FOUND);
            }
        }
        if(imageRequestDTO.getImageUrl() != null && !imageRequestDTO.getImageUrl().isEmpty())
        {
            saveImages.setImageUrl(imageRequestDTO.getImageUrl());
        }

        Images saved = imageRepository.save(saveImages);

        if (saved.getProduct() != null) {
            if (saved.getImageId() > 0)
            {
                imageResponseDTO.setImageId(saved.getImageId());
            }
            if (saved.getImageUrl() != null) {
                imageResponseDTO.setImageUrl(saved.getImageUrl());
            }
            if (saved.getProduct().getProductId() != null) {
                imageResponseDTO.setProductId(saved.getProduct().getProductId());
            }
        }

        return imageResponseDTO;
    }

    @Override
    public Boolean deleteImage(Long imageId) {
        imageRepository.findById(imageId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.IMAGE_NOT_FOUND));
        imageRepository.deleteImageBYId(imageId);
        return !imageRepository.existsById(imageId);
    }


}
