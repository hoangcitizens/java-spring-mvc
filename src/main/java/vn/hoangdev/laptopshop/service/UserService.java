package vn.hoangdev.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoangdev.laptopshop.domain.Role;
import vn.hoangdev.laptopshop.domain.User;
import vn.hoangdev.laptopshop.domain.dto.RegisterDTO;
import vn.hoangdev.laptopshop.repository.OrderRepository;
import vn.hoangdev.laptopshop.repository.ProductRepository;
import vn.hoangdev.laptopshop.repository.RoleRepository;
import vn.hoangdev.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository,
            RoleRepository roleRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    // public Page<User> getAllUsers(Pageable page) {
    //     return this.userRepository.findAll(page);
    // }

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

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public long countUsers() {
        return this.userRepository.count();
    }

    public long countProducts() {
        return this.productRepository.count();
    }

    public long countOrders() {
        return this.orderRepository.count();
    }
}

