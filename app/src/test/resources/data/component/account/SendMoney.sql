-- account
INSERT INTO account (id, number, owner_id, balance, status, update_date, active)
VALUES (1, '0001', 1234, 100.00, 'ACTIVE', NOW(), true);

INSERT INTO account (id, number, owner_id, balance, status, update_date, active)
VALUES (2, '0002', 5678, 150.00, 'ACTIVE', NOW(), true);

INSERT INTO account (id, number, owner_id, balance, status, update_date, active)
VALUES (3, '0003', 7890, 1050.00, 'ACTIVE', NOW(), true);

INSERT INTO account (id, number, owner_id, balance, status, update_date, active)
VALUES (4, '0004', 3456, 1950.00, 'ACTIVE', NOW(), true);


-- activity
INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('WITHDRAW', 'Trânsferência enviada para 0004', NOW() - 1, 100.00, 3, 1900.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('DEPOSIT', 'Trânsferência recebida de 0003', NOW() - 1, 100.00, 4, 1100.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('WITHDRAW', 'Trânsferência enviada para 0004', NOW(), 200.00, 3, 1700.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('DEPOSIT', 'Trânsferência recebida de 0003', NOW(), 200.00, 4, 1300.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('WITHDRAW', 'Trânsferência enviada para 0004', NOW(), 350.00, 3, 1350.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after,transaction_key)
VALUES ('DEPOSIT', 'Trânsferência recebida de 0003', NOW(), 350.00, 4, 1650.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('WITHDRAW', 'Trânsferência enviada para 0004', NOW(), 400.00, 3, 950.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('DEPOSIT', 'Trânsferência recebida de 0003', NOW(), 400.00, 4, 2050.00, UUID());
