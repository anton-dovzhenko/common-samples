package com.gammadevs.h2db.research.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 12/14/2014.
 */
public interface Dao {

    void initSchema();
    void initClients();
    void initTransactionsAndPnL();
    List<Map<String, Object> > getPnLByClientReport(String ccyPair, boolean internal, double minPnL);

}
