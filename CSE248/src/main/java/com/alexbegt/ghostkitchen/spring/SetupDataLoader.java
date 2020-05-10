package com.alexbegt.ghostkitchen.spring;

import com.alexbegt.ghostkitchen.persistence.dao.cart.CartItemRepository;
import com.alexbegt.ghostkitchen.persistence.dao.cart.CartRepository;
import com.alexbegt.ghostkitchen.persistence.dao.item.ItemRepository;
import com.alexbegt.ghostkitchen.persistence.dao.restaurant.RestaurantRepository;
import com.alexbegt.ghostkitchen.persistence.dao.role.PrivilegeRepository;
import com.alexbegt.ghostkitchen.persistence.dao.role.RoleRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.UserRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.address.AddressRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.order.OrderItemRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.order.OrderRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.order.OrdersRepository;
import com.alexbegt.ghostkitchen.persistence.model.cart.Cart;
import com.alexbegt.ghostkitchen.persistence.model.cart.CartItem;
import com.alexbegt.ghostkitchen.persistence.model.menu.Item;
import com.alexbegt.ghostkitchen.persistence.model.restaurant.Restaurant;
import com.alexbegt.ghostkitchen.persistence.model.role.Privilege;
import com.alexbegt.ghostkitchen.persistence.model.role.Role;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.persistence.model.user.address.Address;
import com.alexbegt.ghostkitchen.persistence.model.user.order.Order;
import com.alexbegt.ghostkitchen.persistence.model.user.order.OrderItem;
import com.alexbegt.ghostkitchen.persistence.model.user.order.Orders;
import com.alexbegt.ghostkitchen.util.Defaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private boolean alreadySetup = false;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PrivilegeRepository privilegeRepository;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private CartItemRepository cartItemRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private OrdersRepository ordersRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {

    if (this.alreadySetup) {
      return;
    }

    // == create initial privileges
    final Privilege readPrivilege = this.createPrivilegeIfNotFound(Defaults.READ_PRIVILEGE);
    final Privilege writePrivilege = this.createPrivilegeIfNotFound(Defaults.WRITE_PRIVILEGE);
    final Privilege passwordPrivilege = this.createPrivilegeIfNotFound(Defaults.CHANGE_PASSWORD_PRIVILEGE);

    // == create initial roles
    final List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
    final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));
    final Role adminRole = this.createRoleIfNotFound(Defaults.ADMIN_ROLE, adminPrivileges);
    final Role userRole = this.createRoleIfNotFound(Defaults.USER_ROLE, userPrivileges);

    // === create initial items
    final Item burger = this.createItemIfNotFound("Burger", "A burger cooked to your liking.", 10.99, "burger.png");
    final Item steak = this.createItemIfNotFound("Streak", "A steak cooked to your liking.", 20.99, "steak.png");
    final Item cheeseburger = this.createItemIfNotFound("Cheeseburger", "A cheeseburger cooked to your liking.", 12.99, "cheeseburger.png");

    // === create initial cart items
    final CartItem burgerCartItem = this.createCartItemIfNotFound(burger, 1);
    final CartItem cheeseburgerCartItem = this.createCartItemIfNotFound(cheeseburger, 1);
    final CartItem twoCheeseburgerCartItem = this.createCartItemIfNotFound(cheeseburger, 2);
    final List<CartItem> adminCartItems = new ArrayList<>(Arrays.asList(burgerCartItem, cheeseburgerCartItem));
    final List<CartItem> userCartItems = new ArrayList<>(Collections.singletonList(twoCheeseburgerCartItem));

    // == create initial restaurants
    final List<Item> jcMenuItems = new ArrayList<>(Arrays.asList(burger, cheeseburger, steak));
    final List<Item> menuItems = new ArrayList<>(Arrays.asList(burger, cheeseburger));
    this.createRestaurantIfNotFound("JC's Restaurant", "2 Country Club Dr", "Manorville", "NY", "11949", "631-677-8778", jcMenuItems);
    this.createRestaurantIfNotFound("McDonald's", "971 Montauk Hwy", "Shirley", "NY", "11967", "631-399-6404", menuItems);

    // == create initial order items
    final OrderItem burgerOrderItem = this.createOrderItemIfNotFound(burger, 1);
    final OrderItem cheeseBurgerOrderItem = this.createOrderItemIfNotFound(cheeseburger, 1);
    final OrderItem twoCheeseBurgerOrderItem = this.createOrderItemIfNotFound(cheeseburger, 2);
    final List<OrderItem> adminOrderItems = new ArrayList<>(Arrays.asList(burgerOrderItem, cheeseBurgerOrderItem));
    final List<OrderItem> userOrderItems = new ArrayList<>(Collections.singletonList(twoCheeseBurgerOrderItem));

    // == create initial users
    User adminUser = this.createUserIfNotFound(Defaults.ADMIN_FIRST_NAME, Defaults.ADMIN_LAST_NAME, Defaults.ADMIN_EMAIL, Defaults.ADMIN_PASSWORD, new ArrayList<>(Collections.singletonList(adminRole)), adminCartItems, adminOrderItems);
    User normalUser = this.createUserIfNotFound(Defaults.USER_FIRST_NAME, Defaults.USER_LAST_NAME, Defaults.USER_EMAIL, Defaults.USER_PASSWORD, new ArrayList<>(Collections.singletonList(userRole)), userCartItems, userOrderItems);

    final Address adminAddress = this.createAddressIfNotFound(adminUser, Defaults.ADMIN_STREET_ADDRESS, Defaults.ADMIN_CITY, Defaults.ADMIN_STATE, Defaults.ADMIN_ZIP);
    final Address userAddress = this.createAddressIfNotFound(normalUser, Defaults.USER_STREET_ADDRESS, Defaults.USER_CITY, Defaults.USER_STATE, Defaults.USER_ZIP);

    this.setUsersAddress(adminUser, adminAddress);
    this.setUsersAddress(normalUser, userAddress);

    this.alreadySetup = true;
  }

  @Transactional
  Privilege createPrivilegeIfNotFound(final String name) {
    Privilege privilege = this.privilegeRepository.findByName(name);

    if (privilege == null) {
      privilege = new Privilege(name);
      privilege = this.privilegeRepository.save(privilege);
    }

    return privilege;
  }

  @Transactional
  Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
    Role role = this.roleRepository.findByName(name);

    if (role == null) {
      role = new Role(name);
    }

    role.setPrivileges(privileges);
    role = this.roleRepository.save(role);

    return role;
  }

  @Transactional
  Item createItemIfNotFound(final String name, final String description, final Double price, final String image) {
    Item item = this.itemRepository.findByName(name);

    if (item == null) {
      item = new Item(name, description, price, image);

      item = this.itemRepository.save(item);
    }

    return item;
  }

  @Transactional
  CartItem createCartItemIfNotFound(final Item item, final int quantity) {
    CartItem cartItem = this.cartItemRepository.findByItemAndQuantity(item, quantity);

    if (cartItem == null) {
      cartItem = new CartItem(quantity);
    }

    cartItem.setItem(item);
    cartItem = this.cartItemRepository.save(cartItem);

    return cartItem;
  }

  @Transactional
  Restaurant createRestaurantIfNotFound(final String name, final String streetAddress, final String city, final String state, final String zipCode, final String phoneNumber, final Collection<Item> items) {
    Restaurant restaurant = this.restaurantRepository.findByName(name);

    if (restaurant == null) {
      restaurant = new Restaurant(name, streetAddress, city, state, zipCode, phoneNumber);
    }

    restaurant.setItems(items);
    restaurant = this.restaurantRepository.save(restaurant);

    return restaurant;
  }

  @Transactional
  User createUserIfNotFound(final String firstName, final String lastName, final String email, final String password, final Collection<Role> roles, List<CartItem> cartItems, List<OrderItem> orderItems) {
    User user = this.userRepository.findByEmail(email);
    Cart cart = this.cartRepository.findByUser(user);
    Orders orders = this.ordersRepository.findByUser(user);
    Order order = this.orderRepository.findByOrderItemsIn(orderItems);

    if (cart == null) {
      cart = new Cart();

      cart = this.cartRepository.save(cart);
    }

    if (orders == null) {
      orders = new Orders();

      orders = this.ordersRepository.save(orders);
    }

    if (order == null) {
      order = new Order();

      order = this.orderRepository.save(order);
    }

    if (cartItems != null) {
      cart.setCartItems(cartItems);

      cart = this.cartRepository.save(cart);
    }

    if (orderItems != null) {
      order.setOrderItems(orderItems);

      order = this.orderRepository.save(order);
    }

    orders.addOrder(order);

    orders = this.ordersRepository.save(orders);

    if (user == null) {
      user = new User();
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setEmail(email);
      user.setPassword(this.passwordEncoder.encode(password));
      user.setEnabled(true);
      user.setCart(cart);
    }

    user.setCart(cart);
    user.setRoles(roles);
    user.setOrders(orders);

    user = this.userRepository.save(user);

    return user;
  }

  @Transactional
  Address createAddressIfNotFound(final User user, final String streetAddress, final String city, final String state, final String zipCode) {
    Address address = this.addressRepository.findByUser(user);

    if (address == null) {
      address = new Address(streetAddress, city, state, zipCode);

      address = this.addressRepository.save(address);
    }

    return address;
  }

  void setUsersAddress(final User user, final Address address) {
    if (user != null) {
      user.setAddress(address);

      this.userRepository.save(user);
    }
  }

  @Transactional
  OrderItem createOrderItemIfNotFound(final Item item, final int quantity) {
    OrderItem orderItem = this.orderItemRepository.findByItemAndQuantity(item, quantity);

    if (orderItem == null) {
      orderItem = new OrderItem(quantity);
    }

    orderItem.setItem(item);
    orderItem = this.orderItemRepository.save(orderItem);

    return orderItem;
  }
}
