SELECT
  t.client_id
  , c.name
  , SUM(t.volume * t.usd_rate) AS usdVolume
  , SUM(p.pnl_5000) AS pnl_5000
  , SUM(p.pnl_2000) AS pnl_2000
  , SUM(p.pnl_1000) AS pnl_1000
  , SUM(p.pnl0) AS pnl0
  , SUM(p.pnl1000) AS pnl1000
  , SUM(p.pnl2000) AS pnl2000
  , SUM(p.pnl5000) AS pnl5000
  , SUM (t.volume * t.usd_rate * (t.exit_rate - t.client_rate)) AS pnl
FROM transaction t
  INNER JOIN pnl_trend p ON p.transaction_id = t.id
  INNER JOIN client c ON c.id = t.client_id
WHERE t.ccyPair = ?
  AND c.internal = ?
GROUP BY t.client_id, c.name
HAVING pnl >= ?