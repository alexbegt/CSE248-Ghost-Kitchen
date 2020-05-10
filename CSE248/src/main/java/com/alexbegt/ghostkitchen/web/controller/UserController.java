package com.alexbegt.ghostkitchen.web.controller;

import com.alexbegt.ghostkitchen.persistence.dao.restaurant.RestaurantRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.order.OrderItemRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.order.OrderRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.order.OrdersRepository;
import com.alexbegt.ghostkitchen.persistence.model.menu.Item;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.persistence.model.user.order.Order;
import com.alexbegt.ghostkitchen.persistence.model.user.order.OrderItem;
import com.alexbegt.ghostkitchen.persistence.model.user.order.Orders;
import com.alexbegt.ghostkitchen.security.user.ActiveUserStorage;
import com.alexbegt.ghostkitchen.service.IUserService;
import com.alexbegt.ghostkitchen.web.dto.password.PasswordDto;
import com.alexbegt.ghostkitchen.web.dto.user.AddressDto;
import com.alexbegt.ghostkitchen.web.dto.user.CreditCardDto;
import com.alexbegt.ghostkitchen.web.error.InvalidOldPasswordException;
import com.alexbegt.ghostkitchen.web.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Controller
public class UserController {

  @Autowired
  ActiveUserStorage activeUserStorage;

  @Autowired
  IUserService userService;

  @Autowired
  private MessageSource messageSource;

  @PostMapping("/api/change-password")
  @ResponseBody
  public GenericResponse savePassword(@Valid PasswordDto passwordDto) throws InvalidOldPasswordException {
    final User user = this.userService.findUserByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());

    if (!this.userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
      throw new InvalidOldPasswordException();
    }

    this.userService.changeUserPassword(user, passwordDto.getNewPassword());

    return new GenericResponse("success");
  }

  @PostMapping("/api/change-address")
  @ResponseBody
  public GenericResponse changeAddress(@Valid AddressDto addressDto) {
    final User user = this.userService.findUserByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());

    this.userService.changeUserAddress(user, addressDto.getStreetAddress(), addressDto.getCity(), addressDto.getState(), addressDto.getZipCode());

    return new GenericResponse(this.messageSource.getMessage("message.addressChanged", null, LocaleContextHolder.getLocale()));
  }

  @PostMapping("/api/change-credit-card")
  @ResponseBody
  public GenericResponse changeCreditCard(@Valid CreditCardDto creditCardDto) {
    final User user = this.userService.findUserByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());

    this.userService.changeUserCreditCard(user, creditCardDto.getCardHolderName(), creditCardDto.getCreditCardNumber(), creditCardDto.getExpirationDate(), creditCardDto.getCvv());

    return new GenericResponse(this.messageSource.getMessage("message.creditCardChanged", null, LocaleContextHolder.getLocale()));
  }

  @PostMapping("/api/disable-two-factor-authentication")
  @ResponseBody
  public GenericResponse modifyUserTwoFactorAuthentication() {
    this.userService.updateUserTwoFactorAuthentication(false);

    return null;
  }

  @GetMapping("/home")
  public String getUserHome(final Locale locale, final Model model) {
    model.addAttribute("currentUser", (((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getFullName()));

    return "/user/home";
  }
}
