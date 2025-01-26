package com.ageinghippy.customerwebsite.repository;

import com.ageinghippy.customerwebsite.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByName(String roleName);
}
