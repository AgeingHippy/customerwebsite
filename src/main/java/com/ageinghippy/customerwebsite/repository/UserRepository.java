package com.ageinghippy.customerwebsite.repository;

import com.ageinghippy.customerwebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
