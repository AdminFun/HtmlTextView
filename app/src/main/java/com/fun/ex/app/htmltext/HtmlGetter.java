/*
 * SPAN_INCLUSIVE_INCLUSIVE：前后都包括，在指定范围前后插入新字符，都会应用新样式
 * SPAN_EXCLUSIVE_EXCLUSIVE：前后都不包括，在指定范围前后插入新字符，两端样式无变化
 * SPAN_INCLUSIVE_EXCLUSIVE：前面包括，后面不包括
 * SPAN_EXCLUSIVE_INCLUSIVE：后面包括，前面不包括
 *
 * AssetManager 是全局的，不能随手close，关闭后会影响其他资源的使用！！！
 * */
package com.fun.ex.app.htmltext;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.fun.ex.app.htmltext.interfase.HtmlInterface;
import com.fun.ex.app.htmltext.interfase.IHtmlImageGetter;
import com.fun.ex.app.htmltext.score.HtmlDrawable;
import com.fun.ex.app.htmltext.util.MD5;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/24
 * 修改: 2018/12/24
 * 版本: v1.0.0
 * 描述: 自定义ImageGetter，实现HTML显示各种类型的图片。
 * 目前支持 Assets图片、本地图片、项目资源图片(仅支持drawable)、网络图片
 */
public class HtmlGetter implements IHtmlImageGetter {

    // Assets资源
    private final String assets = "assets://";
    // 本地资源
    private final String file = "file://";
    // 项目资源
    private final String drawable = "drawable://";
    // 网络资源
    private final String http = "http://";
    private final String https = "https://";

    private Activity mContext;
    private int mDefaultDrawable;
    private Bitmap mBitmap = null;
    private HtmlDrawable mDrawable = null;
    private AssetManager mAssetManager;
    private HtmlInterface htmlInterface;

    public HtmlGetter(Activity context) {
        this.mContext = context;
    }

    public void setHtmlInterface(HtmlInterface htmlInterface) {
        this.htmlInterface = htmlInterface;
    }

    public void setDefaultDrawable(int mDefaultDrawable) {
        this.mDefaultDrawable = mDefaultDrawable;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public int getDefaultDrawable() {
        return mDefaultDrawable;
    }

    @Override
    public Drawable getDrawable(String source) {
        if (TextUtils.isEmpty(source)) {
            return null;
        }
        if (source.startsWith(assets)) {
            source = source.substring(assets.length());
            mBitmap = loadImageFromAssets(mContext, source);

        } else if (source.startsWith(file)) {
            source = source.substring(file.length());
            mBitmap = BitmapFactory.decodeFile(source);

        } else if (source.startsWith(drawable)) {
            source = source.substring(drawable.length());
            mBitmap = loadImageFromResource(mContext, source);

        } else if (source.startsWith(http) || source.startsWith(https)) {
            mBitmap = loadImageFromHttp(mContext, source);
        } else {
            mBitmap = null;
        }

        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(mContext.getResources(), getDefaultDrawable());
        }

        // 必须设为图片的边际,不然TextView显示不出图片
        if (mBitmap != null) {
            mDrawable = new HtmlDrawable(mContext.getResources(), mBitmap);
            mDrawable.setBounds(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
            return mDrawable;
        }
        return null;
    }

    /**
     * 从Assets文件夹中读取图片
     */
    private Bitmap loadImageFromAssets(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        this.mAssetManager = context.getResources().getAssets();
        InputStream open = null;
        try {
            open = this.mAssetManager.open(fileName);
            return BitmapFactory.decodeStream(open);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.mAssetManager = null;
            if (open != null) {
                try {
                    open.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 从项目资源加载图片
     */
    private Bitmap loadImageFromResource(@NonNull Context context, String source) {
        if (TextUtils.isEmpty(source)) {
            return null;
        }
        try {
            Resources resources = context.getResources();
            int resId = resources.getIdentifier(source, "drawable", mContext.getPackageName());
            Drawable drawable = resources.getDrawable(resId);
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            return bitmapDrawable.getBitmap();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取网络资源图片
     */
    private Bitmap loadImageFromHttp(Context context, String imageUrl) {
        String imageName = MD5.MD5(imageUrl) + ".png";
        Log.d("common", "网络资源：" + imageName);
        File file = new File(Environment.getExternalStorageDirectory(), imageName);
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getPath());
        } else {
            if (htmlInterface != null) {
                htmlInterface.downLoadImage(context, imageUrl, imageName);
            }
            return null;
        }
    }

    public void onDestroy() {
        mContext = null;
        if (mDrawable != null) {
            mDrawable.onDestroy();
            mDrawable = null;
        }
        if (mAssetManager != null) {
            mAssetManager = null;
        }
        if (mBitmap != null) {
            if (!mBitmap.isRecycled()) {
                mBitmap.recycle();
            }
            mBitmap = null;
        }
    }
}