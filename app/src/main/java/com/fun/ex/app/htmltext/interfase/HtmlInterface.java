package com.fun.ex.app.htmltext.interfase;

import android.content.Context;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/25
 * 修改: 2018/12/25
 * 版本: v1.0.0
 * 描述: HtmlView的接口，需要在此接口内执行下载并回调操作
 */
public interface HtmlInterface {

    /**
     * 下载图片
     * 下载完成后请调用 invalidate(File) 方法刷新UI
     */
    void downLoadImage(Context context, String imgUrl, String imgName);
}
