package vn.hoangdev.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoangdev.laptopshop.domain.User;
import vn.hoangdev.laptopshop.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
      
    }
    @RequestMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("hoang","test");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        model.addAttribute("newUser",new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create1", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User hoangdev) {
        System.out.println("run here" + hoangdev);
        this.userService.handleSaveUser(hoangdev);
        return "hello";
    }
}


