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
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.fun.ex.app.htmltext.entity.ImageInfo;
import com.fun.ex.app.htmltext.interfase.HtmlInterface;
import com.fun.ex.app.htmltext.interfase.IHtmlImageGetter;
import com.fun.ex.app.htmltext.score.HtmlDrawable;
import com.fun.ex.app.htmltext.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

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
    private String mSavePath;
    private int mDefaultDrawable;
    // 需要下载的图片集合
    private HashMap<String, ImageInfo> imageInfoMap;
    private Bitmap mBitmap = null;
    private HtmlDrawable mDrawable = null;
    private AssetManager mAssetManager;
    private HtmlInterface htmlInterface;

    /**
     * 是否已经被触发下载图片
     * 每个Getter只允许被触发一次下载，避免重复触发
     */
    private boolean isDownload = false;

    public HtmlGetter(Activity context) {
        this.mContext = context;
        this.isDownload = false;
    }

    public void setHtmlInterface(HtmlInterface htmlInterface) {
        this.htmlInterface = htmlInterface;
    }

    public void setDefaultDrawable(int mDefaultDrawable) {
        this.mDefaultDrawable = mDefaultDrawable;
    }

    public void setSavePath(String absolutePath) {
        this.mSavePath = absolutePath;
    }

    public void setDownload(boolean download) {
        this.isDownload = download;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public int getDefaultDrawable() {
        return mDefaultDrawable;
    }

    /**
     * 触发下载：这种写法的好处是一个HtmlTextView只需要刷新一次
     */
    public void handleDownload() {
        if (htmlInterface != null) {
            if (!isDownload) {
                isDownload = true;
                htmlInterface.downLoadImage(mContext, imageInfoMap);
            }
        }
    }

    @Override
    public Drawable getDrawable(String source) {
        Log.d("common", "HTML遇到img标签：" + source);
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
        File saveFile = FileUtil.createImagePath(imageUrl, mSavePath);
        if (saveFile.exists()) {
            this.removeImageInfo(imageUrl);
            return BitmapFactory.decodeFile(saveFile.getPath());
        } else {
            addImageInfo(new ImageInfo().setContext(context).setUrl(imageUrl).setPath(saveFile.getPath()));
            return null;
        }
    }

    private void addImageInfo(ImageInfo info) {
        if (imageInfoMap == null) {
            imageInfoMap = new HashMap<>();
        }
        if (!imageInfoMap.containsKey(info.getUrl())) {
            this.imageInfoMap.put(info.getUrl(), info);
        }
    }

    private void removeImageInfo(String key) {
        if (imageInfoMap != null) {
            this.imageInfoMap.remove(key);
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
        if (imageInfoMap != null) {
            imageInfoMap.clear();
            imageInfoMap = null;
        }
    }
}