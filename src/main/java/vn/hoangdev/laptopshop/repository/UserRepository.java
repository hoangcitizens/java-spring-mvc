package vn.hoangdev.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoangdev.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User hoangdev);

    void deleteById(long id);

    List<User> findOneByEmail(String email);

    List<User> findAll();

    User findById(long id);

}
