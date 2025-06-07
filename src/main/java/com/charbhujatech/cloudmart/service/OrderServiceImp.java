package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.*;
import com.charbhujatech.cloudmart.dao.*;
import com.online.shopping.Model.*;
import com.online.shopping.dao.*;
import com.charbhujatech.cloudmart.dto.OrdersResponseDTO;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.exception.BadRequestException;
import com.charbhujatech.cloudmart.exception.ResourceNotFoundException;
import com.charbhujatech.cloudmart.dto.OrderResponseDTO;
import com.charbhujatech.cloudmart.mapper.OrderMapper;
import com.charbhujatech.cloudmart.util.ConstantsConfig;
import com.charbhujatech.cloudmart.util.ConstantsString;
import com.charbhujatech.cloudmart.enums.OrderStatus;
import com.charbhujatech.cloudmart.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderServiceImp implements OrderService {

    private final ProductRepository productRepository;
    private final CardProductRepository cardProductRepository;
    private final OrderRepository orderRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public ResponseDTO createOrder(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.USER_NOT_FOUND));
        Order order = new Order();
        Card card = cardRepository.findByUser(user);
        Set<CardProduct> cardProducts = card.getCardProducts();

        if ( cardProducts.isEmpty() )
            throw new BadRequestException(ConstantsString.PRODUCT_IS_NOT_IN_CARD);

        double totalPrice = 0;
        Set<OrderProduct> orderProducts = new HashSet<>();
        Iterator<CardProduct> iterator = cardProducts.iterator();
        HashMap<Long,Integer> productQuntityUpdate = new HashMap<>();
        while(iterator.hasNext()){
            CardProduct cardProduct = iterator.next();
            if(cardProduct.getQuantity() > 0 && (cardProduct.getProduct() != null ) )
            {
                Optional<Product> changeProduct = productRepository.findById(cardProduct.getProduct().getProductId());
                if (changeProduct.isPresent()) {
                    Product product = changeProduct.get();
                    if (product.getAvailableQuantity() >= cardProduct.getQuantity()) {
                        OrderProduct orderProduct = new OrderProduct();
                        orderProduct.setQuantity(cardProduct.getQuantity());
                        orderProduct.setProduct(product);
                        orderProduct.setOrder(order);
                        orderProducts.add(orderProduct);
                        totalPrice += product.getPrice() * cardProduct.getQuantity();

                        productQuntityUpdate.put(product.getProductId(),cardProduct.getQuantity());
                    }else
                    {
                        throw new BadRequestException(ConstantsString.PRODUCT_QUANTITY_NOT_AVAILABLE);
                    }
                }
                else
                {
                    throw new ResourceNotFoundException(ConstantsString.PRODUCT_NOT_FOUND);
                }
                cardProductRepository.deleteByCardProductId(cardProduct.getCardProductId());
            }
            else
            {
                throw new BadRequestException(ConstantsString.PRODUCT_IS_NOT_IN_CARD);
            }
        }

        productQuntityUpdate.forEach(
                (productId,quantity) -> {
                    Product byId = productRepository.findById(productId).get();
                    byId.setAvailableQuantity(byId.getAvailableQuantity()-quantity);
                    productRepository.save(byId);
                });

        order.setTotalPrice(totalPrice);
        order.setOrderProducts(orderProducts);
        order.setUser(user);
        order.setOrderStatus(OrderStatus.AWAITING_PAYMENT);
        order.setOrderDate(new Date());
        final Order save = orderRepository.save(order);
        return new ResponseDTO(HttpStatus.OK.toString(), ConstantsString.ORDER_CREATED);
    }

    /**
     * @param userId
     * @param orderId
     * @return
     */
    @Override
    public Boolean deleteOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.ORDER_NOT_FOUND));

        if (order.getOrderStatus() != OrderStatus.AWAITING_PAYMENT)
            throw new BadRequestException(ConstantsString.ORDER_CAN_NOT_BE_DELETED_AFTER_PAYMENT);

        orderRepository.deleteByOrderId(orderId);

        return !orderRepository.existsById(orderId);
    }

    /**
     * @param orderId
     * @return
     */
    @Override
    public OrderResponseDTO getOrder(Long orderId) {
        final Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.ORDER_NOT_FOUND));
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        OrderMapper.mapToOrderResponseDTO(order,responseDTO);
        return responseDTO;
    }

    /**
     * @param userId
     * @param orderStatus
     * @param page
     * @param sortBy
     * @param ascending
     * @return
     */
    @Override
    public OrdersResponseDTO getAllOrder(Long userId, OrderStatus orderStatus, int page, String sortBy, Boolean ascending) {
        int pageSize = ConstantsConfig.PAGE_SIDE;
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, pageSize, sort);
        Page<Order> allOrder = null;

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ConstantsString.USER_NOT_FOUND));

        if( orderStatus == null)
        {
            allOrder = orderRepository.findByUser(user,pageable);
        }
        else
        {
            allOrder = orderRepository.findByOrderStatusAndUser(orderStatus, user , pageable);
        }

        OrdersResponseDTO ordersResponseDTO = new OrdersResponseDTO();

        OrderMapper.mapToOrdersResponseDTO(allOrder,ordersResponseDTO);

        return ordersResponseDTO;
    }

    /**
     * @param userId
     * @param orderId
     * @return
     */
    @Override
    public ResponseDTO confirmedOrder(Long userId, Long orderId) {

        Order order = findOrder(orderId);

        return updateOrderStatus(order,OrderStatus.PENDING,OrderStatus.CONFIRMED, ConstantsString.ORDER_CONFIRMED, ConstantsString.ORDER_NOT_IN_PENDING_STATUS);

    }

    /**
     * @param userId
     * @param orderId
     * @return
     */
    @Override
    public ResponseDTO processingOrder(Long userId, Long orderId) {

        Order order = findOrder(orderId);

        return updateOrderStatus(order,OrderStatus.CONFIRMED,OrderStatus.PROCESSING, ConstantsString.ORDER_PROCESSING, ConstantsString.ORDER_NOT_IN_CONFIRMED_STATUS);
    }

    /**
     * @param userId
     * @param orderId
     * @return
     */
    @Override
    public ResponseDTO shippingOrder(Long userId, Long orderId) {

        Order order = findOrder(orderId);

        return updateOrderStatus(order,OrderStatus.PROCESSING,OrderStatus.SHIPPED, ConstantsString.ORDER_SHIPPED, ConstantsString.ORDER_NOT_IN_PROCESSING_STATUS);
    }

    /**
     * @param userId
     * @param orderId
     * @return
     */
    @Override
    public ResponseDTO outForDeliveryOrder(Long userId, Long orderId) {

        Order order = findOrder(orderId);

        return updateOrderStatus(order,OrderStatus.SHIPPED,OrderStatus.OUT_FOR_DELIVERY, ConstantsString.ORDER_OUT_FOR_DELIVERY, ConstantsString.ORDER_NOT_IN_SHIPPED_STATUS);
    }

    /**
     * @param userId
     * @param orderId
     * @return
     */
    @Override
    public ResponseDTO deliverredOrder(Long userId, Long orderId) {

        Order order = findOrder(orderId);

        return updateOrderStatus(order,OrderStatus.OUT_FOR_DELIVERY,OrderStatus.DELIVERED, ConstantsString.ORDER_DELIVERED, ConstantsString.ORDER_NOT_IN_OUT_FOR_DELIVERY_STATUS);

    }

    /**
     * @param userId
     * @param orderId
     * @return
     */
    @Override
    public ResponseDTO returnRequestOrder(Long userId, Long orderId) {

        Order order = findOrder(orderId);

        return updateOrderStatus(order,OrderStatus.DELIVERED,OrderStatus.RETURN_REQUESTED, ConstantsString.ORDER_RETURNED_REQUESTED, ConstantsString.ORDER_NOT_IN_DELIVERED_STATUS);

    }

    /**
     * @param userId
     * @param orderId
     * @return
     */
    @Override
    public ResponseDTO returnedOrder(Long userId, Long orderId) {

        Order order = findOrder(orderId);

        return updateOrderStatus(order,OrderStatus.RETURN_REQUESTED,OrderStatus.RETURN_ACCEPTED, ConstantsString.ORDER_RETURN_ACCEPTED, ConstantsString.ORDER_NOT_IN_RETURN_REQUESTED);

    }

    /**
     * @param userId
     * @param orderId
     * @return
     */
    @Override
    public ResponseDTO rejectedOrder(Long userId, Long orderId) {

        Order order = findOrder(orderId);

        if ( order.getOrderStatus() != null && order.getOrderStatus() == OrderStatus.RETURN_REQUESTED )
            return updateOrderStatus(order,OrderStatus.RETURN_REQUESTED,OrderStatus.REJECTED, ConstantsString.ORDER_REJECTED_BY_VENDOR, ConstantsString.ORDER_NOT_IN_RETURN_STATUS);
        else
            return updateOrderStatus(order,OrderStatus.PENDING,OrderStatus.REJECTED, ConstantsString.ORDER_REJECTED_BY_VENDOR, ConstantsString.ORDER_NOT_IN_PENDING_STATUS);

    }

    /**
     * @param userId
     * @param orderId
     * @return
     */
    @Override
    public ResponseDTO refundOrder(Long userId, Long orderId) {
        Order order = findOrder(orderId);

        Payment payment = paymentRepository.findByOrder(order).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.PAYMENT_NOT_FOUND));

        payment.setPaymentStatus(PaymentStatus.REFUNDED);

        paymentRepository.saveAndFlush(payment);

        return updateOrderStatus(order,OrderStatus.RETURN_ACCEPTED,OrderStatus.RETURNED_AND_REFUNDED, ConstantsString.ORDER_RETURNED_REFUNDED, ConstantsString.ORDER_NOT_IN_RETURN_ACCEPTED_STATUS);
    }

    @Override
    public void paymentOrder(Order order, Long userId, Long orderId) {
        updateOrderStatus(order,OrderStatus.AWAITING_PAYMENT,OrderStatus.PENDING, ConstantsString.ORDER_PENDING, ConstantsString.ORDER_NOT_IN_AWAITING_PAYMENT_STATUS);
    }

    private Order findOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.ORDER_NOT_FOUND));
        return order;
    }

    private ResponseDTO updateOrderStatus(Order order, OrderStatus src, OrderStatus dst, String sucessString, String failedString) {
        if (order.getOrderStatus() != null && order.getOrderStatus() == src) {

            order.setOrderStatus(dst);
            final Order save = orderRepository.save(order);
            return new ResponseDTO(HttpStatus.OK.toString(), sucessString);
        }
        else{
            throw new BadRequestException(failedString);
        }
    }
}
