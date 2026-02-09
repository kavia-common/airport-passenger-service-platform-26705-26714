-- Flyway migration V3 (kyc_service): service-local tables
-- We keep V1 as the shared platform schema. This migration adds tables needed specifically by kyc_service.

CREATE TABLE IF NOT EXISTS kyc_records (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  passenger_id uuid NOT NULL,
  aadhaar_ref varchar(100),
  status kyc_status NOT NULL DEFAULT 'NOT_STARTED',
  created_at timestamptz NOT NULL DEFAULT now(),
  updated_at timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT fk_kyc_records_passenger FOREIGN KEY (passenger_id) REFERENCES passengers(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_kyc_records_passenger_created_at
  ON kyc_records (passenger_id, created_at DESC);

CREATE TABLE IF NOT EXISTS audit_logs (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  actor_type varchar(30) NOT NULL,
  actor_id uuid,
  action varchar(120) NOT NULL,
  entity_type varchar(80),
  entity_id uuid,
  details jsonb,
  created_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_audit_logs_entity
  ON audit_logs (entity_type, entity_id, created_at DESC);
