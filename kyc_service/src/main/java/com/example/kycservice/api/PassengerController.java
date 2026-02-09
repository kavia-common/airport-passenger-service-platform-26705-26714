package com.example.kycservice.api;

import com.example.kycservice.api.dto.PassengerCreateRequest;
import com.example.kycservice.api.dto.PassengerResponse;
import com.example.kycservice.api.dto.PassengerUpdateRequest;
import com.example.kycservice.api.error.NotFoundException;
import com.example.kycservice.domain.Passenger;
import com.example.kycservice.repo.PassengerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Passenger CRUD endpoints.
 */
@RestController
@RequestMapping("/api/passengers")
@Tag(name = "Passengers", description = "Passenger CRUD and profile data")
public class PassengerController {

    private final PassengerRepository passengerRepository;

    public PassengerController(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @GetMapping
    @Operation(summary = "List passengers", description = "Returns all passengers (baseline endpoint; will be paginated later).")
    public List<PassengerResponse> list() {
        return passengerRepository.findAll().stream().map(PassengerMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get passenger by id")
    public PassengerResponse get(@PathVariable UUID id) {
        Passenger p =
                passengerRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("Passenger not found: " + id));
        return PassengerMapper.toResponse(p);
    }

    @PostMapping
    @Operation(summary = "Create passenger")
    public ResponseEntity<PassengerResponse> create(@Valid @RequestBody PassengerCreateRequest req) {
        Passenger p = new Passenger();
        p.setPhoneE164(req.getPhoneE164());
        p.setEmail(req.getEmail());
        p.setFullName(req.getFullName());
        p.setDateOfBirth(req.getDateOfBirth());
        p.setAadhaarLast4(req.getAadhaarLast4());

        Passenger saved = passengerRepository.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(PassengerMapper.toResponse(saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update passenger", description = "Updates provided fields for a passenger.")
    public PassengerResponse update(@PathVariable UUID id, @Valid @RequestBody PassengerUpdateRequest req) {
        Passenger p =
                passengerRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("Passenger not found: " + id));

        if (req.getPhoneE164() != null) {
            p.setPhoneE164(req.getPhoneE164());
        }
        if (req.getEmail() != null) {
            p.setEmail(req.getEmail());
        }
        if (req.getFullName() != null) {
            p.setFullName(req.getFullName());
        }
        if (req.getDateOfBirth() != null) {
            p.setDateOfBirth(req.getDateOfBirth());
        }
        if (req.getAadhaarLast4() != null) {
            p.setAadhaarLast4(req.getAadhaarLast4());
        }

        Passenger saved = passengerRepository.save(p);
        return PassengerMapper.toResponse(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete passenger")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!passengerRepository.existsById(id)) {
            throw new NotFoundException("Passenger not found: " + id);
        }
        passengerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
