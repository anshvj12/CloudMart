package com.charbhujatech.cloudmart.controller;

import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.dto.ReviewRequestDTO;
import com.charbhujatech.cloudmart.dto.ReviewResponseDTO;
import com.charbhujatech.cloudmart.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("users/{userId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getUserReviews(@PathVariable Long userId) {

        List<ReviewResponseDTO> reviewList = reviewService.getUserReviews(userId);

        return new ResponseEntity<>(reviewList,HttpStatus.OK);
    }

    @GetMapping("products/{productId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getProductReviews(@PathVariable Long productId) {

        List<ReviewResponseDTO> reviewList = reviewService.getProductReviews(productId);

        return new ResponseEntity<>(reviewList,HttpStatus.OK);
    }

    @PostMapping("users/{userId}/orders/{orderId}/products/{productId}/reviews")
    public ResponseEntity<ResponseDTO> addReview(@PathVariable Long userId, @PathVariable Long orderId,
                                                 @PathVariable Long productId, @RequestBody ReviewRequestDTO reviewRequestDTO) {
        final ResponseDTO reviewResponseDTO = reviewService.addReview(userId,orderId,productId,reviewRequestDTO);
        if (reviewResponseDTO != null) {
            return new ResponseEntity<>(reviewResponseDTO, HttpStatus.CREATED);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
