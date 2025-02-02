package com.progcon.programacionconcurrente2.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public void updateOrder(Order order) throws Exception {
        orderRepository.save(order);
        if (order.getAmount() < 0) {
            throw new Exception("Invalid order amount");
        }
    }
}
