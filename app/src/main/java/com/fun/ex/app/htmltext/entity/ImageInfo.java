package com.fun.ex.app.htmltext.entity;

import android.content.Context;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/26
 * 修改: 2018/12/26
 * 版本: v1.0.0
 * 描述: 图片下载实体
 */
public class ImageInfo {

    private String mPath;
    private String mUrl;
    private Context mContext;

    @Override
    public String toString() {
        return String.format("{path:%s,url:%s", getPath(), getUrl());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return obj.toString().equals(toString());
        }
        return super.equals(obj);
    }

    public String getPath() {
        return mPath;
    }

    public ImageInfo setPath(String path) {
        this.mPath = path;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public ImageInfo setUrl(String url) {
        this.mUrl = url;
        return this;
    }

    public Context getContext() {
        return mContext;
    }

    public ImageInfo setContext(Context context) {
        this.mContext = context;
        return this;
    }
}