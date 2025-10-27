package com.examly.springapp.service;

import com.examly.springapp.model.Item;
import com.examly.springapp.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class ItemService {
    private final ItemRepository repo;

    public ItemService(ItemRepository repo) {
        this.repo = repo;
    }

    public Item addItem(Item item) {
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        item.setCreatedBy("owner");
        item.setUpdatedBy("owner");
        return repo.save(item);
    }

    public List<Item> getAllItems() {
        return repo.findAll();
    }

    public List<Item> getItemsByCategory(String category) {
        return repo.findByCategory(category);
    }

    public List<Item> getItemsSortedByPrice() {
        return repo.findAllByOrderByPriceAsc();
    }

    public Optional<Item> getItemById(Long id) {
        return repo.findById(id);
    }

    public Item updateItem(Long id, Item updated) {
        return repo.findById(id).map(existing -> {
            existing.setItemName(updated.getItemName());
            existing.setCategory(updated.getCategory());
            existing.setPrice(updated.getPrice());
            existing.setAvailable(updated.getAvailable());
            existing.setCalories(updated.getCalories());
            existing.setDescription(updated.getDescription());
            existing.setImageUrl(updated.getImageUrl());
            existing.setUpdatedAt(LocalDateTime.now());
            existing.setUpdatedBy("owner");
            return repo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public void deleteItem(Long id) {
        repo.deleteById(id);
    }

    public Item toggleAvailability(Long id) {
        return repo.findById(id).map(item -> {
            item.setAvailable(!item.getAvailable());
            item.setUpdatedAt(LocalDateTime.now());
            item.setUpdatedBy("owner");
            return repo.save(item);
        }).orElseThrow(() -> new RuntimeException("Item not found"));
    }
    
    // Paging, filtering, and sorting methods
    public Page<Item> getItemsWithPaging(int page, int size, String sortBy, String sortDir, 
                                        String search, String category, Boolean available, 
                                        Float minPrice, Float maxPrice) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                   Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // Apply filters based on provided parameters
        if (search != null && !search.isEmpty() && category != null && !category.isEmpty() && available != null) {
            return repo.findByItemNameContainingIgnoreCaseAndCategoryAndAvailable(search, category, available, pageable);
        } else if (search != null && !search.isEmpty() && category != null && !category.isEmpty()) {
            return repo.findByItemNameContainingIgnoreCaseAndCategory(search, category, pageable);
        } else if (search != null && !search.isEmpty() && available != null) {
            return repo.findByItemNameContainingIgnoreCaseAndAvailable(search, available, pageable);
        } else if (category != null && !category.isEmpty() && available != null) {
            return repo.findByCategoryAndAvailable(category, available, pageable);
        } else if (search != null && !search.isEmpty()) {
            return repo.findByItemNameContainingIgnoreCase(search, pageable);
        } else if (category != null && !category.isEmpty()) {
            return repo.findByCategory(category, pageable);
        } else if (available != null) {
            return repo.findByAvailable(available, pageable);
        } else if (minPrice != null && maxPrice != null) {
            return repo.findByPriceBetween(minPrice, maxPrice, pageable);
        } else {
            return repo.findAll(pageable);
        }
    }
}
