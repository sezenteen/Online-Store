package com.example.onlineshop.repository;

import com.example.onlineshop.model.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
    @Modifying
    @Query("DELETE FROM OrderedProduct op WHERE op.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);

}
