package com.examly.springapp.controller;

import com.examly.springapp.model.Item;
import com.examly.springapp.service.ItemService;
import com.examly.springapp.repository.ItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/owner")
@CrossOrigin(origins = "http://localhost:3000")
public class OwnerController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    public OwnerController(ItemService itemService, ItemRepository itemRepository) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
    }

    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        Item created = itemService.addItem(item);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
        Item updated = itemService.updateItem(id, item);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/items/{id}/toggle")
    public ResponseEntity<Item> toggleAvailability(@PathVariable Long id) {
        Item item = itemService.toggleAvailability(id);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalItems", itemRepository.count());
        stats.put("availableItems", itemRepository.countAvailableItems());
        stats.put("unavailableItems", itemRepository.countUnavailableItems());
        return ResponseEntity.ok(stats);
    }
}