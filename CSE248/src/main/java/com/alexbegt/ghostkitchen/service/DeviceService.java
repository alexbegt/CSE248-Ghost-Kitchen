package com.alexbegt.ghostkitchen.service;

import com.alexbegt.ghostkitchen.geoip2.CityDatabaseReader;
import com.alexbegt.ghostkitchen.persistence.dao.device.DeviceMetadataRepository;
import com.alexbegt.ghostkitchen.persistence.model.device.DeviceMetadata;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.util.Defaults;
import com.google.common.base.Strings;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Component
public class DeviceService {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private static final String UNKNOWN = "UNKNOWN";

  @Value("${support.email}")
  private String from;

  private final DeviceMetadataRepository deviceMetadataRepository;
  private final CityDatabaseReader cityDatabaseReader;
  private final Parser parser;
  private final JavaMailSender mailSender;
  private final MessageSource messages;

  public DeviceService(DeviceMetadataRepository deviceMetadataRepository, CityDatabaseReader cityDatabaseReader, Parser parser, JavaMailSender mailSender, MessageSource messages) {
    this.deviceMetadataRepository = deviceMetadataRepository;
    this.cityDatabaseReader = cityDatabaseReader;
    this.parser = parser;
    this.mailSender = mailSender;
    this.messages = messages;
  }

  /**
   * Verifies the device being used to sign into the given user's account
   *
   * Adds a device details to the db if one doesn't exist and sends an email to the user otherwise updates the last login date to today's date.
   *
   * @param user the user being logged into
   * @param request the http request
   * @throws IOException if there is a file error
   * @throws GeoIp2Exception if geoip cannot process the users ip
   */
  public void verifyDevice(User user, HttpServletRequest request) throws IOException, GeoIp2Exception {
    String ip = this.extractIp(request);
    String location = this.getIpLocation(ip);

    String deviceDetails = this.getDeviceDetails(request.getHeader("user-agent"));

    DeviceMetadata existingDevice = this.findExistingDevice(user.getId(), deviceDetails, location);

    if (Objects.isNull(existingDevice)) {
      if(!user.getEmail().equalsIgnoreCase(Defaults.ADMIN_EMAIL)){
        this.unknownDeviceNotification(deviceDetails, location, ip, user.getEmail(), request.getLocale());
      }

      DeviceMetadata deviceMetadata = new DeviceMetadata();
      deviceMetadata.setUserId(user.getId());
      deviceMetadata.setLocation(location);
      deviceMetadata.setDeviceDetails(deviceDetails);
      deviceMetadata.setLastLoggedIn(new Date());

      this.deviceMetadataRepository.save(deviceMetadata);
    }
    else {
      existingDevice.setLastLoggedIn(new Date());

      this.deviceMetadataRepository.save(existingDevice);
    }
  }

  /**
   * Extracts the ip from the http request
   *
   * @param request the http request
   * @return the ip from the request
   */
  private String extractIp(HttpServletRequest request) {
    String clientIp;
    String clientXForwardedForIp = request.getHeader("x-forwarded-for");

    if (nonNull(clientXForwardedForIp)) {
      clientIp = this.parseXForwardedHeader(clientXForwardedForIp);
    }
    else {
      clientIp = request.getRemoteAddr();
    }

    return clientIp;
  }

  /**
   * Parses the passed header
   *
   * @param header the header
   * @return the parsed header
   */
  private String parseXForwardedHeader(String header) {
    return header.split(" *, *")[0];
  }

  /**
   * Generates the device details from the user agent.
   *
   * @param userAgent The user agent
   *
   * @return the device details from the client
   */
  private String getDeviceDetails(String userAgent) {
    String deviceDetails = UNKNOWN;

    Client client = this.parser.parse(userAgent);

    if (Objects.nonNull(client)) {
      deviceDetails = client.userAgent.family + " " + client.userAgent.major + "." + client.userAgent.minor + " - " + client.os.family + " " + client.os.major + "." + client.os.minor;
    }

    return deviceDetails;
  }

  /**
   * Gets the location from the ip
   *
   * @param ip the users ip
   *
   * @return The ip locaton
   *
   * @throws IOException if the GeoIp2 fails to load the file
   * @throws GeoIp2Exception if GeoIp2 fails to find a location based on the ip
   */
  private String getIpLocation(String ip) throws IOException, GeoIp2Exception {
    String location = UNKNOWN;

    InetAddress ipAddress = InetAddress.getByName(ip);

    CityResponse cityResponse = this.cityDatabaseReader.city(ipAddress);

    if (Objects.nonNull(cityResponse) && Objects.nonNull(cityResponse.getCity()) && !Strings.isNullOrEmpty(cityResponse.getCity().getName())) {
      location = cityResponse.getCity().getName();
    }

    return location;
  }

  /**
   * Finds an existing device details if one exists for the given location and details.
   *
   * @param userId the user to look up
   * @param deviceDetails the device details to compare
   * @param location the location to compare
   * @return
   */
  private DeviceMetadata findExistingDevice(Long userId, String deviceDetails, String location) {
    List<DeviceMetadata> knownDevices = this.deviceMetadataRepository.findByUserId(userId);

    for (DeviceMetadata existingDevice : knownDevices) {
      if (existingDevice.getDeviceDetails().equals(deviceDetails) && existingDevice.getLocation().equals(location)) {
        return existingDevice;
      }
    }

    return null;
  }

  /**
   * Sends a email to the user showing the unknown device details
   *
   * @param deviceDetails The Device details
   * @param location the location the request came from
   * @param ip the ip of the request
   * @param email the email to send to
   * @param locale the language being used
   */
  private void unknownDeviceNotification(String deviceDetails, String location, String ip, String email, Locale locale) {
    final String subject = "New Login Notification";
    final SimpleMailMessage notification = new SimpleMailMessage();

    notification.setTo(email);
    notification.setSubject(subject);

    String text = getMessage("message.login.notification.deviceDetails", locale) +
      " " + deviceDetails +
      "\n" +
      getMessage("message.login.notification.location", locale) +
      " " + location +
      "\n" +
      getMessage("message.login.notification.ip", locale) +
      " " + ip;

    notification.setText(text);
    notification.setFrom(from);

    this.mailSender.send(notification);
  }

  /**
   * Get the message with a provided locale. If not found, try default {@link Locale#ENGLISH}.
   *
   * @param code   Message code
   * @param locale Request's locale
   * @return Retrieved message
   */
  private String getMessage(String code, Locale locale) {
    try {
      return this.messages.getMessage(code, null, locale);
    }
    catch (NoSuchMessageException ex) {
      return this.messages.getMessage(code, null, Locale.ENGLISH);
    }
  }
}
