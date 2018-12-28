package com.fun.ex.app.utils;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fun.ex.app.htmltext.entity.ImageInfo;

import java.io.File;
import java.util.HashMap;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/28
 * 修改: 2018/12/28
 * 版本: v1.0.0
 * 描述:
 */
public class DownLoadHelper implements NetImageUtil.OnImageDownloadListener {

    private int mCurrentCount = 0;
    private int mTotalCount = 0;
    private RecyclerView.Adapter mAdapter;

    public DownLoadHelper(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
    }

    public void start(HashMap<String, ImageInfo> imageInfos) {
        if (imageInfos == null || imageInfos.isEmpty()) {
            return;
        }
        mCurrentCount = 0;
        mTotalCount = imageInfos.size();
        for (String key : imageInfos.keySet()) {
            ImageInfo imageInfo = imageInfos.get(key);
            NetImageUtil netImageUtil = new NetImageUtil();
            netImageUtil.setImagePath(imageInfo.getPath());
            netImageUtil.setHttpUrl(imageInfo.getUrl());
            netImageUtil.setDownloadListener(this);
            Log.d("common", "启动一次下载：" + imageInfo.getUrl());
            netImageUtil.execute();
        }
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onComplete(File file) {
        mCurrentCount += 1;
        Log.d("common", "成功下载：" + file.getPath());
        if (mCurrentCount >= mTotalCount) {
            if (mAdapter != null) {
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        Log.d("common", "刷新》》》》》》》》》");
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    public void onFailed(String message) {

    }
}
