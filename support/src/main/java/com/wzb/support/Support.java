package com.wzb.support;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import com.wzb.support.cache.Cache;
import com.wzb.support.database.DataBase;
import com.wzb.support.network.NetWork;
import com.wzb.support.util.Util;
import com.wzb.support.view.View;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者 文泽彪
 * 时间 2016/5/19 0019.
 */
public enum Support
{
    SUPPORT();
    private Context mContext;
    private ExecutorService cachedPool;

    public Support init(Context context)
    {
        mContext = context;
        return this;
    }

    public Cache getCache()
    {
        return new Cache(mContext);
    }

    public DataBase getDataBase(int version)
    {
        return DataBase.getDb(mContext, version);
    }

    public DataBase getDataBase()
    {
        return DataBase.getDb(mContext);
    }

    public NetWork getNetWork(String httpUrl) throws IOException
    {
        return new NetWork(mContext, httpUrl);
    }

    public static Util getUtil()
    {
        return new Util();
    }

    public View getView()
    {
        return new View(mContext);
    }

    public Context getmContext()
    {
        return mContext;
    }

    public void setmContext(Context mContext)
    {
        this.mContext = mContext;
    }

    public ExecutorService getCachedPool()
    {
        if (cachedPool == null)
            cachedPool = Executors.newCachedThreadPool();
        return cachedPool;
    }

    public void setCachedPool(ExecutorService cachedPool)
    {
        this.cachedPool = cachedPool;
    }
}
