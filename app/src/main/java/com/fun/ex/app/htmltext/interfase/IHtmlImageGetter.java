package com.fun.ex.app.htmltext.interfase;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.Html;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/25
 * 修改: 2018/12/25
 * 版本: v1.0.0
 */
public interface IHtmlImageGetter extends Html.ImageGetter {

    /**
     * 获取默认的Drawable，当图片显示失败时展示
     *
     * @return DrawableRes
     */
    @DrawableRes
    int getDefaultDrawable();

    Context getContext();
}