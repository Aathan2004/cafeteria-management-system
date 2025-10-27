package com.examly.springapp.controller;

import com.examly.springapp.dto.MenuItemDto;
import com.examly.springapp.service.MenuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MenuCustomerController {
    
    private final MenuService menuService;
    
    public MenuCustomerController(MenuService menuService) {
        this.menuService = menuService;
    }
    
    @GetMapping("/menu-items")
    public ResponseEntity<Page<MenuItemDto>> searchMenuItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String sort,
            @RequestParam(required = false) Long menuId,
            @RequestParam(required = false) Long ownerId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String createdAfter,
            @RequestParam(required = false) String createdBefore) {
        
        // Parse sort parameter
        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        Sort.Direction direction = sortParts.length > 1 && "desc".equalsIgnoreCase(sortParts[1]) 
            ? Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        
        // Parse dates
        LocalDateTime createdAfterDate = createdAfter != null ? LocalDateTime.parse(createdAfter) : null;
        LocalDateTime createdBeforeDate = createdBefore != null ? LocalDateTime.parse(createdBefore) : null;
        
        Page<MenuItemDto> result = menuService.searchMenuItems(menuId, ownerId, category, q, 
            minPrice, maxPrice, available, tags, createdAfterDate, createdBeforeDate, pageable);
        
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/menus/{menuId}/items")
    public ResponseEntity<Page<MenuItemDto>> getMenuItems(@PathVariable Long menuId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<MenuItemDto> result = menuService.searchMenuItems(menuId, null, null, null, 
            null, null, null, null, null, null, pageable);
        
        return ResponseEntity.ok(result);
    }
}