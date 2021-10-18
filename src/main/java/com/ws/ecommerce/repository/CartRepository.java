package com.ws.ecommerce.repository;

import com.ws.ecommerce.model.Buyers;
import com.ws.ecommerce.model.Cart;
import com.ws.ecommerce.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByItemId (Long itemID);
    List<Cart> findByBuyersId(Long buyersId);
}
