package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.*;
import com.online.shopping.Model.*;
import com.charbhujatech.cloudmart.dao.OrderRepository;
import com.charbhujatech.cloudmart.dao.ProductRepository;
import com.charbhujatech.cloudmart.dao.ReviewRepository;
import com.charbhujatech.cloudmart.dao.UserRepository;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.dto.ReviewRequestDTO;
import com.charbhujatech.cloudmart.dto.ReviewResponseDTO;
import com.charbhujatech.cloudmart.exception.ResourceNotFoundException;
import com.charbhujatech.cloudmart.mapper.ReviewMapper;
import com.charbhujatech.cloudmart.util.ConstantsString;
import com.charbhujatech.cloudmart.enums.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ReviewServiceImp implements ReviewService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    /**
     * @param userId
     * @return
     */
    @Override
    public List<ReviewResponseDTO> getUserReviews(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ConstantsString.USER_NOT_FOUND));
        List<Review> byUser = reviewRepository.findByUser(user);
        List<ReviewResponseDTO> reviewResponseDTOS = new ArrayList<>();
        ReviewMapper.mapToReviewResponseDTOS(byUser, reviewResponseDTOS);
        return reviewResponseDTOS;
    }

    /**
     * @param productId
     * @return
     */
    @Override
    public List<ReviewResponseDTO> getProductReviews(Long productId) {
        final Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(ConstantsString.PRODUCT_NOT_FOUND));
        final List<Review> byProduct = reviewRepository.findByProduct(product);
        List<ReviewResponseDTO> reviewResponseDTOS = new ArrayList<>();
        ReviewMapper.mapToReviewResponseDTOS(byProduct, reviewResponseDTOS);
        return reviewResponseDTOS;
    }

    @Override
    public ResponseDTO addReview(Long userId, Long orderId, Long productId, ReviewRequestDTO review) {

        final User getUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.USER_NOT_FOUND));

        final Order getOrder = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.ORDER_NOT_FOUND));

        if (getOrder.getOrderStatus() == OrderStatus.DELIVERED) {
            final Product getProduct = productRepository.findById(productId).orElseThrow(
                    () -> new ResourceNotFoundException(ConstantsString.ORDER_NOT_FOUND));

            final Set<OrderProduct> orderProducts = getOrder.getOrderProducts();
            boolean orderProductStatus = false;

            for (OrderProduct orderProduct : orderProducts) {
                if (orderProduct.getProduct().getProductId() == getProduct.getProductId()) {
                    orderProductStatus = true;
                }
            }

            if (orderProductStatus) {
                Review reviewToAdd = new Review();

                ReviewMapper.mapToReview(review, reviewToAdd, getProduct, getUser, getOrder);

                final Review saveReview = reviewRepository.save(reviewToAdd);
            }
        }
        return new ResponseDTO(HttpStatus.OK.toString(), ConstantsString.REVIEW_ADDED);
    }
}