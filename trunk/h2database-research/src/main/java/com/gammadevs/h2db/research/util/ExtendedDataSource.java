package com.gammadevs.h2db.research.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnectionFactory;

import java.sql.SQLException;

/**
 * Created by Anton on 12/14/2014.
 */
public class ExtendedDataSource extends BasicDataSource {

    public void setMvStore(Boolean value) {
        addConnectionProperty("MV_STORE", value ? "TRUE" : "FALSE");
    }

    public void setMvcc(Boolean value) {
        addConnectionProperty("MVCC", value ? "TRUE" : "FALSE");
    }

    public void setMultiThreaded(Boolean value) {
        addConnectionProperty("MULTI_THREADED", value ? "TRUE" : "FALSE");
    }

    public void setLockMode(Integer value) {
        addConnectionProperty("LOCK_MODE", value.toString());
    }

}
