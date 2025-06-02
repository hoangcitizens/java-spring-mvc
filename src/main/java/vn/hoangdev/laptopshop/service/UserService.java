package vn.hoangdev.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoangdev.laptopshop.domain.Role;
import vn.hoangdev.laptopshop.domain.User;
import vn.hoangdev.laptopshop.repository.RoleRepository;
import vn.hoangdev.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void deleteAUser(long id) {
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }
}
