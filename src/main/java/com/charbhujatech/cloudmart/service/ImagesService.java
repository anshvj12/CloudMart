package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.dto.ImageRequestDTO;
import com.charbhujatech.cloudmart.dto.ImageResponseDTO;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ImagesService {

    public List<ImageResponseDTO> getImages(Long productId);

    Boolean deleteImage(Long imageId);

    ResponseDTO addProductImage(Long productId, MultipartFile productImage) throws IOException;
}
