package com.alexbegt.ghostkitchen.web.controller;

import com.alexbegt.ghostkitchen.persistence.dao.user.order.OrderItemRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.order.OrderRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.order.OrdersRepository;
import com.alexbegt.ghostkitchen.persistence.model.menu.Item;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.persistence.model.user.order.Order;
import com.alexbegt.ghostkitchen.persistence.model.user.order.OrderItem;
import com.alexbegt.ghostkitchen.persistence.model.user.order.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Controller
public class OrderController {

  @Autowired
  private OrdersRepository ordersRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private MessageSource messageSource;

  private static DecimalFormat df = new DecimalFormat("0.00");

  @GetMapping("/view-orders")
  public String getAllOrders(final Locale locale, final Model model) {
    User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    Orders orders = this.ordersRepository.findByUser(user);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    if (orders != null) {
      if (orders.getNumberOfOrders() != 0) {
        Collection<Order> placedOrders = orders.getPlacedOrders();

        model.addAttribute("numberOfOrders", orders.getNumberOfOrders());

        if (orders.getLatestOrderDate() != null) {
          model.addAttribute("lastOrderTime", orders.getLatestOrderDate().format(formatter));
        }
        else {
          model.addAttribute("lastOrderTime", orders.getFirstOrderDate().format(formatter));
        }

        model.addAttribute("placedOrders", placedOrders);

        return "/user/view-orders";
      }
    }

    model.addAttribute("error", this.messageSource.getMessage("message.noOrders", null, LocaleContextHolder.getLocale()));

    return "redirect:/home";
  }

  @GetMapping("/view-order-details")
  public String viewOrderWithOrderIdOf(final HttpServletRequest request, final Model model, @RequestParam("orderId") final String orderId) {
    Order order = this.orderRepository.findOrderById(Long.parseLong(orderId));

    if (order != null) {
      List<OrderItemPair> items = new ArrayList<>();

      for (OrderItem orderItem : order.getOrderItems()) {
        items.add(new OrderItemPair(orderItem.getItem(), orderItem.getQuantity()));
      }

      model.addAttribute("orderNumber", orderId);
      model.addAttribute("items", items);

      model.addAttribute("orderSubtotal", df.format(order.getSubTotal()));
      model.addAttribute("orderTax", df.format(order.getTax()));
      model.addAttribute("orderTotal", df.format(order.getTotal()));

      return "/user/view-order-details";
    }

    model.addAttribute("error", this.messageSource.getMessage("message.invalidOrder", null, LocaleContextHolder.getLocale()));

    return "redirect:/view-orders";
  }

  static class OrderItemPair {
    public Item item;
    public int quantity;

    public OrderItemPair(Item item, int quantity) {
      this.item = item;
      this.quantity = quantity;
    }
  }
}
