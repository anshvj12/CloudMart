package com.charbhujatech.cloudmart.mapper;

import com.charbhujatech.cloudmart.Model.Order;
import com.charbhujatech.cloudmart.Model.Product;
import com.charbhujatech.cloudmart.Model.Review;
import com.charbhujatech.cloudmart.Model.User;
import com.charbhujatech.cloudmart.dto.ReviewRequestDTO;
import com.charbhujatech.cloudmart.dto.ReviewResponseDTO;

import java.util.Date;
import java.util.List;

public class ReviewMapper {

    public static void mapToReviewResponseDTO(Review saveReview, ReviewResponseDTO reviewResponseDTO) {
        if (saveReview.getReviewText() != null && !saveReview.getReviewText().isEmpty())
            reviewResponseDTO.setReviewText(saveReview.getReviewText());
        if (saveReview.getRating() > 0)
            reviewResponseDTO.setRating(saveReview.getRating());
        reviewResponseDTO.setReviewId(saveReview.getReviewId());
    }

    public static void mapToReview(ReviewRequestDTO review, Review reviewToAdd, Product getProduct, User getUser, Order getOrder) {
        reviewToAdd.setProduct(getProduct);
        reviewToAdd.setUser(getUser);

        if (review.getRating() > 0)
            reviewToAdd.setRating(review.getRating());

        if (review.getReviewText() != null && !review.getReviewText().isEmpty())
            reviewToAdd.setReviewText(review.getReviewText());

        reviewToAdd.setReviewDate(new Date());
        reviewToAdd.setOrder(getOrder);
    }

    public static void mapToReviewResponseDTOS(List<Review> byUser, List<ReviewResponseDTO> reviewResponseDTOS) {
        for( Review review : byUser)
        {
            ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
            ReviewMapper.mapToReviewResponseDTO(review,reviewResponseDTO);
            reviewResponseDTOS.add(reviewResponseDTO);
        }
    }
}
