package com.examly.springapp.service;

import com.examly.springapp.dto.MenuItemDto;
import com.examly.springapp.model.Menu;
import com.examly.springapp.model.MenuItem;
import com.examly.springapp.repository.MenuRepository;
import com.examly.springapp.repository.MenuItemRepository;
import com.examly.springapp.specification.MenuItemSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MenuService {
    
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    
    public MenuService(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }
    
    // Owner services
    public Menu createMenu(Long ownerId, String name, String description) {
        return menuRepository.save(new Menu(name, ownerId, description));
    }
    
    public MenuItem addMenuItem(Long menuId, MenuItemDto dto) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));
        MenuItem item = new MenuItem(menu, dto.getName(), dto.getDescription(), dto.getPrice(), dto.getCategory(), dto.getTags());
        return menuItemRepository.save(item);
    }
    
    public MenuItem toggleAvailability(Long itemId, Boolean available) {
        MenuItem item = menuItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        item.setAvailable(available);
        return menuItemRepository.save(item);
    }
    
    public List<MenuItem> bulkAddItems(Long menuId, List<MenuItemDto> items) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));
        List<MenuItem> menuItems = items.stream()
            .map(dto -> new MenuItem(menu, dto.getName(), dto.getDescription(), dto.getPrice(), dto.getCategory(), dto.getTags()))
            .toList();
        return menuItemRepository.saveAll(menuItems);
    }
    
    // Customer services
    @Transactional(readOnly = true)
    public Page<MenuItemDto> searchMenuItems(Long menuId, Long ownerId, String category, String q, 
                                           BigDecimal minPrice, BigDecimal maxPrice, Boolean available, 
                                           String tags, LocalDateTime createdAfter, LocalDateTime createdBefore, 
                                           Pageable pageable) {
        
        Specification<MenuItem> spec = Specification.where(MenuItemSpecification.hasMenuId(menuId))
            .and(MenuItemSpecification.hasOwnerId(ownerId))
            .and(MenuItemSpecification.hasCategory(category))
            .and(MenuItemSpecification.searchText(q))
            .and(MenuItemSpecification.priceBetween(minPrice, maxPrice))
            .and(MenuItemSpecification.isAvailable(available))
            .and(MenuItemSpecification.hasTags(tags))
            .and(MenuItemSpecification.createdAfter(createdAfter))
            .and(MenuItemSpecification.createdBefore(createdBefore));
        
        return menuItemRepository.findAll(spec, pageable).map(this::toDto);
    }
    
    @Transactional(readOnly = true)
    private MenuItemDto toDto(MenuItem item) {
        MenuItemDto dto = new MenuItemDto();
        dto.setId(item.getId());
        dto.setMenuId(item.getMenu().getId());
        dto.setMenuName(item.getMenu().getName());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setPrice(item.getPrice());
        dto.setCategory(item.getCategory());
        dto.setAvailable(item.getAvailable());
        dto.setTags(item.getTags());
        dto.setImageUrl(item.getImageUrl());
        dto.setCreatedAt(item.getCreatedAt());
        return dto;
    }
}