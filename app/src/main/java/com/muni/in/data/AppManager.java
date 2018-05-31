package com.muni.in.data;

/**
 * Created by ARUN DAVID on 5/5/2018.
 */

public class AppManager {
    private static final AppManager ourInstance = new AppManager();

    public static AppManager getInstance() {
        return ourInstance;
    }

    private AppManager() {
    }
}
