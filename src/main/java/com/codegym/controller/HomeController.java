package com.codegym.controller;

import com.codegym.model.User;
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

    @GetMapping
    public String home(){
        return "home";
    }

    @ModelAttribute("user")
    public User setUpUserForm() {
        return new User();
    }

    @GetMapping("login")
    public String showFormLogin(@CookieValue(value = "setUser", defaultValue = "") String setUser, Model model) {
        Cookie cookie = new Cookie("setUser", setUser);
        model.addAttribute("cookieValue", cookie);
        return "login";
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
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("message","Bạn đã đăng ký thành công !, mời đăng nhập !");
        userService.save(user);
        return modelAndView;
    }

}
