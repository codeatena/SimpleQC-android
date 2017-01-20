package com.virtusventures.simpleqc.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by mac on 11/01/2017.
 */

@Database(name = QCDatabase.NAME, version = QCDatabase.VERSION)
public class QCDatabase {

    public static final String NAME = "QCDataBase";
    public static final int VERSION = 1;
}
