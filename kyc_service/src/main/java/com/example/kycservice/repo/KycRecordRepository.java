package com.example.kycservice.repo;

import com.example.kycservice.domain.KycRecord;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for KycRecord persistence operations.
 */
public interface KycRecordRepository extends JpaRepository<KycRecord, UUID> {
    List<KycRecord> findByPassengerIdOrderByCreatedAtDesc(UUID passengerId);
}
