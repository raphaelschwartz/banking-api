CREATE TABLE activity (
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	type VARCHAR(20) NOT NULL,
	detail VARCHAR(50) NOT NULL,
  create_date DATETIME NOT NULL,
  amount DECIMAL(19,2) NOT NULL,
	account_id BIGINT(20) NOT NULL,
	balance_after DECIMAL(19,2) NOT NULL,
	transaction_key VARCHAR(36) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE INDEX id_UNIQUE (id),
  INDEX account_idx (account_id),
  CONSTRAINT activity_fk_source FOREIGN KEY (account_id) REFERENCES account (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
