CREATE TABLE client (
  id BIGINT PRIMARY KEY
  , name VARCHAR(200)
  , internal BOOLEAN
);

CREATE TABLE transaction (
  id BIGINT PRIMARY KEY
  , time TIMESTAMP
  , client_id BIGINT
  , ccyPair CHAR(6)
  , volume NUMERIC(15, 2)
  , client_rate NUMERIC(10, 5)
  , exit_rate NUMERIC(10, 5)
  , usd_rate NUMERIC(10, 5)
);

CREATE TABLE pnl_trend (
  transaction_id BIGINT PRIMARY KEY
  , pnl_5000 NUMERIC(8, 2)
  , pnl_2000 NUMERIC(8, 2)
  , pnl_1000 NUMERIC(8, 2)
  , pnl0 NUMERIC(8, 2)
  , pnl1000 NUMERIC(8, 2)
  , pnl2000 NUMERIC(8, 2)
  , pnl5000 NUMERIC(8, 2)
);
