package com.charbhujatech.cloudmart.controller;

import com.charbhujatech.cloudmart.dto.ImageRequestDTO;
import com.charbhujatech.cloudmart.dto.ImageResponseDTO;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.service.ImagesService;
import com.charbhujatech.cloudmart.util.ConstantsString;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ImageController {

    private final ImagesService imagesService;

    @PostMapping("products/{productId}/images")
    public ResponseEntity<ResponseDTO> addImageInS3(@PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile productImage)
            throws IOException
    {
        ResponseDTO imageResponseDTO = imagesService.addProductImage(productId,productImage);
        if (imageResponseDTO != null) {
            return new ResponseEntity<>(imageResponseDTO, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("products/{productId}/images")
    public ResponseEntity<List<ImageResponseDTO>> getImages(@PathVariable Long productId) {
        final List<ImageResponseDTO> images = imagesService.getImages(productId);
        return new ResponseEntity<>(images,HttpStatus.OK);
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<ResponseDTO> deleteImage(@PathVariable Long imageId)
    {
        return imagesService.deleteImage(imageId) ?
                new ResponseEntity<>(new ResponseDTO(HttpStatus.OK.toString(), ConstantsString.IMAGE_DELETED),HttpStatus.OK)
                : new ResponseEntity<>(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ConstantsString.IMAGE_NOT_DELETED),HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
