package by.kaziulin.InternetShop.service;

import by.kaziulin.InternetShop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
    void addToUserBucket(Long productId, String username);

    void addProduct(ProductDTO dto);
}
