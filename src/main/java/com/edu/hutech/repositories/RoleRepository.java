package com.edu.hutech.repositories;

import com.edu.hutech.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * find by name
     * 
     * @param name
     * @return
     */
    @Query(value = "select * from role where name = ?1", nativeQuery = true)
    List<Role> findByName(String name);

}
