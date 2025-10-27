package com.examly.springapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    private Menu menu;
    
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Boolean available = true;
    private String tags; // Comma-separated tags
    private String imageUrl;
    private LocalDateTime createdAt;
    
    public MenuItem() {}
    
    public MenuItem(Menu menu, String name, String description, BigDecimal price, String category, String tags) {
        this.menu = menu;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.tags = tags;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Menu getMenu() { return menu; }
    public void setMenu(Menu menu) { this.menu = menu; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}