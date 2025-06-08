package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.Images;
import com.charbhujatech.cloudmart.Model.Product;
import com.charbhujatech.cloudmart.config.AwsS3CRUDOperation;
import com.charbhujatech.cloudmart.dao.ImageRepository;
import com.charbhujatech.cloudmart.dao.ProductRepository;
import com.charbhujatech.cloudmart.dto.ImageResponseDTO;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.exception.BadRequestException;
import com.charbhujatech.cloudmart.exception.ResourceNotFoundException;
import com.charbhujatech.cloudmart.util.ConstantsString;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class ImagesServiceImp implements ImagesService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final AwsS3CRUDOperation awsS3CRUDOperation;


    @Override
    public List<ImageResponseDTO> getImages(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.PRODUCT_NOT_FOUND));
        List<ImageResponseDTO> imageResponseDTOS = new ArrayList<>();
        List<Images> byProduct = imageRepository.findByProduct(product);
        for(Images image : byProduct)
        {
            ImageResponseDTO imageResponseDTO = new ImageResponseDTO();
            if(image.getImageUrl() != null) {
                URL url = awsS3CRUDOperation.generatePresignedUrl(image.getImageUrl());
                imageResponseDTO.setImageUrl(url);
            }
            imageResponseDTO.setImageId(image.getImageId());
            imageResponseDTOS.add(imageResponseDTO);
        }
        return imageResponseDTOS;
    }

    @Override
    public Boolean deleteImage(Long imageId) {
        final Images images = imageRepository.findById(imageId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.IMAGE_NOT_FOUND));
        if(images.getImageUrl() != null)
            awsS3CRUDOperation.deleteFile(images.getImageUrl());
        imageRepository.deleteImageBYId(imageId);
        return !imageRepository.existsById(imageId);
    }

    /**
     * @param productId
     * @param productImage
     * @return
     */
    @Override
    public ResponseDTO addProductImage(Long productId, MultipartFile productImage) throws IOException {

        Product product = productRepository.findById(productId).orElseThrow( () ->
                new ResourceNotFoundException(ConstantsString.PRODUCT_NOT_FOUND));

        if(productImage.isEmpty())
            throw new ResourceNotFoundException(ConstantsString.IMAGE_NOT_FOUND);

        String key = productId+"/"+productImage.getOriginalFilename();

        boolean imageExists = awsS3CRUDOperation.isImageExists(key);

        if(imageExists)
            throw new BadRequestException(ConstantsString.IMAGE_ALREADY_EXIST);

        awsS3CRUDOperation.uploadFileOnS3(productImage, key);

        Images images = new Images();
        images.setImageUrl(key);
        images.setProduct(product);
        final Images save = imageRepository.save(images);

        return new ResponseDTO(HttpStatus.OK.toString(),ConstantsString.IMAGE_UPLOADED);
    }



}
