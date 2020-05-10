package com.alexbegt.ghostkitchen.web.controller;

import com.alexbegt.ghostkitchen.persistence.dao.cart.CartRepository;
import com.alexbegt.ghostkitchen.persistence.dao.item.ItemRepository;
import com.alexbegt.ghostkitchen.persistence.dao.restaurant.RestaurantRepository;
import com.alexbegt.ghostkitchen.persistence.model.cart.Cart;
import com.alexbegt.ghostkitchen.persistence.model.cart.CartItem;
import com.alexbegt.ghostkitchen.persistence.model.menu.Item;
import com.alexbegt.ghostkitchen.persistence.model.restaurant.Restaurant;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.persistence.model.user.order.Order;
import com.alexbegt.ghostkitchen.persistence.model.user.order.Orders;
import com.alexbegt.ghostkitchen.service.IUserService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class RestaurantController {

  @Autowired
  RestaurantRepository restaurantRepository;

  @Autowired
  IUserService userService;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private MessageSource messageSource;

  private static DecimalFormat df = new DecimalFormat("0.00");

  @GetMapping("/view-restaurants")
  public String viewRestaurants(final Locale locale, final Model model) {
    model.addAttribute("restaurants", this.restaurantRepository.findAll().toArray());

    return "/restaurant/view-restaurants";
  }

  @GetMapping("/view-menu")
  public String viewRestaurantWithRestaurantIdOf(final HttpServletRequest request, final Model model, @RequestParam("restaurantId") final String restaurantId) {
    Restaurant restaurant = this.restaurantRepository.findRestaurantById(Long.parseLong(restaurantId));

    if (restaurant != null) {
      List<Item> items = new ArrayList<>(restaurant.getItems());

      model.addAttribute("items", items);
      model.addAttribute("restaurantId", restaurantId);

      return "/restaurant/view-menu";
    }

    model.addAttribute("error", this.messageSource.getMessage("message.invalidRestaurant", null, LocaleContextHolder.getLocale()));

    return "redirect:/view-restaurants";
  }

  @GetMapping("/add-item-to-cart")
  public String addItemToCartAndReturnToMenu(final HttpServletRequest request, final Model model, @RequestParam("itemId") final String itemId, @RequestParam("restaurantId") final String restaurantId) {
    Item item = this.itemRepository.findItemById(Long.valueOf(itemId));
    final User user = this.userService.findUserByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());

    if (item != null) {
      this.userService.addItemToCart(user, item);

      model.addAttribute("message", this.messageSource.getMessage("message.itemAddedToCart", new Object[] { item.getName() }, LocaleContextHolder.getLocale()));
      model.addAttribute("restaurantId", restaurantId);
      return "redirect:/view-menu";
    }

    model.addAttribute("error", this.messageSource.getMessage("message.invalidItem", null, LocaleContextHolder.getLocale()));
    model.addAttribute("restaurantId", restaurantId);
    return "redirect:/view-menu";
  }

  @GetMapping("/view-cart")
  public String viewCart(final HttpServletRequest request, final Model model, @RequestParam("locationId") final String locationId, @RequestParam("restaurantId") final String restaurantId) {
    final User user = this.userService.findUserByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
    Cart cart = this.cartRepository.findByUser(user);

    if(cart != null) {
      if (!cart.isCartEmpty()) {
        List<CartItemPair> items = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
          items.add(new CartItemPair(cartItem.getItem(), cartItem.getQuantity()));
        }

        model.addAttribute("items", items);

        model.addAttribute("orderSubtotal", df.format(cart.getSubTotal()));
        model.addAttribute("orderTax", df.format(cart.getTax()));
        model.addAttribute("orderTotal", df.format(cart.getTotal()));

        return "/restaurant/view-cart";
      }
    }

    if (locationId.equalsIgnoreCase("1")) {
      model.addAttribute("error", this.messageSource.getMessage("message.emptyCart", null, LocaleContextHolder.getLocale()));
      model.addAttribute("restaurantId", restaurantId);
      return "redirect:/view-menu";
    }
    else {
      model.addAttribute("error", this.messageSource.getMessage("message.emptyCart", null, LocaleContextHolder.getLocale()));
      return "redirect:/home";
    }
  }

  static class CartItemPair {

    public Item item;
    public int quantity;

    public CartItemPair(Item item, int quantity) {
      this.item = item;
      this.quantity = quantity;
    }
  }
}
