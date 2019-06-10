package com.dmts.budget.projections;

import com.dmts.budget.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "userProjection", types = { User.class })
public interface UserProjection {

    @Value("#{target.username} #{target.role.roleName}")
    String getFullName();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.role.roleName}")
    String getRole();
}
