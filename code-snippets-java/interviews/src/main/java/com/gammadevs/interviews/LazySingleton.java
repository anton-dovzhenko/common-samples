package com.gammadevs.interviews;

/**
 * Created by anton on 12/5/18.
 */
public class LazySingleton {

    //Volatile s not necessary
    private static LazySingleton instance;

    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }

}
