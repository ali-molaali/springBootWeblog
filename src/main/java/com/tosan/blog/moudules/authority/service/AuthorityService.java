package com.tosan.blog.moudules.authority.service;

import com.tosan.blog.moudules.authority.model.Authority;
import com.tosan.blog.moudules.authority.repository.AuthorityRepository;
import com.tosan.blog.moudules.category.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {
    private final AuthorityRepository authorityRepository;
    @Autowired
    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public List<Authority> findAll(){
        return authorityRepository.findAll();
    }
}
