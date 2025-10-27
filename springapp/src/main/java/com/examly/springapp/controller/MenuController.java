package com.examly.springapp.controller;

import com.examly.springapp.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final ItemService itemService;

    public MenuController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getMenu() {
        List<String> categories = Arrays.asList("Continental", "Indian", "Arabic", "Chinese", "South Indian");
        
        List<Map<String, Object>> menu = categories.stream().map(category -> {
            Map<String, Object> section = new HashMap<>();
            section.put("id", category.toLowerCase().replace(" ", "_"));
            section.put("name", category);
            section.put("items", itemService.getItemsByCategory(category).stream().map(item -> {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("id", item.getId());
                itemMap.put("name", item.getItemName());
                itemMap.put("price", "₹" + item.getPrice().intValue());
                itemMap.put("imageUrl", item.getImageUrl());
                itemMap.put("description", item.getDescription());
                return itemMap;
            }).collect(Collectors.toList()));
            return section;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(menu);
    }
}