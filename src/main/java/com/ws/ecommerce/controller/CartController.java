package com.ws.ecommerce.controller;

import com.ws.ecommerce.model.Cart;
import com.ws.ecommerce.model.Item;
import com.ws.ecommerce.repository.CartRepository;

import com.ws.ecommerce.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/")
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    @PostMapping("/")
    public Cart tambahcart(@Valid @RequestBody Cart cart) {
        Optional<Item> item = itemRepository.findById(cart.getItemId());
        if (item.isPresent()){
            Item item1 = item.get();
            double totalPrice = cart.getQuantity() * item1.getPrice();
            cart.setTotalPrice(totalPrice);
            itemRepository.save(item1);
        }
        return cartRepository.save(cart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable(value = "id") Long id,
                                           @Valid @RequestBody Cart detailcart) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (!cart.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Cart cart1 = cart.get();
        if (detailcart.getBuyersId() != null) {
            cart1.setBuyersId(detailcart.getBuyersId());
        }
        if (detailcart.getItemId() != null) {
            cart1.setItemId(detailcart.getItemId());
        }
        if (detailcart.getQuantity() != 0) {
            cart1.setQuantity(detailcart.getQuantity());
        }

        Cart updateCart = cartRepository.save(cart1);
        return ResponseEntity.ok(updateCart);
    }

    @DeleteMapping("/{id}")
    public String deleteCart(@PathVariable(value = "id") Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        String result = "";
        if (!cart.isPresent()) {
            result = "id " + id + " tidak ditemukan";
            return result;
        }
        result = "id " + id + " berhasil di hapus";
        cartRepository.deleteById(id);
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable(value = "id") Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (!cart.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Cart cart1 = cart.get();
        return ResponseEntity.ok().body(cart1);
    }

    @GetMapping("/item/{id}")
    public List<Cart> getAllCartByItemId(@PathVariable(value = "id") Long id) {
        return cartRepository.findByItemId(id);
    }

    @GetMapping("/buyers/{id}")
    public List<Cart> getAllCartByBuyersId(@PathVariable(value = "id") Long id) {
        return cartRepository.findByBuyersId(id);
    }
}
