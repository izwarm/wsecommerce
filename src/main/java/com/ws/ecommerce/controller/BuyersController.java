package com.ws.ecommerce.controller;

import com.ws.ecommerce.model.Buyers;
import com.ws.ecommerce.repository.BuyersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/buyers")
public class BuyersController {

    @Autowired
    BuyersRepository buyersRepository;

    @GetMapping("/")
    public List<Buyers> getAll() {

        return buyersRepository.findAll();
    }

    @PostMapping("/")
    public Buyers tambahbuyers(@Validated @RequestBody Buyers buyers) {
        return buyersRepository.save(buyers);
    }

    @PutMapping("/{buyersId}")
    public ResponseEntity<Buyers> updateBuyers(@PathVariable(value = "buyersId") Long id,
                                               @Valid @RequestBody Buyers detailbuyers) {
        Optional<Buyers> buyers = buyersRepository.findById(id);
        if (!buyers.isPresent())
            return ResponseEntity.notFound().build();
        Buyers buyers1 = buyers.get();
        if (detailbuyers.getName() != null) {
            buyers1.setName(detailbuyers.getName());
        }
        if (detailbuyers.getAddress() != null) {
            buyers1.setAddress(detailbuyers.getAddress());
        }
        if (detailbuyers.getCity() != null) {
            buyers1.setCity(detailbuyers.getCity());
        }
            Buyers updateBuyers = buyersRepository.save(buyers1);
            return ResponseEntity.ok(updateBuyers);
        }

        @DeleteMapping("/{id}")
        public String deleteBuyers (@PathVariable(value = "id") Long id){
            Optional<Buyers> buyers = buyersRepository.findById(id);
            String result = "";
            if (!buyers.isPresent()) {
                result = "id " + id + " tidak ditemukan";
                return result;
            }
            result = "id " + id + " berhasil dihapus";
            buyersRepository.deleteById(id);
            return result;
        }

        @GetMapping("/{id}")
        public ResponseEntity<Buyers> getBuyersById (@PathVariable(value = "id") Long id){
            Optional<Buyers> buyers = buyersRepository.findById(id);
            if (!buyers.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Buyers buyers1 = buyers.get();
            return ResponseEntity.ok().body(buyers1);
        }
    }
