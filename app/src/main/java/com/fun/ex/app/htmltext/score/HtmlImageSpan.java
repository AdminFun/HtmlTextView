package com.fun.ex.app.htmltext.score;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.style.ImageSpan;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/24
 * 修改: 2018/12/24
 * 版本: v1.0.0
 * 描述: 自定义ImageSpan，实现Image在HTML中居中显示
 */
public class HtmlImageSpan extends ImageSpan {

    public HtmlImageSpan(@NonNull Bitmap b) {
        super(b);
    }

    public HtmlImageSpan(@NonNull Bitmap b, int verticalAlignment) {
        super(b, verticalAlignment);
    }

    public HtmlImageSpan(@NonNull Context context, @NonNull Bitmap bitmap) {
        super(context, bitmap);
    }

    public HtmlImageSpan(@NonNull Context context, @NonNull Bitmap bitmap, int verticalAlignment) {
        super(context, bitmap, verticalAlignment);
    }

    public HtmlImageSpan(@NonNull Drawable drawable) {
        super(drawable);
    }

    public HtmlImageSpan(@NonNull Drawable drawable, int verticalAlignment) {
        super(drawable, verticalAlignment);
    }

    public HtmlImageSpan(@NonNull Drawable drawable, @NonNull String source) {
        super(drawable, source);
    }

    public HtmlImageSpan(@NonNull Drawable drawable, @NonNull String source, int verticalAlignment) {
        super(drawable, source, verticalAlignment);
    }

    public HtmlImageSpan(@NonNull Context context, @NonNull Uri uri) {
        super(context, uri);
    }

    public HtmlImageSpan(@NonNull Context context, @NonNull Uri uri, int verticalAlignment) {
        super(context, uri, verticalAlignment);
    }

    public HtmlImageSpan(@NonNull Context context, int resourceId) {
        super(context, resourceId);
    }

    public HtmlImageSpan(@NonNull Context context, int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, @NonNull Paint paint) {
        Drawable b = getDrawable();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        // 计算y方向的位移
        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;
        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}