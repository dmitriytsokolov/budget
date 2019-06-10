package com.dmts.budget.repository;

import com.dmts.budget.dto.UserDto;
import com.dmts.budget.entity.User;
import com.dmts.budget.projections.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface UserRepository extends JpaRepository<User, Long>, QueryByExampleExecutor<User> {

    Page<User> findAll(Pageable pageable);

    List<User> findAll();

    User findByUsername(String username);

    User findById(long id);

    User findByEmail(String email);

    @Query("select new com.dmts.budget.dto.UserDto(u.username, u.email, u.role.roleName) from User u where u.email=:email")
    UserDto findByEmailQuery(@Param("email") String email);

}
