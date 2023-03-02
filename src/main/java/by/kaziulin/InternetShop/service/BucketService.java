package by.kaziulin.InternetShop.service;

import by.kaziulin.InternetShop.dto.BucketDTO;
import by.kaziulin.InternetShop.entity.Bucket;
import by.kaziulin.InternetShop.entity.User;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);

    void addProducts(Bucket bucket, List<Long> productIds);
    BucketDTO getBucketByUser(String name);
}
