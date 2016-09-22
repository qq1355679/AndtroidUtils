package com.wzb.support.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.wzb.support.Entity.AppInfoEntity;
import com.wzb.support.modle.Base;

/**
 * 作者 文泽彪
 * 时间 2016/5/20 0020.
 */
public class Util {
    public Util() {
    }

    public AppInfoEntity getAppInfoEntity(Context mContext) {
        PackageInfo pkg = null;
        AppInfoEntity info = new AppInfoEntity();
        try {
            pkg = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            info.appName = pkg.applicationInfo.loadLabel(mContext.getPackageManager()).toString();
            info.appVersionName = pkg.versionName;
            info.appVersionCode = pkg.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }
}
