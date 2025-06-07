package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.dto.ReviewRequestDTO;
import com.charbhujatech.cloudmart.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {

    ResponseDTO addReview(Long userId, Long orderId, Long productId, ReviewRequestDTO review);

    List<ReviewResponseDTO> getUserReviews(Long userId);

    List<ReviewResponseDTO> getProductReviews(Long productId);
}
