package com.alexbegt.ghostkitchen.security.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component("myLogoutSuccessHandler")
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

  /**
   * When the user goes to logout, handles the success
   *
   * @param request the request
   * @param response the response
   * @param authentication the authentication
   * @throws IOException if there was a file error
   * @throws ServletException if there was a servlet error
   */
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    final HttpSession session = request.getSession();

    if (session != null) {
      session.removeAttribute("user");
    }

    response.sendRedirect("/login?loggedOut");
  }
}
