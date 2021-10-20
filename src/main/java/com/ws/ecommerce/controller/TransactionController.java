package com.ws.ecommerce.controller;

import com.ws.ecommerce.model.Cart;
import com.ws.ecommerce.model.Item;
import com.ws.ecommerce.model.Transaction;
import com.ws.ecommerce.repository.CartRepository;
import com.ws.ecommerce.repository.ItemRepository;
import com.ws.ecommerce.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CartRepository cartRepository;

    @GetMapping("/")
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @PostMapping("/")
    public Transaction tambahtransaction(@Valid @RequestBody Transaction transaction) {
        List<Cart> carts = cartRepository.findByIdIn(transaction.getCartIds());
        carts = carts.stream()
                .filter(a -> Objects.equals(a.getBuyersId(), transaction.getBuyersId()))
                .collect(Collectors.toList());
        if (!carts.isEmpty()) {
            double totalPrice = 0d;
            List<String> itemsName = new ArrayList<>();
            List<String> cartId = new ArrayList<>();
            for (Cart cart1 : carts) {
                Optional<Item> item = itemRepository.findById(cart1.getItemId());
                if (item.isPresent()) {
                    Item item1 = item.get();
                    int sisaItem = item1.getQuantity() - cart1.getQuantity();
                    item1.setQuantity(sisaItem);
                    itemRepository.save(item1);
                    totalPrice += cart1.getTotalPrice();
                    itemsName.add(item1.getItemName());
                    cartId.add(String.valueOf(cart1.getId()));
                }
                cartRepository.deleteById(cart1.getId());
            }
            transaction.setItemName(String.join(",", itemsName));
            transaction.setCartId(String.join(",", cartId));
            transaction.setTotalPrice(totalPrice);
        }
        return transactionRepository.save(transaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable(value = "id") Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (!transaction.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Transaction transaction1 = transaction.get();
        return ResponseEntity.ok().body(transaction1);
    }

    @GetMapping("/buyers/{id}")
    public List<Transaction> getAllTransactionByBuyersId(@PathVariable(value = "id") Long id) {
        return transactionRepository.findByBuyersId(id);

    }

    @GetMapping("/item/{itemName}")
    public List<Transaction> getAllTransactionByItemName(@PathVariable(value = "itemName") String itemName) {
        return transactionRepository.findByItemName(itemName);
    }
}