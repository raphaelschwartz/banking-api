-- account
INSERT INTO account (id, number, owner_id, balance, status, update_date, active)
VALUES (1, '0001', 1234, 50.00, 'ACTIVE', NOW(), true);

INSERT INTO account (id, number, owner_id, balance, status, update_date, active)
VALUES (2, '0002', 5678, 100.00, 'ACTIVE', NOW(), true);


-- activity
INSERT INTO activity (id, type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES (1, 'WITHDRAW', 'Trânsferência enviada para 0001', NOW(), 50.00, 1, 50.00, UUID());

INSERT INTO activity (id, type, detail, create_date, amount, account_id, balance_after,transaction_key)
VALUES (2, 'DEPOSIT', 'Trânsferência recebida de 0002', NOW(), 50.00, 2, 100.00, UUID());
