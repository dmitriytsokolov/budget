package com.dmts.budget.repository;

import com.dmts.budget.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    List<User> findAll();

    User findByUsername(String username);

    User findById(int id);

    User findByEmail(String email);

}
