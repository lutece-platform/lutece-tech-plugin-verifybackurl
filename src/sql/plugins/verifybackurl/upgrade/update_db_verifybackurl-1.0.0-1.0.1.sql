-- liquibase formatted sql
-- changeset core:update_db_verifybackurl-1.0.0-1.0.1.sql
-- preconditions onFail:MARK_RAN onError:WARN
ALTER TABLE verifybackurl_authorized_url ADD COLUMN application_code varchar(100) DEFAULT NULL;

