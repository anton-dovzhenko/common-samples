package com.gammadevs.h2db.research.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringWriter;

import static org.apache.commons.io.IOUtils.copy;

/**
 * Created by Anton on 12/14/2014.
 */
public class DbUtil {

    public static String getScript(String name) {
        try {
            StringWriter writer = new StringWriter();
            copy(DbUtil.class.getResourceAsStream("/sql/" + name + ".sql"), writer);
            writer.close();
            return writer.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error on reading script " + name, e);
        }
    }
}
