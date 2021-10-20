package com.ws.ecommerce.repository;

import com.ws.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByItemId(Long itemId);
    List<Cart> findByBuyersId(Long buyersId);
    List<Cart> findByIdIn(List<Long> cartIds);
}
