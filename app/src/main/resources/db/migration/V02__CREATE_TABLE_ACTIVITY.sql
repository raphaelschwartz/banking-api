CREATE TABLE activity (
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	external_id VARBINARY(36) DEFAULT (UUID()),
	type VARCHAR(20) NOT NULL,
  create_date DATETIME NOT NULL,
  amount DECIMAL(19,2) NOT NULL,
	source_account_id BIGINT(20) NOT NULL,
	source_account_balance DECIMAL(19,2) NOT NULL,
  target_account_id BIGINT(20),
	PRIMARY KEY (id),
	UNIQUE INDEX id_UNIQUE (id),
  INDEX source_account_idx (source_account_id),
  INDEX target_account_idx (target_account_id),
  CONSTRAINT activity_fk_source FOREIGN KEY (source_account_id) REFERENCES account (id),
  CONSTRAINT activity_fk_target  FOREIGN KEY (target_account_id)  REFERENCES account (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
