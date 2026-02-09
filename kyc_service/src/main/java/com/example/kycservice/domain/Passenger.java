package com.example.kycservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Passenger aggregate for KYC and passenger profile.
 *
 * Maps to Flyway table: passengers
 */
@Entity
@Table(name = "passengers")
public class Passenger {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "phone_e164", length = 20, unique = true)
    private String phoneE164;

    @Column(name = "email", length = 255, unique = true)
    private String email;

    @Column(name = "full_name", length = 200)
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "aadhaar_last4", length = 4)
    private String aadhaarLast4;

    @Enumerated(EnumType.STRING)
    @Column(name = "kyc_status", nullable = false, columnDefinition = "kyc_status")
    private KycStatus kycStatus = KycStatus.NOT_STARTED;

    @Column(name = "kyc_verified_at")
    private Instant kycVerifiedAt;

    @Column(name = "digilocker_user_id", length = 100)
    private String digilockerUserId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "digilocker_document_refs", columnDefinition = "jsonb")
    private String digilockerDocumentRefsJson;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "created_by", columnDefinition = "uuid")
    private UUID createdBy;

    @Column(name = "updated_by", columnDefinition = "uuid")
    private UUID updatedBy;

    @Version
    @Column(name = "version")
    private Long version;

    @PrePersist
    void prePersist() {
        // For safety: Flyway already defaults id/created_at/updated_at in DB. This ensures JPA doesn't insert nulls.
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
        Instant now = Instant.now();
        if (this.createdAt == null) {
            this.createdAt = now;
        }
        if (this.updatedAt == null) {
            this.updatedAt = now;
        }
        if (this.kycStatus == null) {
            this.kycStatus = KycStatus.NOT_STARTED;
        }
    }

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

    public String getDigilockerUserId() {
        return digilockerUserId;
    }

    public void setDigilockerUserId(String digilockerUserId) {
        this.digilockerUserId = digilockerUserId;
    }

    public String getDigilockerDocumentRefsJson() {
        return digilockerDocumentRefsJson;
    }

    public void setDigilockerDocumentRefsJson(String digilockerDocumentRefsJson) {
        this.digilockerDocumentRefsJson = digilockerDocumentRefsJson;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UUID updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
