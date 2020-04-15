package com.alexbegt.ghostkitchen.service;

import com.alexbegt.ghostkitchen.persistence.dao.device.NewLocationTokenRepository;
import com.alexbegt.ghostkitchen.persistence.dao.device.UserLocationRepository;
import com.alexbegt.ghostkitchen.persistence.dao.role.RoleRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.PasswordResetTokenRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.UserRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.VerificationTokenRepository;
import com.alexbegt.ghostkitchen.persistence.model.device.NewLocationToken;
import com.alexbegt.ghostkitchen.persistence.model.device.UserLocation;
import com.alexbegt.ghostkitchen.persistence.model.user.PasswordResetToken;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.persistence.model.user.VerificationToken;
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
  private VerificationTokenRepository tokenRepository;

  @Autowired
  private PasswordResetTokenRepository passwordTokenRepository;

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

  public static final String TOKEN_INVALID = "invalidToken";
  public static final String TOKEN_EXPIRED = "expired";
  public static final String TOKEN_VALID = "valid";

  public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
  public static String APP_NAME = "GhostKitchen";

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
    user.setUsingTwoFactorAuthentication(accountDto.isUsingTwoFactorAuthentication());
    user.setRoles(Collections.singletonList(this.roleRepository.findByName(Defaults.USER_ROLE)));

    return this.userRepository.save(user);
  }

  @Override
  public User getUser(String verificationToken) {
    final VerificationToken token = this.tokenRepository.findByToken(verificationToken);

    if (token != null) {
      return token.getUser();
    }

    return null;
  }

  @Override
  public void createVerificationTokenForUser(User user, String token) {
    final VerificationToken myToken = new VerificationToken(token, user);

    this.tokenRepository.save(myToken);
  }

  @Override
  public VerificationToken generateNewVerificationToken(String token) {
    VerificationToken newVerificationToken = this.tokenRepository.findByToken(token);

    newVerificationToken.updateToken(UUID.randomUUID().toString());
    newVerificationToken = this.tokenRepository.save(newVerificationToken);

    return newVerificationToken;
  }

  @Override
  public void createPasswordResetTokenForUser(User user, String token) {
    final PasswordResetToken myToken = new PasswordResetToken(token, user);

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
    final VerificationToken verificationToken = this.tokenRepository.findByToken(token);

    if (verificationToken == null) {
      return TOKEN_INVALID;
    }

    final User user = verificationToken.getUser();

    final Calendar cal = Calendar.getInstance();

    if ((verificationToken.getExpirationDate().getTime() - cal.getTime().getTime()) <= 0) {
      this.tokenRepository.delete(verificationToken);
      return TOKEN_EXPIRED;
    }

    user.setEnabled(true);

    this.userRepository.save(user);

    return TOKEN_VALID;
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
    if (username.equalsIgnoreCase(Defaults.ADMIN_EMAIL)) { // Don't send emails to the admin user.
      return null;
    }

    try {
      final InetAddress ipAddress = InetAddress.getByName(ip);
      final String country = this.databaseReader.country(ipAddress).getCountry().getName();

      System.out.println(country + "====****");
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
    return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getEmail(), user.getSecret(), APP_NAME), "UTF-8");
  }

  @Override
  public User updateUserTwoFactorAuthentication(boolean useTwoFactorAuthentication) {
    final Authentication curAuth = SecurityContextHolder.getContext().getAuthentication();

    User currentUser = (User) curAuth.getPrincipal();

    currentUser.setUsingTwoFactorAuthentication(useTwoFactorAuthentication);
    currentUser = userRepository.save(currentUser);

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

  // TODO IMPLEMENT TESTS USING METHODS BELOW

  @Override
  public void saveRegisteredUser(User user) {
    this.userRepository.save(user);
  }

  @Override
  public void deleteUser(User user) {
    final VerificationToken verificationToken = this.tokenRepository.findByUser(user);

    if (verificationToken != null) {
      this.tokenRepository.delete(verificationToken);
    }

    final PasswordResetToken passwordToken = this.passwordTokenRepository.findByUser(user);

    if (passwordToken != null) {
      this.passwordTokenRepository.delete(passwordToken);
    }

    this.userRepository.delete(user);
  }

  @Override
  public VerificationToken getVerificationToken(String VerificationToken) {
    return this.tokenRepository.findByToken(VerificationToken);
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
}
