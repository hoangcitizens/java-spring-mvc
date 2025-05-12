package vn.hoangdev.laptopshop.service;

import org.springframework.stereotype.Service;

import vn.hoangdev.laptopshop.domain.User;
import vn.hoangdev.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User handleSaveUser(User user) {
        User hoangdev = this.userRepository.save(user);
        System.out.println(hoangdev);
        return hoangdev;
    }
}
