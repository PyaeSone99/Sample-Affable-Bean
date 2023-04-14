package com.example.sampleaffablebean.dao;

import com.example.sampleaffablebean.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category,Integer> {
}
