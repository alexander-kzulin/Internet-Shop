package by.kaziulin.InternetShop.service;

import by.kaziulin.InternetShop.dao.BucketRepository;
import by.kaziulin.InternetShop.dao.ProductRepository;
import by.kaziulin.InternetShop.dto.BucketDTO;
import by.kaziulin.InternetShop.dto.BucketDetailDTO;
import by.kaziulin.InternetShop.entity.Bucket;
import by.kaziulin.InternetShop.entity.Product;
import by.kaziulin.InternetShop.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public BucketServiceImpl(BucketRepository bucketRepository, ProductRepository productRepository, UserService userService) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Bucket createBucket(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        bucket.setProducts(productList);
        return bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getOne)
                .collect(Collectors.toList());

    }

    @Override
    public void addProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductsList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductsList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProductsList);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDTO getBucketByUser(String name) {
        User user = userService.findByName(name);
        if (user == null || user.getBucket() == null) {
            return new BucketDTO();
        }
        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getBucket().getProducts();
        for (Product product : products) {
            BucketDetailDTO detailDTO = mapByProductId.get(product.getId());
            if (detailDTO == null) {
                mapByProductId.put(product.getId(), new BucketDetailDTO(product));
            } else {
                detailDTO.setAmount(detailDTO.getAmount().add(new BigDecimal("1.0")));
                detailDTO.setSum(detailDTO.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }
        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate();
        return bucketDTO;
    }
}
