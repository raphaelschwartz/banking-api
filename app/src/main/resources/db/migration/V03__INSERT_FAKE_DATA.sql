-- account
INSERT INTO account (id, number, owner_id, balance, status, update_date, active)
VALUES (123456, '0123456', 123, 50.20, 'ACTIVE', NOW(), true);

INSERT INTO account (id, number, owner_id, balance, status, update_date, active)
VALUES (678910, '9875240', 1234, 90.78, 'ACTIVE', NOW(), true);


-- activity
INSERT INTO activity (id, type, detail, create_date, amount, account_id, balance_after, key)
VALUES (1, 'WITHDRAW', 'Trânsferência enviada para 67890', NOW(), 50.20, 123456, 80.20, UUID());

INSERT INTO activity (id, type, detail, create_date, amount, account_id, balance_after, key)
VALUES (2, 'DEPOSIT', 'Trânsferência recebida de 12345', NOW(), 50.20, 678910, 100.20, UUID());
