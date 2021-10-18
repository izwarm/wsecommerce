package com.ws.ecommerce.repository;

import com.ws.ecommerce.model.Buyers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BuyersRepository extends JpaRepository<Buyers, Long> {

}
