package com.example.kycservice.api;

import com.example.kycservice.api.dto.PassengerResponse;
import com.example.kycservice.domain.Passenger;

/**
 * Simple mapping helpers to keep controllers readable.
 */
public final class PassengerMapper {

    private PassengerMapper() {}

    public static PassengerResponse toResponse(Passenger p) {
        PassengerResponse r = new PassengerResponse();
        r.setId(p.getId());
        r.setPhoneE164(p.getPhoneE164());
        r.setEmail(p.getEmail());
        r.setFullName(p.getFullName());
        r.setDateOfBirth(p.getDateOfBirth());
        r.setAadhaarLast4(p.getAadhaarLast4());
        r.setKycStatus(p.getKycStatus());
        r.setKycVerifiedAt(p.getKycVerifiedAt());
        return r;
    }
}
