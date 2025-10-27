package com.examly.springapp.specification;

import com.examly.springapp.model.MenuItem;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MenuItemSpecification {
    
    public static Specification<MenuItem> hasMenuId(Long menuId) {
        return (root, query, cb) -> menuId == null ? null : cb.equal(root.get("menu").get("id"), menuId);
    }
    
    public static Specification<MenuItem> hasOwnerId(Long ownerId) {
        return (root, query, cb) -> ownerId == null ? null : cb.equal(root.get("menu").get("ownerId"), ownerId);
    }
    
    public static Specification<MenuItem> hasCategory(String category) {
        return (root, query, cb) -> category == null ? null : cb.equal(root.get("category"), category);
    }
    
    public static Specification<MenuItem> searchText(String q) {
        return (root, query, cb) -> {
            if (q == null || q.trim().isEmpty()) return null;
            String pattern = "%" + q.toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("name")), pattern),
                cb.like(cb.lower(root.get("description")), pattern),
                cb.like(cb.lower(root.get("tags")), pattern)
            );
        };
    }
    
    public static Specification<MenuItem> priceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) return null;
            if (minPrice != null && maxPrice != null) {
                return cb.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else {
                return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
        };
    }
    
    public static Specification<MenuItem> isAvailable(Boolean available) {
        return (root, query, cb) -> available == null ? null : cb.equal(root.get("available"), available);
    }
    
    public static Specification<MenuItem> hasTags(String tags) {
        return (root, query, cb) -> {
            if (tags == null || tags.trim().isEmpty()) return null;
            String[] tagArray = tags.split(",");
            var predicates = new jakarta.persistence.criteria.Predicate[tagArray.length];
            for (int i = 0; i < tagArray.length; i++) {
                predicates[i] = cb.like(cb.lower(root.get("tags")), "%" + tagArray[i].trim().toLowerCase() + "%");
            }
            return cb.or(predicates);
        };
    }
    
    public static Specification<MenuItem> createdAfter(LocalDateTime createdAfter) {
        return (root, query, cb) -> createdAfter == null ? null : cb.greaterThanOrEqualTo(root.get("createdAt"), createdAfter);
    }
    
    public static Specification<MenuItem> createdBefore(LocalDateTime createdBefore) {
        return (root, query, cb) -> createdBefore == null ? null : cb.lessThanOrEqualTo(root.get("createdAt"), createdBefore);
    }
}