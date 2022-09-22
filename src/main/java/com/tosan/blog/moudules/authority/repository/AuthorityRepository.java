package com.tosan.blog.moudules.authority.repository;

import com.tosan.blog.moudules.authority.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long> {
}
