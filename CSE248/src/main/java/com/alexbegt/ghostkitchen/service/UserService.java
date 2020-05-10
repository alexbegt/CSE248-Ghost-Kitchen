package com.alexbegt.ghostkitchen.service;

import com.alexbegt.ghostkitchen.persistence.dao.cart.CartItemRepository;
import com.alexbegt.ghostkitchen.persistence.dao.cart.CartRepository;
import com.alexbegt.ghostkitchen.persistence.dao.device.NewLocationTokenRepository;
import com.alexbegt.ghostkitchen.persistence.dao.device.UserLocationRepository;
import com.alexbegt.ghostkitchen.persistence.dao.role.RoleRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.UserRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.address.AddressRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.credit.CreditCardRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.token.PasswordResetTokenRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.token.VerificationTokenRepository;
import com.alexbegt.ghostkitchen.persistence.model.cart.Cart;
import com.alexbegt.ghostkitchen.persistence.model.cart.CartItem;
import com.alexbegt.ghostkitchen.persistence.model.device.NewLocationToken;
import com.alexbegt.ghostkitchen.persistence.model.device.UserLocation;
import com.alexbegt.ghostkitchen.persistence.model.menu.Item;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.persistence.model.user.address.Address;
import com.alexbegt.ghostkitchen.persistence.model.user.credit.CreditCard;
import com.alexbegt.ghostkitchen.persistence.model.user.token.PasswordResetToken;
import com.alexbegt.ghostkitchen.persistence.model.user.token.VerificationToken;
import com.alexbegt.ghostkitchen.util.Defaults;
import com.alexbegt.ghostkitchen.web.dto.user.UserDto;
import com.alexbegt.ghostkitchen.web.error.UserAlreadyExistException;
import com.maxmind.geoip2.DatabaseReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements IUserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private VerificationTokenRepository verificationTokenRepository;

  @Autowired
  private PasswordResetTokenRepository passwordTokenRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private CreditCardRepository creditCardRepository;

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CartItemRepository cartItemRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private SessionRegistry sessionRegistry;

  @Autowired
  private DatabaseReader databaseReader;

  @Autowired
  private UserLocationRepository userLocationRepository;

  @Autowired
  private NewLocationTokenRepository newLocationTokenRepository;

  @Override
  public User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException {
    if (this.emailExists(accountDto.getEmail())) {
      throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
    }

    final User user = new User();

    user.setFirstName(accountDto.getFirstName());
    user.setLastName(accountDto.getLastName());
    user.setEmail(accountDto.getEmail());
    user.setPassword(this.passwordEncoder.encode(accountDto.getPassword()));
    user.setUsingTwoFactorAuthentication(accountDto.getIfUsingTwoFactorAuthentication());
    user.setRoles(Collections.singletonList(this.roleRepository.findByName(Defaults.USER_ROLE)));

    return this.userRepository.save(user);
  }

  @Override
  public User getUser(String verificationToken) {
    final VerificationToken token = this.verificationTokenRepository.findByToken(verificationToken);

    if (token != null) {
      return token.getUser();
    }

    return null;
  }

  @Override
  public void createVerificationTokenForUser(User user, String token) {
    final VerificationToken myToken = new VerificationToken(token, user);

    this.verificationTokenRepository.deleteAllByUser(user);

    this.verificationTokenRepository.save(myToken);
  }

  @Override
  public VerificationToken generateNewVerificationToken(String token) {
    VerificationToken newVerificationToken = this.verificationTokenRepository.findByToken(token);

    newVerificationToken.updateToken(UUID.randomUUID().toString());
    newVerificationToken = this.verificationTokenRepository.save(newVerificationToken);

    return newVerificationToken;
  }

  @Override
  public void createPasswordResetTokenForUser(User user, String token) {
    final PasswordResetToken myToken = new PasswordResetToken(token, user);

    this.passwordTokenRepository.deleteAllByUser(user);

    this.passwordTokenRepository.save(myToken);
  }

  @Override
  public User findUserByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  @Override
  public Optional<User> getUserByPasswordResetToken(String token) {
    return Optional.ofNullable(this.passwordTokenRepository.findByToken(token).getUser());
  }

  @Override
  public void changeUserPassword(User user, String password) {
    user.setPassword(this.passwordEncoder.encode(password));

    this.userRepository.save(user);
  }

  @Override
  public boolean checkIfValidOldPassword(User user, String password) {
    return this.passwordEncoder.matches(password, user.getPassword());
  }

  @Override
  public String validateVerificationToken(String token) {
    final VerificationToken verificationToken = this.verificationTokenRepository.findByToken(token);

    if (verificationToken == null) {
      return Defaults.VERIFICATION_TOKEN_INVALID;
    }

    final User user = verificationToken.getUser();

    final Calendar cal = Calendar.getInstance();

    if ((verificationToken.getExpirationDate().getTime() - cal.getTime().getTime()) <= 0) {
      this.verificationTokenRepository.delete(verificationToken);
      return Defaults.VERIFICATION_TOKEN_EXPIRED;
    }

    user.setEnabled(true);

    this.userRepository.save(user);

    return Defaults.VERIFICATION_TOKEN_VALID;
  }

  @Override
  public List<String> getUsersFromSessionRegistry() {
    return sessionRegistry.getAllPrincipals()
      .stream()
      .filter((u) -> !sessionRegistry.getAllSessions(u, false).isEmpty())
      .map(o -> {
        if (o instanceof User) {
          return ((User) o).getEmail();
        }
        else {
          return o.toString();
        }
      }).collect(Collectors.toList());
  }

  @Override
  public NewLocationToken isNewLoginLocation(String username, String ip) {
    if (username.equalsIgnoreCase(Defaults.ADMIN_EMAIL) || username.equalsIgnoreCase(Defaults.USER_EMAIL)) { // Don't send emails to the admin user.
      return null;
    }

    try {
      final InetAddress ipAddress = InetAddress.getByName(ip);
      final String country = this.databaseReader.country(ipAddress).getCountry().getName();

      final User user = this.userRepository.findByEmail(username);
      final UserLocation loc = this.userLocationRepository.findByCountryAndUser(country, user);

      if ((loc == null) || !loc.isEnabled()) {
        return this.createNewLocationToken(country, user);
      }
    }
    catch (final Exception e) {
      return null;
    }

    return null;
  }

  @Override
  public String generateQRUrl(User user) throws UnsupportedEncodingException {
    return Defaults.QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", Defaults.APP_NAME, user.getEmail(), user.getSecret(), Defaults.APP_NAME), "UTF-8");
  }

  @Override
  public User updateUserTwoFactorAuthentication(boolean useTwoFactorAuthentication) {
    final Authentication curAuth = SecurityContextHolder.getContext().getAuthentication();

    User currentUser = (User) curAuth.getPrincipal();

    currentUser.setUsingTwoFactorAuthentication(useTwoFactorAuthentication);
    currentUser = this.userRepository.save(currentUser);

    final Authentication auth = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(), curAuth.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);

    return currentUser;
  }

  private boolean emailExists(final String email) {
    return this.userRepository.findByEmail(email) != null;
  }

  private NewLocationToken createNewLocationToken(String country, User user) {
    UserLocation loc = new UserLocation(country, user);
    loc = this.userLocationRepository.save(loc);

    final NewLocationToken token = new NewLocationToken(UUID.randomUUID().toString(), loc);

    return this.newLocationTokenRepository.save(token);
  }

  @Override
  public void changeUserAddress(User user, String streetAddress, String city, String state, String zipCode) {
    Address address = this.addressRepository.findByUser(user);

    if (address == null) {
      address = new Address(streetAddress, city, state, zipCode);

      address = this.addressRepository.save(address);
    }
    else {
      address.setStreetAddress(streetAddress);
      address.setCity(city);
      address.setState(state);
      address.setZipCode(zipCode);

      address = this.addressRepository.save(address);
    }

    user.setAddress(address);

    this.userRepository.save(user);
  }

  @Override
  public void changeUserCreditCard(User user, String creditCardHolderName, String creditCardNumber, String creditCardExpirationDate, String creditCardCVV) {
    CreditCard creditCard = this.creditCardRepository.findByUser(user);

    if (creditCard == null) {
      creditCard = new CreditCard(creditCardHolderName, creditCardNumber, creditCardExpirationDate, creditCardCVV);

      creditCard = this.creditCardRepository.save(creditCard);
    }
    else {
      creditCard.setCardHolderName(creditCardHolderName);
      creditCard.setCreditCardNumber(creditCardNumber);
      creditCard.setExpirationDate(creditCardExpirationDate);
      creditCard.setCvv(creditCardCVV);

      creditCard = this.creditCardRepository.save(creditCard);
    }

    user.setCreditCard(creditCard);

    this.userRepository.save(user);
  }

  @Override
  public void addItemToCart(User user, Item item) {
    Cart cart = this.addToCart(user, item, 1);

    cart = this.cartRepository.save(cart);

    user.setCart(cart);

    this.userRepository.save(user);
  }

  @Override
  public void saveRegisteredUser(User user) {
    this.userRepository.save(user);
  }

  private Cart addToCart(User user, Item item, int quantity) {
    CartItem cartItem = this.cartItemRepository.findByItemAndQuantity(item, quantity);
    Cart cart = this.cartRepository.findByUser(user);

    if (cartItem == null) {
      cartItem = new CartItem();

      cartItem.setQuantity(quantity);

      cartItem = this.cartItemRepository.save(cartItem);
    }

    if (!cart.isItemInCart(cartItem)) {
      cart.addItem(cartItem);
      return cart;
    }
    else {
      return this.addToCart(user, item, quantity + 1);
    }
  }

  @Override
  public void deleteUser(User user) {
    final VerificationToken verificationToken = this.verificationTokenRepository.findByUser(user);

    if (verificationToken != null) {
      this.verificationTokenRepository.delete(verificationToken);
    }

    final PasswordResetToken passwordToken = this.passwordTokenRepository.findByUser(user);

    if (passwordToken != null) {
      this.passwordTokenRepository.delete(passwordToken);
    }

    this.userRepository.delete(user);
  }

  @Override
  public VerificationToken getVerificationToken(String VerificationToken) {
    return this.verificationTokenRepository.findByToken(VerificationToken);
  }

  @Override
  public PasswordResetToken getPasswordResetToken(String token) {
    return this.passwordTokenRepository.findByToken(token);
  }

  @Override
  public Optional<User> getUserByID(long id) {
    return this.userRepository.findById(id);
  }

  @Override
  public String isValidNewLocationToken(String token) {
    final NewLocationToken locToken = this.newLocationTokenRepository.findByToken(token);

    if (locToken == null) {
      return null;
    }

    UserLocation userLoc = locToken.getUserLocation();

    userLoc.setEnabled(true);
    userLoc = this.userLocationRepository.save(userLoc);

    this.newLocationTokenRepository.delete(locToken);

    return userLoc.getCountry();
  }

  @Override
  public void addUserLocation(User user, String ip) {
    try {
      final InetAddress ipAddress = InetAddress.getByName(ip);
      final String country = this.databaseReader.country(ipAddress).getCountry().getName();

      UserLocation loc = new UserLocation(country, user);
      loc.setEnabled(true);

      this.userLocationRepository.save(loc);
    }
    catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Checks if the given verification code from the user is valid or not.
   *
   * @param code the code
   * @return if the code is valid, returns true otherwise returns false
   */
  private boolean isValidLong(String code) {
    try {
      Long.parseLong(code);
    }
    catch (final NumberFormatException e) {
      return false;
    }

    return true;
  }
}
