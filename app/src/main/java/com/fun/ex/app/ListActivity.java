package com.fun.ex.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/26
 * 修改: 2018/12/26
 * 版本: v1.0.0
 */
public class ListActivity extends AppCompatActivity {

    List<String> stringList;
    String str = "<font color='blue'>测试HTML标签：</font>" +
            "1、显示网络图片1 <img src = \"http://static.dev.epetbar.com/static_www/goods_detail_dev/toBrand.png\" />" +
            "2、网络图片2：<img src=\"http://img2.epetbar.com/brand/brandLogo/purchase_suppliers_file_1537174351.png\" />" +
            "<br>Assets：<img src=\"assets://as_img_wx.png\"/>" +
            "<br>source：<img src=\"drawable://mine_order_s4\"/>";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_list_layout);
        RecyclerView recyclerView = this.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        initDatas();
        recyclerView.setAdapter(new HtmlAdapter(stringList));
    }

    private void initDatas() {
        this.stringList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            stringList.add(str);
        }
    }
}
