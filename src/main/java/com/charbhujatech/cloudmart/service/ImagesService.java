package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.dto.ImageRequestDTO;
import com.charbhujatech.cloudmart.dto.ImageResponseDTO;

import java.util.List;

public interface ImagesService {

    public List<ImageResponseDTO> getImages(Long productId);

    public ImageResponseDTO addImage(ImageRequestDTO image);

    Boolean deleteImage(Long imageId);
}
