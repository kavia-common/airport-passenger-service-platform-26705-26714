package com.example.kycservice.repo;

import com.example.kycservice.domain.Passenger;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Passenger persistence operations.
 */
public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
    Optional<Passenger> findByPhoneE164(String phoneE164);

    Optional<Passenger> findByEmail(String email);
}
