-- account
INSERT INTO account (id, external_id, number, owner_id, balance, available_limit, status, update_date, active)
VALUES (123456, UUID(), '0123456', UUID(), 50.20, 1000.00, 'ACTIVE', NOW(), true);

INSERT INTO account (id, external_id, number, owner_id, balance, available_limit, status, update_date, active)
VALUES (678910, UUID(), '9875240', UUID(), 90.78, 15000.00, 'ACTIVE', NOW(), true);


-- activity
INSERT INTO activity (id, external_id, type, create_date, amount, source_account_id, source_account_balance, target_account_id)
VALUES (123456, UUID(), 'TRANSFER', NOW(), 50.20, 123456, 100.20, 678910);
