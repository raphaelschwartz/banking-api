INSERT INTO account (id, number, owner_id, balance, status, update_date, active)
VALUES (1, '0001', 1234, 920.00, 'ACTIVE', NOW(), true);

INSERT INTO account (id, number, owner_id, balance, status, update_date, active)
VALUES (2, '0002', 5678, 2080.00, 'ACTIVE', NOW(), true);


-- activity
INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('WITHDRAW', 'Trânsferência enviada para 0002', NOW() - 1, 100.00, 1, 1900.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('DEPOSIT', 'Trânsferência recebida de 0001', NOW() - 1, 100.00, 2, 1100.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('WITHDRAW', 'Trânsferência enviada para 0002', NOW(), 200.00, 1, 1700.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('DEPOSIT', 'Trânsferência recebida de 0001', NOW(), 200.00, 2, 1300.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('WITHDRAW', 'Trânsferência enviada para 0002', NOW(), 350.00, 1, 1350.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after,transaction_key)
VALUES ('DEPOSIT', 'Trânsferência recebida de 0001', NOW(), 350.00, 2, 1650.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('WITHDRAW', 'Trânsferência enviada para 0002', NOW(), 430.00, 1, 920.00, UUID());

INSERT INTO activity (type, detail, create_date, amount, account_id, balance_after, transaction_key)
VALUES ('DEPOSIT', 'Trânsferência recebida de 0003', NOW(), 430.00, 2, 2080.00, UUID());
