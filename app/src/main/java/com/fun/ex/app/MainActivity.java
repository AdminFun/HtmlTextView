package com.fun.ex.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.fun.ex.app.utils.NetImageUtil;
import com.fun.ex.app.htmltext.HtmlTextView;
import com.fun.ex.app.htmltext.interfase.HtmlInterface;

import java.io.File;

public class MainActivity extends AppCompatActivity implements HtmlInterface, NetImageUtil.OnImageDownloadListener  {

    HtmlTextView htmlTextView;
    String str = "<font color='blue'>测试HTML标签：</font>" +
            "<br>1、显示网络图片1：<img src = \"http://static.dev.epetbar.com/static_www/goods_detail_dev/toBrand.png\" />" +
            "<br>2、网络图片2：<img src=\"http://img2.epetbar.com/brand/brandLogo/purchase_suppliers_file_1537174351.png\" />" +
            "<br>3、网络图片3：<img src=\"http://img2.epetbar.com/nowater/brand_logo/2018-03/07/15/6360b2e4d7a84a5b760db9f4545a813a.jpg\">" +
            "<br>4、网络图片4：<img src=\"http://img2.epetbar.com/brand/brandLogo/upload_file_1542336567.jpg\">" +
            "<br>5、Assets图片：<img src=\"assets://as_img_wx.png\"/>" +
            "<br>6、项目资源图片：<img src=\"drawable://mine_order_s4\"/>" +
            "<br>7、显示缺省图：<img src=\"mipmap://xxx.png\">";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_res_activity_main_layout);
        this.htmlTextView = this.findViewById(R.id.activity3_text1);
        this.htmlTextView.setHtmlInterface(this)             // 设置下载回调
                .setDefaultDrawable(R.drawable.mine_order_s1)// 设置默认图
                .setHtml(str);                               // 设置要展示的HTML字符串
    }

    @Override
    public void downLoadImage(Context context, String imgUrl, String imgName) {
        // TODO 下载监听：这里使用下载工具下载图片，下载完成后回调 invalidate 方法即可
        NetImageUtil netImageUtil = new NetImageUtil();
        netImageUtil.setImageName(imgName);
        netImageUtil.setHttpUrl(imgUrl);
        netImageUtil.setDownloadListener(this);
        netImageUtil.execute();
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onComplete(final File file) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                htmlTextView.invalidate(file);
            }
        });
    }

    @Override
    public void onFailed(String message) {

    }
}
