package by.kaziulin.InternetShop.dao;


import by.kaziulin.InternetShop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
