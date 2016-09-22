package com.wzb.support.network;

/**
 * 作者 文泽彪
 * 时间 2016/9/7 0007.
 */
public interface HttpRequestListener
{
    public void onRequestStart();

    public void onRequestEnd(ResultBean resultBean);
}
