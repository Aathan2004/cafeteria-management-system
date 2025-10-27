package com.examly.springapp.repository;

import com.examly.springapp.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.time.LocalDateTime;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategory(String category);
    List<Item> findAllByOrderByPriceAsc();
    List<Item> findByAvailable(Boolean available);
    
    // Paging and filtering methods
    Page<Item> findByItemNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Item> findByCategory(String category, Pageable pageable);
    Page<Item> findByAvailable(Boolean available, Pageable pageable);
    Page<Item> findByItemNameContainingIgnoreCaseAndCategory(String name, String category, Pageable pageable);
    Page<Item> findByItemNameContainingIgnoreCaseAndAvailable(String name, Boolean available, Pageable pageable);
    Page<Item> findByCategoryAndAvailable(String category, Boolean available, Pageable pageable);
    Page<Item> findByItemNameContainingIgnoreCaseAndCategoryAndAvailable(String name, String category, Boolean available, Pageable pageable);
    List<Item> findByUpdatedAtAfter(LocalDateTime dateTime);
    
    @Query("SELECT i FROM Item i WHERE i.available = true ORDER BY i.updatedAt DESC")
    List<Item> findAvailableItemsOrderByUpdatedDesc();
    
    @Query("SELECT i FROM Item i WHERE i.available = true ORDER BY i.updatedAt DESC")
    Page<Item> findAvailableItemsOrderByUpdatedDesc(Pageable pageable);
    
    @Query("SELECT COUNT(i) FROM Item i WHERE i.available = true")
    Long countAvailableItems();
    
    @Query("SELECT COUNT(i) FROM Item i WHERE i.available = false")
    Long countUnavailableItems();
    
    // Price range filtering
    Page<Item> findByPriceBetween(Float minPrice, Float maxPrice, Pageable pageable);
}
