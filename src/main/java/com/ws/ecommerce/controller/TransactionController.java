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
import java.util.List;
import java.util.Optional;

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
        Optional<Cart> cart = cartRepository.findById(transaction.getCartId());
        if (cart.isPresent()) {
            Cart cart1 = cart.get();
            Optional<Item> item = itemRepository.findById(cart1.getItemId());
            if (item.isPresent()) {
                Item item1 = item.get();
                int sisaItem = item1.getQuantity() - cart1.getQuantity();

                item1.setQuantity(sisaItem);
                itemRepository.save(item1);

                double totalPrice = cart1.getQuantity() * item1.getPrice();
                transaction.setTotalPrice(totalPrice);
                transaction.setBuyersId(cart1.getBuyersId());
                transaction.setItemName(item1.getItemName());
                transaction.setQuantity(cart1.getQuantity());
            }
            cartRepository.deleteById(cart1.getId());
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
    public List<Transaction> getAllTransactinByBuyersId(@PathVariable(value = "id") Long id) {
        return transactionRepository.findByBuyersId(id);

    }

    @GetMapping("/item/{itemName}")
    public List<Transaction> getAllTransactionByItemName(@PathVariable(value = "itemName") String itemName) {
        return transactionRepository.findByItemName(itemName);
    }
}