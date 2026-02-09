package com.example.kycservice.api.dto;

import com.example.kycservice.domain.KycStatus;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Response DTO for Passenger.
 */
public class PassengerResponse {
    private UUID id;
    private String phoneE164;
    private String email;
    private String fullName;
    private LocalDate dateOfBirth;
    private String aadhaarLast4;
    private KycStatus kycStatus;
    private Instant kycVerifiedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhoneE164() {
        return phoneE164;
    }

    public void setPhoneE164(String phoneE164) {
        this.phoneE164 = phoneE164;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAadhaarLast4() {
        return aadhaarLast4;
    }

    public void setAadhaarLast4(String aadhaarLast4) {
        this.aadhaarLast4 = aadhaarLast4;
    }

    public KycStatus getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(KycStatus kycStatus) {
        this.kycStatus = kycStatus;
    }

    public Instant getKycVerifiedAt() {
        return kycVerifiedAt;
    }

    public void setKycVerifiedAt(Instant kycVerifiedAt) {
        this.kycVerifiedAt = kycVerifiedAt;
    }
}
