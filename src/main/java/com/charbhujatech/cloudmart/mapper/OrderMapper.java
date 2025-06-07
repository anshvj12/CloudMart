package com.charbhujatech.cloudmart.mapper;

import com.charbhujatech.cloudmart.Model.Order;
import com.charbhujatech.cloudmart.Model.OrderProduct;
import com.charbhujatech.cloudmart.dto.CardProductResponse;
import com.charbhujatech.cloudmart.dto.OrderResponseDTO;
import com.charbhujatech.cloudmart.dto.OrdersResponseDTO;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class OrderMapper {

    public static void mapToOrderResponseDTO(Order order, OrderResponseDTO orderResponseDTO) {
        if(order.getOrderStatus() != null)
        {
            orderResponseDTO.setOrderStatus(order.getOrderStatus());
        }

        if (order.getTotalPrice() > 0)
        {
            orderResponseDTO.setTotalPrice(order.getTotalPrice());
        }
        List<CardProductResponse> cardProductResponses = new ArrayList<>();
        if(order.getOrderProducts() != null)
        {
            final Set<OrderProduct> orderProducts1 = order.getOrderProducts();
            for (OrderProduct orderProduct : orderProducts1) {
                CardProductResponse cardProductResponse = new CardProductResponse();
                if(orderProduct.getProduct() != null)
                {
                    cardProductResponse.setProductName(orderProduct.getProduct().getProductName());
                }
                if(orderProduct.getQuantity() > 0)
                {
                    cardProductResponse.setQuantity(orderProduct.getQuantity());
                }
                cardProductResponses.add(cardProductResponse);
            }
            orderResponseDTO.setOrderProducts(cardProductResponses);
        }
        if(order.getOrderDate() != null)
            orderResponseDTO.setOrderDate(order.getOrderDate());
        if(order.getDeliveryDate() != null)
            orderResponseDTO.setDeliveryDate(order.getDeliveryDate());
    }

    public static void mapToOrdersResponseDTO(Page<Order> allOrder, OrdersResponseDTO ordersResponseDTO) {

        ordersResponseDTO.setTotalElements(allOrder.getTotalElements());
        ordersResponseDTO.setTotalPages(allOrder.getTotalPages());
        ordersResponseDTO.setPageSize(allOrder.getSize());
        ordersResponseDTO.setPageNumber(allOrder.getNumber());

        final Iterator<Order> iterator = allOrder.stream().iterator();
        while(iterator.hasNext())
        {
            Order order = iterator.next();
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
            mapToOrderResponseDTO(order,orderResponseDTO);
            ordersResponseDTO.getOrderResponseDTOList().add(orderResponseDTO);
        }

    }
}
