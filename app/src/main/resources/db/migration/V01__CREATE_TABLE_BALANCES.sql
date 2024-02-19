CREATE TABLE balances (
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	account_number VARCHAR(10) NOT NULL,
	account_balance VARCHAR(50) NOT NULL,
	account_limit VARCHAR(50) NOT NULL,
	update_date DATETIME NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO balances (id, account_number, account_balance, account_limit, update_date) values (1, '0123456789', '100', '1500', NOW());