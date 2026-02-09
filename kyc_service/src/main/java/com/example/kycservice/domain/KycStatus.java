package com.example.kycservice.domain;

/**
 * KYC lifecycle status for a Passenger and their related KycRecord(s).
 */
public enum KycStatus {
    NOT_STARTED,
    PENDING,
    VERIFIED,
    REJECTED
}
