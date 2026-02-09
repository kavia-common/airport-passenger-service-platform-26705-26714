package com.example.kycservice.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Request body for creating a passenger.
 */
public class PassengerCreateRequest {

    @Pattern(
            regexp = "^\\+?[1-9]\\d{1,14}$",
            message = "phoneE164 must be a valid E.164 phone number (e.g. +919876543210)")
    private String phoneE164;

    @Email(message = "email must be a valid email address")
    private String email;

    @Size(max = 200, message = "fullName must be <= 200 characters")
    private String fullName;

    private LocalDate dateOfBirth;

    @Pattern(regexp = "^\\d{4}$", message = "aadhaarLast4 must be 4 digits")
    private String aadhaarLast4;

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
}
