package com.alexbegt.ghostkitchen.controller;

import com.alexbegt.ghostkitchen.entity.RoleEntity;
import com.alexbegt.ghostkitchen.model.User;
import com.alexbegt.ghostkitchen.service.RoleServiceImpl;
import com.alexbegt.ghostkitchen.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashSet;
import java.util.Set;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private final UserServiceImpl userService;
  private final RoleServiceImpl roleService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;


  @GetMapping("/")
  public String welcome(){
    return "welcome";
  }

  @GetMapping("/registration-form")
  public ModelAndView registerForm(){
    User user = new User();
    ModelAndView modelAndView = new ModelAndView("registration_form");
    modelAndView.addObject("user", user);

    return modelAndView;
  }

  @PostMapping("/registration")
  public RedirectView userRegistration(@ModelAttribute("user") User user){
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    Set<RoleEntity> roles = new HashSet<>();
    roles.add(roleService.getRoleById(2L));
    user.setRoles(roles);
    userService.createUser(user);

    return new RedirectView("/home");
  }

  @GetMapping("/home")
  public String home(){
    return "home";
  }

  @PostMapping("/home")
  public String postHome(){
    return "home";
  }

  @GetMapping("/logout")
  public String logout(){
    return "logout";
  }
}
