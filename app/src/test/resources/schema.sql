DROP TABLE balances IF EXISTS;

CREATE TABLE balances (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY,
  account_number VARCHAR(10) NOT NULL,
  account_balance VARCHAR(50) NOT NULL,
  account_limit VARCHAR(50) NOT NULL,
  update_date TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);