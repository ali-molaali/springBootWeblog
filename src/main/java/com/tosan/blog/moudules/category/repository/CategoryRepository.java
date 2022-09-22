package com.tosan.blog.moudules.category.repository;

import com.tosan.blog.moudules.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository <Category,Long> {
}
