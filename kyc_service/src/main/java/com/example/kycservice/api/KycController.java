package com.example.kycservice.api;

import com.example.kycservice.api.dto.KycStatusUpdateRequest;
import com.example.kycservice.api.error.NotFoundException;
import com.example.kycservice.domain.AuditLog;
import com.example.kycservice.domain.KycRecord;
import com.example.kycservice.domain.KycStatus;
import com.example.kycservice.domain.Passenger;
import com.example.kycservice.repo.AuditLogRepository;
import com.example.kycservice.repo.KycRecordRepository;
import com.example.kycservice.repo.PassengerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Baseline KYC endpoints:
 * - Read passenger KYC status
 * - Update passenger KYC status and create a KycRecord + AuditLog
 */
@RestController
@RequestMapping("/api/kyc")
@Tag(name = "KYC", description = "KYC status retrieval and updates (baseline)")
public class KycController {

    private final PassengerRepository passengerRepository;
    private final KycRecordRepository kycRecordRepository;
    private final AuditLogRepository auditLogRepository;

    public KycController(
            PassengerRepository passengerRepository,
            KycRecordRepository kycRecordRepository,
            AuditLogRepository auditLogRepository) {
        this.passengerRepository = passengerRepository;
        this.kycRecordRepository = kycRecordRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @GetMapping("/passengers/{passengerId}/status")
    @Operation(summary = "Get passenger KYC status")
    public Map<String, Object> getStatus(@PathVariable UUID passengerId) {
        Passenger p =
                passengerRepository
                        .findById(passengerId)
                        .orElseThrow(() -> new NotFoundException("Passenger not found: " + passengerId));

        Map<String, Object> resp = new HashMap<>();
        resp.put("passengerId", p.getId());
        resp.put("status", p.getKycStatus());
        resp.put("kycVerifiedAt", p.getKycVerifiedAt());
        return resp;
    }

    @GetMapping("/passengers/{passengerId}/records")
    @Operation(summary = "List KYC records for passenger")
    public List<Map<String, Object>> listRecords(@PathVariable UUID passengerId) {
        // Ensure passenger exists
        if (!passengerRepository.existsById(passengerId)) {
            throw new NotFoundException("Passenger not found: " + passengerId);
        }

        return kycRecordRepository.findByPassengerIdOrderByCreatedAtDesc(passengerId).stream()
                .map(
                        r -> {
                            Map<String, Object> m = new HashMap<>();
                            m.put("id", r.getId());
                            m.put("passengerId", passengerId);
                            m.put("aadhaarRef", r.getAadhaarRef());
                            m.put("status", r.getStatus());
                            m.put("createdAt", r.getCreatedAt());
                            m.put("updatedAt", r.getUpdatedAt());
                            return m;
                        })
                .toList();
    }

    @PutMapping("/passengers/{passengerId}/status")
    @Transactional
    @Operation(
            summary = "Update passenger KYC status",
            description =
                    "Sets passenger.kyc_status and (optionally) creates a KycRecord capturing aadhaarRef and status.")
    public ResponseEntity<Map<String, Object>> updateStatus(
            @PathVariable UUID passengerId, @Valid @RequestBody KycStatusUpdateRequest req) {
        Passenger p =
                passengerRepository
                        .findById(passengerId)
                        .orElseThrow(() -> new NotFoundException("Passenger not found: " + passengerId));

        KycStatus newStatus = req.getStatus();
        p.setKycStatus(newStatus);
        if (newStatus == KycStatus.VERIFIED) {
            p.setKycVerifiedAt(Instant.now());
        }
        passengerRepository.save(p);

        KycRecord rec = new KycRecord();
        rec.setPassenger(p);
        rec.setAadhaarRef(req.getAadhaarRef());
        rec.setStatus(newStatus);
        KycRecord savedRec = kycRecordRepository.save(rec);

        AuditLog log = new AuditLog();
        log.setActorType("SYSTEM");
        log.setAction("KYC_STATUS_UPDATED");
        log.setEntityType("Passenger");
        log.setEntityId(p.getId());

        String detailsJson =
                "{\"newStatus\":\"" + newStatus.name() + "\"" +
                        (req.getAadhaarRef() != null ? ",\"aadhaarRef\":\"" + req.getAadhaarRef() + "\"" : "") +
                        ",\"kycRecordId\":\"" + savedRec.getId() + "\"}";
        log.setDetailsJson(detailsJson);
        auditLogRepository.save(log);

        Map<String, Object> resp = new HashMap<>();
        resp.put("passengerId", p.getId());
        resp.put("status", p.getKycStatus());
        resp.put("kycVerifiedAt", p.getKycVerifiedAt());
        resp.put("kycRecordId", savedRec.getId());
        return ResponseEntity.ok(resp);
    }
}
