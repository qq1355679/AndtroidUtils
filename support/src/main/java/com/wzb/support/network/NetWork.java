package com.wzb.support.network;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.wzb.support.Support;
import com.wzb.support.modle.Base;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 作者 文泽彪
 * 时间 2016/5/20 0020.
 */
public class NetWork extends Base
{
    private int timeOut = 10 * 1000;
    private HttpURLConnection connection;
    private String httpUrl;

    public NetWork(Context mContext, String httpUrl) throws IOException
    {
        super(mContext);
        this.httpUrl = httpUrl;
        init(httpUrl);
    }

    public void init(String httpUrl) throws IOException
    {
        URL url = new URL(httpUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(timeOut);
        connection.setReadTimeout(timeOut);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type",
                "text/html");
        this.httpUrl = httpUrl;
    }

    public void uploadFile(File file)
    {

    }

    public ResultBean sendHttpRequest()
    {
        return sendHttpRequest(new byte[0]);
    }

    public void sendHttpRequest(final HttpRequestListener listener)
    {

        sendHttpRequest(new byte[0], listener);
    }

    public void sendHttpRequest(String params, final HttpRequestListener listener)
    {
        sendHttpRequest(params.getBytes(), listener);
    }

    public void sendHttpRequest(final byte[] params, final HttpRequestListener listener)
    {
        Support.SUPPORT.getCachedPool().execute(new Runnable()
        {
            @Override
            public void run()
            {
                HttpEntity entity = new HttpEntity();
                entity.listener = listener;
                Message msg = handler.obtainMessage();
                msg.what = 0;
                msg.obj = entity;
                handler.sendMessage(msg);
                entity.resultBean = sendHttpRequest(params);
                Message msg2 = handler.obtainMessage();
                msg2.what = 1;
                msg2.obj = entity;
                handler.sendMessage(msg2);
            }
        });
    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    HttpEntity entity = (HttpEntity) msg.obj;
                    entity.listener.onRequestStart();
                    break;
                case 1:
                    HttpEntity entity1 = (HttpEntity) msg.obj;
                    entity1.listener.onRequestEnd(entity1.resultBean);
                    break;
            }
        }
    };

    public ResultBean sendHttpRequest(byte[] params)
    {
        ResultBean resultBean = new ResultBean();

        try
        {
            if (params == null || params.length == 0)
            {
                connection.setRequestMethod("GET");
            } else
            {
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Length",
                        String.valueOf(params.length));
                OutputStream os = connection.getOutputStream();
                os.write(params);
                os.flush();
            }
            int code = connection.getResponseCode();
            if (code == 200)
            {
                InputStream is = connection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len = 0;
                byte buffer[] = new byte[1024];
                while ((len = is.read(buffer)) != -1)
                {
                    baos.write(buffer, 0, len);
                }
                is.close();
                baos.close();
                resultBean.result = new String(baos.toByteArray());
            }
            resultBean.resultCode = code;
        } catch (Exception e)
        {
            e.printStackTrace();
            resultBean.exception = e;
            resultBean.resultCode = -1;
        }
        return resultBean;
    }

    public HttpURLConnection getConnection()
    {
        return connection;
    }

    public void setConnection(HttpURLConnection connection)
    {
        this.connection = connection;
    }

    public int getTimeOut()
    {
        return timeOut;
    }

    public void setTimeOut(int timeOut)
    {
        this.timeOut = timeOut;
    }

    class HttpEntity
    {
        public HttpRequestListener listener;
        public ResultBean resultBean;
    }
}
