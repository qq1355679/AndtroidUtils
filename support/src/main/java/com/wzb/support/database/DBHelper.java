package com.wzb.support.database;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 文泽彪
 * 时间 2016/5/20 0020.
 */
public class DBHelper extends SQLiteOpenHelper {
    private List<DataBase.OnDataBaseUpdate> onDataBaseUpdateList = new ArrayList<>();

    /**
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (DataBase.OnDataBaseUpdate l : onDataBaseUpdateList) {
            l.onDataBaseUpdate(oldVersion, newVersion);
        }
    }

    public void addUpgradeListener(DataBase.OnDataBaseUpdate l) {
        onDataBaseUpdateList.add(l);
    }
}
