package com.fun.ex.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fun.ex.app.htmltext.HtmlTextView;
import com.fun.ex.app.htmltext.entity.ImageInfo;
import com.fun.ex.app.htmltext.interfase.HtmlInterface;
import com.fun.ex.app.utils.DownLoadHelper;

import java.util.HashMap;
import java.util.List;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/26
 * 修改: 2018/12/26
 * 版本: v1.0.0
 * 描述: 测试Adapter
 */
public class HtmlAdapter extends RecyclerView.Adapter<HtmlAdapter.HtmlViewHolder> implements HtmlInterface {

    List<String> texts;

    public HtmlAdapter(List<String> temps) {
        this.texts = temps;
    }

    public static class HtmlViewHolder extends RecyclerView.ViewHolder {

        public final HtmlTextView textView;

        public HtmlViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.html_text_view);
        }
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }

    @NonNull
    @Override
    public HtmlViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_layout, viewGroup, false);
        return new HtmlViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HtmlViewHolder holder, int position) {
        holder.textView.setHtmlInterface(this)             // 设置下载回调
                .setDefaultDrawable(R.drawable.mine_order_s1)// 设置默认图
                .setHtml(texts.get(position));             // 设置要展示的HTML字符串
    }

    @Override
    public void downLoadImage(Context context, HashMap<String, ImageInfo> imageInfos) {
        if (imageInfos == null || imageInfos.isEmpty()) {
            return;
        }
        DownLoadHelper loadHelper = new DownLoadHelper(this);
        loadHelper.start(imageInfos);
    }
}