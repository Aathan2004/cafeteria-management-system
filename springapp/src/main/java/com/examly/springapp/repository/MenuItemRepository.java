package com.examly.springapp.repository;

import com.examly.springapp.model.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long>, JpaSpecificationExecutor<MenuItem> {
    List<MenuItem> findByMenuId(Long menuId);
    Page<MenuItem> findByMenuId(Long menuId, Pageable pageable);
}