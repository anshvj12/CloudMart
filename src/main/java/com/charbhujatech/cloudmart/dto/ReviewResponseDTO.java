package com.charbhujatech.cloudmart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDTO {

    private Long reviewId;

    private String reviewText;

    private int rating;

}
