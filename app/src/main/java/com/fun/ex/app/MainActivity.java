package com.fun.ex.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fun.ex.app.htmltext.HtmlTextView;
import com.fun.ex.app.htmltext.entity.ImageInfo;
import com.fun.ex.app.htmltext.interfase.HtmlInterface;
import com.fun.ex.app.utils.NetImageUtil;

import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements HtmlInterface, NetImageUtil.OnImageDownloadListener {

    HtmlTextView htmlTextView;
    String str = "<font color='blue'>测试HTML标签：</font>" +
            "<br>1、显示网络图片1 <img src = \"http://static.dev.epetbar.com/static_www/goods_detail_dev/toBrand.png\" />" +
            "<br>2、网络图片2：<img src=\"http://img2.epetbar.com/brand/brandLogo/purchase_suppliers_file_1537174351.png\" />" +
            "<br>3、网络图片3：<img src=\"http://img2.epetbar.com/nowater/brand_logo/2018-03/07/15/6360b2e4d7a84a5b760db9f4545a813a.jpg\">" +
            "<br>4、网络图片4：<img src=\"http://img2.epetbar.com/brand/brandLogo/upload_file_1542336567.jpg\">" +
            "<br>5、Assets图片：<img src=\"assets://as_img_wx.png\"/>" +
            "<br>6、项目资源图片：<img src=\"drawable://mine_order_s4\"/>" +
            "<br>7、显示缺省图：<img src=\"mipmap://xxx.png\">";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        this.htmlTextView = this.findViewById(R.id.activity3_text1);
//        this.htmlTextView.setHtmlInterface(this)             // 设置下载回调
//                .setDefaultDrawable(R.drawable.mine_order_s1)// 设置默认图
//                .setHtml(str);                               // 设置要展示的HTML字符串

//        File file = Environment.getExternalStorageDirectory();
//        String path = file.getPath();
//        Log.d("common", "=======:" + path);
    }

    public void testList(View view) {
        startActivity(new Intent(this, ListActivity.class));
    }

    int currentCount = 0;
    int totalCount = 0;

    @Override
    public void downLoadImage(Context context, HashMap<String, ImageInfo> imageInfos) {
        // TODO 下载监听：这里使用下载工具下载图片，下载完成后回调 invalidate 方法即可
        if (imageInfos == null || imageInfos.isEmpty()) {
            return;
        }
        currentCount = 0;
        totalCount = imageInfos.size();
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
    public void onComplete(final File file) {
        currentCount += 1;
        Log.d("common", "成功下载：" + file.getPath());
        if (currentCount >= totalCount) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    htmlTextView.refreshHtml();
                }
            });
        }
    }

    @Override
    public void onFailed(String message) {

    }
}
