package by.kaziulin.InternetShop.service;

import by.kaziulin.InternetShop.dao.ProductRepository;
import by.kaziulin.InternetShop.dto.ProductDTO;
import by.kaziulin.InternetShop.entity.Bucket;
import by.kaziulin.InternetShop.entity.Product;
import by.kaziulin.InternetShop.entity.User;
import by.kaziulin.InternetShop.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductMapper productMapper = ProductMapper.MAPPER;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;
    private final SimpMessagingTemplate template;
@Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService, SimpMessagingTemplate template) {
        this.productRepository = productRepository;
    this.userService = userService;
    this.bucketService = bucketService;
    this.template = template;
}

    @Override
    public List<ProductDTO> getAll() {
    return productMapper.fromProductList(productRepository.findAll());
    }

    @Override
    @Transactional
    public void addToUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if (user == null) {
            throw new RuntimeException("User not found - " + username);
        }
        Bucket bucket = user.getBucket();
        if (bucket == null) {
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);
        } else {
            bucketService.addProducts(bucket, Collections.singletonList(productId));
        }
    }

    @Override
    @Transactional
    public void addProduct(ProductDTO dto) {

        Product product = productMapper.toProduct(dto);
        Product savedProduct = productRepository.save(product);
        template.convertAndSend("/topic/products", ProductMapper.MAPPER.fromProduct(savedProduct));
    }
}
