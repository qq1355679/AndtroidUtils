package com.wzb.support.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.wzb.support.Support;
import com.wzb.support.modle.Base;
import com.wzb.support.util.Util;

/**
 * 作者 文泽彪
 * 时间 2016/5/20 0020.
 */
public class DataBase extends Base {
    private static DataBase db;
    private String dbName = Support.getUtil().getAppInfoEntity(mContext).appName + ".db";
    private String dbPath = null;
    private SQLiteDatabase sqlDb;
    private DBHelper dbHelper;
    private int version;

    private DataBase(Context mContext, int version) {
        super(mContext);
        this.version = version;
        if (dbPath != null)
            dbHelper = new DBHelper(mContext, dbPath, null, version);
        else
            dbHelper = new DBHelper(mContext, dbName, null, version);
    }

    public static DataBase getDb(Context context, int version) {
        if (db == null) {
            synchronized (DataBase.class) {
                if (db == null) {
                    db = new DataBase(context, version);
                }
            }
        }
        return db;
    }

    public static DataBase getDb(Context context) {
        return getDb(context, 1);
    }

    public interface OnDataBaseUpdate {
        public void onDataBaseUpdate(int oldVersion, int newVersion);
    }

}
