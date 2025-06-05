package vn.hoangdev.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoangdev.laptopshop.domain.User;
import vn.hoangdev.laptopshop.service.UploadService;
import vn.hoangdev.laptopshop.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;
    
    public UserController(UserService userService, UploadService uploadService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }
    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUsersByEmail("kevin@gmail.com");
        System.out.println(arrUsers);
        model.addAttribute("hoang","test");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/show";
    }

    // @RequestMapping("/admin/user")
    // public String getUserPage(Model model,
    //         @RequestParam("page") Optional<String> pageOptional) {
    //     int page = 1;
    //     try {
    //         if (pageOptional.isPresent()) {
    //             // convert from String to int
    //             page = Integer.parseInt(pageOptional.get());
    //         } else {
    //             // page = 1
    //         }
    //     } catch (Exception e) {
    //         // page = 1
    //         // TODO: handle exception
    //     }

    //     Pageable pageable = PageRequest.of(page - 1, 1);
    //     Page<User> usersPage = this.userService.getAllUsers(pageable);
    //     List<User> users = usersPage.getContent();
    //     model.addAttribute("users1", users);

    //     model.addAttribute("currentPage", page);
    //     model.addAttribute("totalPages", usersPage.getTotalPages());
    //     return "admin/user/show";
    // }


    // Button View
    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser",new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(Model model, @ModelAttribute("newUser") @Valid User hoangdev, BindingResult newUserBindingResult, @RequestParam("hoangFile") MultipartFile file) {
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + " - " +
            error.getDefaultMessage());
        }

        // validate
        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }
        
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashedPassword = this.passwordEncoder.encode(hoangdev.getPassword());
        
        hoangdev.setAvatar(avatar);
        hoangdev.setPassword(hashedPassword);
        hoangdev.setRole(this.userService.getRoleByName(hoangdev.getRole().getName())); // Default role
        this.userService.handleSaveUser(hoangdev);
        return "redirect:/admin/user";
    }

    // Button Update
    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User hoangdev) {
        User currentUser = this.userService.getUserById(hoangdev.getId());
        if (currentUser != null) {
            currentUser.setFullName(hoangdev.getFullName());
            currentUser.setPhone(hoangdev.getPhone());
            currentUser.setAddress(hoangdev.getAddress());
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    // Button Delete
    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // User user = new User();
        // user.setId(id);
        model.addAttribute("newUser",new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User hoangdev) {
        this.userService.deleteAUser(hoangdev.getId());
        return "redirect:/admin/user";
    }
}
