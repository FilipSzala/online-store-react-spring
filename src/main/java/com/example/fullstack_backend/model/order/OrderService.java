package com.example.fullstack_backend.model.order;

import com.example.fullstack_backend.model.cart.Cart;
import com.example.fullstack_backend.model.cart.ICartService;
import com.example.fullstack_backend.model.order.dtoResponse.OrderDto;
import com.example.fullstack_backend.model.order_item.OrderItem;
import com.example.fullstack_backend.model.order_item.OrderItemService;
import com.example.fullstack_backend.model.product.Product;
import com.example.fullstack_backend.model.product.ProductRepository;
import com.example.fullstack_backend.model.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ICartService cartService;
    private final ModelMapper modelMapper;
    private final ProductService productService;
    private final OrderItemService orderItemService;

    @Override
    @Transactional
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart.isEmpty()){
            throw new IllegalStateException("You can't place order if cart is empty");
        }
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        Order savedOrder = orderRepository.save(order);
        cartService.deleteCart(cart.getId());
        return savedOrder;
    }
    private List<OrderItem> createOrderItems(Order order, Cart cart){
        List <OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    Product product = productService.updateInventoryInProduct(cartItem.getProduct(),cartItem.getQuantity());
                    int quantity = cartItem.getQuantity();
                    BigDecimal unitPrice = cartItem.getUnitPrice();
                    OrderItem orderItem = orderItemService.createOrderItem(quantity, unitPrice, order, product);
                    return orderItem;
                }).toList();

        return orderItems;
    }

    private Order createOrder (Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
    @Override
    public OrderDto convertToDto(Order order){
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        return orderDto;
    }
}
