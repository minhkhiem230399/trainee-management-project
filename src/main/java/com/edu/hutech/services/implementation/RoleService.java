package com.edu.hutech.services.implementation;

import com.edu.hutech.entities.Role;
import com.edu.hutech.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> findByName(String name){
        return roleRepository.findByName(name);
    }
}
