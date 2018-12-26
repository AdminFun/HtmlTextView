package com.fun.ex.app.htmltext.score;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import java.io.InputStream;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/24
 * 修改: 2018/12/24
 * 版本: v1.0.0
 * 描述: 自定义BitmapDrawable，实现Image在HTML中正常显示（系统的BitmapDrawable显示在HTML中是缩小的）
 */
public class HtmlDrawable extends BitmapDrawable {

    private Bitmap mBitmap;

    public HtmlDrawable(Resources res, Bitmap mBitmap) {
        super(res);
        this.mBitmap = mBitmap;
    }

    public HtmlDrawable(Bitmap bitmap, Bitmap mBitmap) {
        super(bitmap);
        this.mBitmap = mBitmap;
    }

    public HtmlDrawable(Resources res, Bitmap bitmap, Bitmap mBitmap) {
        super(res, bitmap);
        this.mBitmap = mBitmap;
    }

    public HtmlDrawable(String filepath, Bitmap mBitmap) {
        super(filepath);
        this.mBitmap = mBitmap;
    }

    public HtmlDrawable(Resources res, String filepath, Bitmap mBitmap) {
        super(res, filepath);
        this.mBitmap = mBitmap;
    }

    public HtmlDrawable(InputStream is, Bitmap mBitmap) {
        super(is);
        this.mBitmap = mBitmap;
    }

    public HtmlDrawable(Resources res, InputStream is, Bitmap mBitmap) {
        super(res, is);
        this.mBitmap = mBitmap;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, getPaint());
        }
    }

    public void onDestroy() {
        if (mBitmap != null) {
            if (!mBitmap.isRecycled()) {
                mBitmap.recycle();
            }
        }
    }
}