package com.example.kycservice.api.dto;

import com.example.kycservice.domain.KycStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request body for updating passenger KYC status and optionally storing aadhaar reference.
 */
public class KycStatusUpdateRequest {

    @NotNull(message = "status is required")
    private KycStatus status;

    @Size(max = 100, message = "aadhaarRef must be <= 100 characters")
    @Pattern(regexp = "^[A-Za-z0-9._\\-]*$", message = "aadhaarRef contains invalid characters")
    private String aadhaarRef;

    public KycStatus getStatus() {
        return status;
    }

    public void setStatus(KycStatus status) {
        this.status = status;
    }

    public String getAadhaarRef() {
        return aadhaarRef;
    }

    public void setAadhaarRef(String aadhaarRef) {
        this.aadhaarRef = aadhaarRef;
    }
}
