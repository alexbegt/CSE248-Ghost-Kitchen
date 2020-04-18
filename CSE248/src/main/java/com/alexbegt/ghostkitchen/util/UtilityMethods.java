package com.alexbegt.ghostkitchen.util;

import javax.servlet.http.HttpServletRequest;

public class UtilityMethods {

  /**
   * Get's the client ip
   *
   * @return the client ip
   */
  public static String getClientIP(HttpServletRequest request) {
    final String xfHeader = request.getHeader("X-Forwarded-For");

    if (xfHeader != null) {
      return xfHeader.split(",")[0];
    }

    return request.getRemoteAddr();

    // return "128.101.101.101"; // for testing United States
    // return "41.238.0.198"; // for testing Egypt
  }
}
