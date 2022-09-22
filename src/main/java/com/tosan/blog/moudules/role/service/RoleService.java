package com.tosan.blog.moudules.role.service;

import com.tosan.blog.moudules.role.model.Role;
import com.tosan.blog.moudules.role.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public void roleRegister(Role role) {
        roleRepository.save(role);
    }

    public Object findById(Long id) {
        return roleRepository.findById(id);
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }
}
