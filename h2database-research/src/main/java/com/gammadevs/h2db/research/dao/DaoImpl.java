package com.gammadevs.h2db.research.dao;

import com.gammadevs.h2db.research.model.Client;
import com.gammadevs.h2db.research.model.PnLTrend;
import com.gammadevs.h2db.research.model.Transaction;
import com.gammadevs.h2db.research.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class DaoImpl implements Dao {

    private final String initSchemaScript = DbUtil.getScript("init_schema");
    private final String pnLByClientReportScript = DbUtil.getScript("client_pnl_report");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void initSchema() {
        jdbcTemplate.execute(initSchemaScript);
    }

    @Override
    public void initClients() {
        final List<Client> clients = new ArrayList<>();
        long id = 1;
        clients.add(new Client(id++, "Aberdeen", false));
        clients.add(new Client(id++, "BMW", false));
        clients.add(new Client(id++, "Citadel", false));
        clients.add(new Client(id++, "Citrix", false));
        clients.add(new Client(id++, "Gamma G10 Traders UA", true));
        clients.add(new Client(id++, "Gamma G10 Traders US", true));
        clients.add(new Client(id++, "Gamma G10 Traders UK", true));
        clients.add(new Client(id++, "Gamma G10 Traders SG", true));
        clients.add(new Client(id++, "Privat Bank", false));
        clients.add(new Client(id++, "Citi Bank", false));
        clients.add(new Client(id++, "Commerzbank", false));
        clients.add(new Client(id++, "JP Morgan", false));
        clients.add(new Client(id++, "a", false));
        clients.add(new Client(id++, "b", false));
        clients.add(new Client(id++, "c", false));
        clients.add(new Client(id++, "d", false));
        clients.add(new Client(id++, "e", false));
        clients.add(new Client(id++, "f", false));
        clients.add(new Client(id++, "g", false));
        clients.add(new Client(id, "h", false));

        jdbcTemplate.batchUpdate("INSERT INTO client VALUES(?, ?, ?)", new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int index = 1;
                ps.setLong(index++, clients.get(i).getId());
                ps.setString(index++, clients.get(i).getName());
                ps.setBoolean(index, clients.get(i).isInternal());
            }

            @Override
            public int getBatchSize() {
                return clients.size();
            }

        });
    }

    @Override
    public void initTransactionsAndPnL() {
        long id = 1;
        Random random = new Random();
        String[] ccyPairs = {"USDEUR", "USDSEK", "USDNOK"};
        double[] rates = {1.21, 7.55, 7.37};
        for (int i = 0; i < 100; i++) {
            List<Transaction> transactions = new ArrayList<>();
            List<PnLTrend> pnlTrends = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                int ccy = Math.abs(random.nextInt()) % 3;
                //System.out.println(ccy);
                Transaction transaction = new Transaction(id
                , new Date(System.currentTimeMillis() - Math.abs(random.nextLong()))
                , Math.abs(random.nextLong()) % 20
                , ccyPairs[ccy]
                , new BigDecimal(random.nextLong() % 50000000).setScale(2)
                , new BigDecimal(rates[ccy] + (random.nextDouble() - 0.5) / 10).setScale(5, BigDecimal.ROUND_HALF_DOWN)
                , new BigDecimal(rates[ccy] + (random.nextDouble() - 0.5) / 10).setScale(5, BigDecimal.ROUND_HALF_DOWN)
                , BigDecimal.ONE.setScale(5));

                double basicPnL = transaction.getVolume().doubleValue() *
                        (transaction.getExitRate().doubleValue() - transaction.getClientRate().doubleValue());

                PnLTrend pnLTrend = new PnLTrend(id
                        , new BigDecimal(basicPnL * (random.nextDouble() - 0.5) / 10).setScale(2, BigDecimal.ROUND_HALF_DOWN)
                        , new BigDecimal(basicPnL * (random.nextDouble() - 0.5) / 10).setScale(2, BigDecimal.ROUND_HALF_DOWN)
                        , new BigDecimal(basicPnL * (random.nextDouble() - 0.5) / 10).setScale(2, BigDecimal.ROUND_HALF_DOWN)
                        , new BigDecimal(basicPnL * (random.nextDouble() - 0.5) / 10).setScale(2, BigDecimal.ROUND_HALF_DOWN)
                        , new BigDecimal(basicPnL * (random.nextDouble() - 0.5) / 10).setScale(2, BigDecimal.ROUND_HALF_DOWN)
                        , new BigDecimal(basicPnL * (random.nextDouble() - 0.5) / 10).setScale(2, BigDecimal.ROUND_HALF_DOWN)
                        , new BigDecimal(basicPnL * (random.nextDouble() - 0.5) / 10).setScale(2, BigDecimal.ROUND_HALF_DOWN));

                transactions.add(transaction);
                pnlTrends.add(pnLTrend);
                id++;
            }

            jdbcTemplate.batchUpdate("INSERT INTO transaction VALUES(?,?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    int index = 1;
                    ps.setLong(index++, transactions.get(i).getId());
                    ps.setTimestamp(index++, new Timestamp(transactions.get(i).getTime().getTime()));
                    ps.setLong(index++, transactions.get(i).getClientId());
                    ps.setString(index++, transactions.get(i).getCcyPair());
                    ps.setBigDecimal(index++, transactions.get(i).getVolume());
                    ps.setBigDecimal(index++, transactions.get(i).getClientRate());
                    ps.setBigDecimal(index++, transactions.get(i).getExitRate());
                    ps.setBigDecimal(index, transactions.get(i).getUsdRate());
                }

                @Override
                public int getBatchSize() {
                    return transactions.size();
                }
            });

            jdbcTemplate.batchUpdate("INSERT INTO pnl_trend VALUES(?,?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    int index = 1;
                    ps.setLong(index++, pnlTrends.get(i).getId());
                    ps.setBigDecimal(index++, pnlTrends.get(i).getPnl_5000());
                    ps.setBigDecimal(index++, pnlTrends.get(i).getPnl_2000());
                    ps.setBigDecimal(index++, pnlTrends.get(i).getPnl_1000());
                    ps.setBigDecimal(index++, pnlTrends.get(i).getPnl0());
                    ps.setBigDecimal(index++, pnlTrends.get(i).getPnl1000());
                    ps.setBigDecimal(index++, pnlTrends.get(i).getPnl2000());
                    ps.setBigDecimal(index, pnlTrends.get(i).getPnl5000());
                }

                @Override
                public int getBatchSize() {
                    return pnlTrends.size();
                }
            });

        }
    }

    @Override
    public List<Map<String, Object>> getPnLByClientReport(String ccyPair, boolean internal, double minPnL) {
        return jdbcTemplate.queryForList(pnLByClientReportScript, ccyPair, internal, minPnL);
    }
}
