package com.examly.springapp.controller;

import com.examly.springapp.model.Item;
import com.examly.springapp.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/addItem")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        Item saved = itemService.addItem(item);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/allItems")
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    // expected by tests: GET /api/items/byCategory?category=...
    @GetMapping("/byCategory")
    public ResponseEntity<List<Item>> getItemsByCategory(@RequestParam String category) {
        return ResponseEntity.ok(itemService.getItemsByCategory(category));
    }

    // expected by tests: GET /api/items/sortedByPrice
    @GetMapping("/sortedByPrice")
    public ResponseEntity<List<Item>> getItemsSortedByPrice() {
        return ResponseEntity.ok(itemService.getItemsSortedByPrice());
    }

    // expected by tests: PUT /api/items/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
        Item updated = itemService.updateItem(id, item);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
    
    // Paging, filtering, and sorting endpoint
    @GetMapping("/paged")
    public ResponseEntity<Page<Item>> getItemsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "itemName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice) {
        
        Page<Item> items = itemService.getItemsWithPaging(page, size, sortBy, sortDir, 
                                                         search, category, available, minPrice, maxPrice);
        return ResponseEntity.ok(items);
    }
}
