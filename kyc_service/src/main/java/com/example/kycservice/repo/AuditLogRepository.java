package com.example.kycservice.repo;

import com.example.kycservice.domain.AuditLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for AuditLog persistence operations.
 */
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {}
