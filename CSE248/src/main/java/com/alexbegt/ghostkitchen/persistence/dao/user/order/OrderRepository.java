package com.alexbegt.ghostkitchen.persistence.dao.user.order;

import com.alexbegt.ghostkitchen.persistence.model.user.order.Order;
import com.alexbegt.ghostkitchen.persistence.model.user.order.OrderItem;
import com.alexbegt.ghostkitchen.persistence.model.user.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

  Order findOrderById(long id);

  Order findByOrderItemsIn(Collection<OrderItem> orderItems);

}
