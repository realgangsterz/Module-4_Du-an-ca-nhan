package com.codegym.controller;

import com.codegym.model.Role;
import com.codegym.model.User;
import com.codegym.service.RoleService;
import com.codegym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String home(){
        return "home";
    }

    @ModelAttribute("user")
    public User setUpUserForm() {
        return new User();
    }


//    @PostMapping("doLogin")
//    public ModelAndView doLogin(){
//        ModelAndView modelAndView = new ModelAndView("")
//        return
//    }
    @ModelAttribute("roles")
    public Iterable<Role> roles(){
        Iterable<Role> roles = roleService.findAll();
        return roles;
}

    @GetMapping("signup")
    public ModelAndView showFormSignUp() {
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("validateUser")
    public ModelAndView checkValidation(@Validated @ModelAttribute("user") User user, BindingResult bindingResult) {
        new User().validate(user, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("signup");
            return modelAndView;
        }
        user.setRole(roleService.findById(2l));
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("message","Successful!");
        return modelAndView;
    }

}
