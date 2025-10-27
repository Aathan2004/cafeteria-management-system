package com.examly.springapp.controller;

import com.examly.springapp.dto.MenuItemDto;
import com.examly.springapp.model.Menu;
import com.examly.springapp.model.MenuItem;
import com.examly.springapp.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/owners")
@CrossOrigin(origins = "http://localhost:3000")
public class MenuOwnerController {
    
    private final MenuService menuService;
    
    public MenuOwnerController(MenuService menuService) {
        this.menuService = menuService;
    }
    
    @PostMapping("/{ownerId}/menus")
    public ResponseEntity<Menu> createMenu(@PathVariable Long ownerId, @RequestBody Map<String, String> request) {
        Menu menu = menuService.createMenu(ownerId, request.get("name"), request.get("description"));
        return ResponseEntity.ok(menu);
    }
    
    @PostMapping("/menus/{menuId}/items")
    public ResponseEntity<MenuItem> addMenuItem(@PathVariable Long menuId, @RequestBody MenuItemDto dto) {
        MenuItem item = menuService.addMenuItem(menuId, dto);
        return ResponseEntity.ok(item);
    }
    
    @PostMapping("/menus/{menuId}/items/bulk")
    public ResponseEntity<List<MenuItem>> bulkAddItems(@PathVariable Long menuId, @RequestBody List<MenuItemDto> items) {
        List<MenuItem> savedItems = menuService.bulkAddItems(menuId, items);
        return ResponseEntity.ok(savedItems);
    }
    
    @PutMapping("/menu-items/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id, @RequestBody Map<String, Boolean> request) {
        MenuItem item = menuService.toggleAvailability(id, request.get("available"));
        return ResponseEntity.ok(item);
    }
}