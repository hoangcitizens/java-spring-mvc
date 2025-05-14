package vn.hoangdev.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoangdev.laptopshop.domain.User;
import vn.hoangdev.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }
    public User handleSaveUser(User user) {
        User hoangdev = this.userRepository.save(user);
        System.out.println(hoangdev);
        return hoangdev;
    }
}
