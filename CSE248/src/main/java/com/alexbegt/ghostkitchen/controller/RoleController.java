package com.alexbegt.ghostkitchen.controller;

import com.alexbegt.ghostkitchen.model.Role;
import com.alexbegt.ghostkitchen.service.role.RoleServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {

  private final RoleServiceImpl roleService;

  @GetMapping("/role-creation-form")
  public ModelAndView registerForm() {
    Role role = new Role();
    ModelAndView modelAndView = new ModelAndView("role_creation_form");
    modelAndView.addObject("role", role);

    return modelAndView;
  }

  @PostMapping("/role_creation")
  public RedirectView userRegistration(@ModelAttribute("role") Role role) {
    roleService.createRole(role);

    return new RedirectView("/home");
  }

}
