package com.alexbegt.ghostkitchen.service;

import com.alexbegt.ghostkitchen.persistence.model.device.NewLocationToken;
import com.alexbegt.ghostkitchen.persistence.model.user.address.Address;
import com.alexbegt.ghostkitchen.persistence.model.user.token.PasswordResetToken;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.persistence.model.user.token.VerificationToken;
import com.alexbegt.ghostkitchen.web.dto.user.UserDto;
import com.alexbegt.ghostkitchen.web.error.UserAlreadyExistException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface IUserService {

  User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

  User getUser(String verificationToken);

  void createVerificationTokenForUser(User user, String token);

  VerificationToken generateNewVerificationToken(String token);

  void createPasswordResetTokenForUser(User user, String token);

  User findUserByEmail(String email);

  Optional<User> getUserByPasswordResetToken(String token);

  void changeUserPassword(User user, String password);

  boolean checkIfValidOldPassword(User user, String password);

  String validateVerificationToken(String token);

  List<String> getUsersFromSessionRegistry();

  NewLocationToken isNewLoginLocation(String username, String ip);

  String generateQRUrl(User user) throws UnsupportedEncodingException;

  User updateUserTwoFactorAuthentication(boolean useTwoFactorAuthentication);

  void changeUserAddress(User user, String streetAddress, String city, String state, String zipCode);

  void changeUserCreditCard(User user, String creditCardHolderName, String creditCardNumber, String creditCardExpirationDate, String creditCardCVV);

  void saveRegisteredUser(User user);

  void deleteUser(User user);

  VerificationToken getVerificationToken(String VerificationToken);

  PasswordResetToken getPasswordResetToken(String token);

  Optional<User> getUserByID(long id);

  String isValidNewLocationToken(String token);

  void addUserLocation(User user, String ip);
}
