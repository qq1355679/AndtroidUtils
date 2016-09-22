package com.wzb.support.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者 文泽彪
 * 时间 2016/8/25 0025.
 */
public class BitmapUtil
{
    /**
     * 读取sd卡图片并，返回压缩后的bitmap
     *
     * @param path
     * @param h
     * @param w
     * @return
     * @throws IOException
     */
    public static Bitmap revitionImageSize(String path, int h, int w) throws IOException
    {
        InputStream temp = new FileInputStream(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(temp, null, options);
        temp.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true)
        {
            if ((options.outWidth >> i <= w)
                    && (options.outHeight >> i <= h))
            {
                temp = new FileInputStream(path);
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;

                bitmap = BitmapFactory.decodeStream(temp, null, options);
                break;
            }
            i += 1;
        }
        temp.close();
        return bitmap;
    }

    /**
     * 从sd卡获取bitmap
     *
     * @return
     */
    public static Bitmap getImgBitmap(String path)
    {
        return BitmapFactory.decodeFile(path);
    }

    /**
     * 压缩图片
     *
     * @param image
     * @param size
     * @return
     */
    public static Bitmap compressImage(Bitmap image, int size)
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > size)
        {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        try
        {
            baos.flush();
            baos.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static boolean compressImageOutFile(String imgPath, int size)
    {
        return compressImageOutFile(imgPath, size);
    }

    /**
     * 压缩图片输出文件
     *
     * @param image   //需要压缩的位图
     * @param size    //期望大小
     * @param outPath //输出路径
     * @return
     */
    public static boolean compressImageOutFile(Bitmap image, int size, String outPath)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        LogUtil.dLog("原图大小:" + baos.toByteArray().length);
        while (baos.toByteArray().length / 1024 > size)
        {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;//每次都减少10
            if (options < 0)
                break;
        }
        LogUtil.dLog("压缩" + options + "%");
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(outPath);
            fos.write(baos.toByteArray());
            fos.flush();
            return true;
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        } finally
        {
            try
            {
                fos.flush();
                fos.close();
                baos.flush();
                baos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    /**
     * 比例压缩
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getCompressImage(String srcPath)
    {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww)
        {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh)
        {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap, 100);
    }

    /**
     * @param srcPath 图片路径
     * @param size    期望大小
     * @return 本地图片压缩流
     */
    public static byte[] getCompressImageBytes(String srcPath, int size)
    {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww)
        {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh)
        {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImageOutBytes(bitmap, size);
    }

    /**
     * 压缩图片输出文件
     *
     * @param image
     * @param size
     * @return
     */
    public static byte[] compressImageOutBytes(Bitmap image, int size)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        LogUtil.dLog("原图大小:" + baos.toByteArray().length);
        while (baos.toByteArray().length / 1024 > size)
        {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;//每次都减少10
            if (options < 0)
                break;
        }
        LogUtil.dLog("压缩" + options + "%");
        return baos.toByteArray();
    }
}
