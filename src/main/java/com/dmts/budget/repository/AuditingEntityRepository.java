package com.dmts.budget.repository;

import com.dmts.budget.entity.AuditingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditingEntityRepository extends JpaRepository<AuditingEntity, Long> {

}
