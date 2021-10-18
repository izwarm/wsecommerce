package com.ws.ecommerce.controller;

import com.ws.ecommerce.model.Item;
import com.ws.ecommerce.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/")
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @PostMapping("/")
    public Item tambahitem(@Valid @RequestBody Item item) {
        return itemRepository.save(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable(value = "id") Long id,
                                           @Valid @RequestBody Item detailitem) {
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Item item1 = item.get();
        if (detailitem.getItemName() != null) {
            item1.setItemName(detailitem.getItemName());
        }
        if (detailitem.getPrice() != 0) {
            item1.setPrice(detailitem.getPrice());
        }
        if (detailitem.getQuantity() != 0) {
            item1.setQuantity(detailitem.getQuantity());
        }
        Item updateItem = itemRepository.save(item1);
        return ResponseEntity.ok(updateItem);
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable(value = "id") Long id) {
        Optional<Item> item = itemRepository.findById(id);
        String result = "";
        if (!item.isPresent()){
            result = "id "+id+" berhasil dihapus";
            return result;
        }
        result = "id " + id + " berhasil di hapus";
        itemRepository.deleteById(id);
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable(value = "id") Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent())
            return ResponseEntity.notFound().build();
        Item item1 = item.get();
        return ResponseEntity.ok().body(item1);
    }
}
