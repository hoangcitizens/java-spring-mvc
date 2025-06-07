package vn.hoangdev.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoangdev.laptopshop.domain.Cart;
import vn.hoangdev.laptopshop.domain.CartDetail;
import vn.hoangdev.laptopshop.domain.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    // Kiểm tra xem CartDetail có tồn tại theo Cart và Product hay không
    // Trả về true nếu tồn tại, ngược lại trả về false
    boolean existsByCartAndProduct(Cart cart, Product product);
    // Tìm kiếm CartDetail theo Cart và Product
    // Trả về CartDetail nếu tồn tại, ngược lại trả về null
    CartDetail findByCartAndProduct(Cart cart, Product product);
}
