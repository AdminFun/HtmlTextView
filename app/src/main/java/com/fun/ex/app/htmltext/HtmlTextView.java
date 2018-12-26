/*
 *
 * 1、支持显示HTML标签，且图片可以居中显示！
 * 2、可自定义HTML标签识别类型
 * 3、图片类型支持：【assets://】、【file://】、【drawable://】、【http://】、【https://】
 * <p>
 * 注意事项：
 * 1、assets图片是全名，                 例如：assets://launch.png
 * 2、file是本地资源路径，               例如：/storage/emulated/0/launch.png
 * 3、drawable是项目资源，不含后缀名，    例如：drawable://launch
 * 4、http和https是网络图片路径，只要是可以下载的图片路径都可以
 *
 * 5、网络图片需要先下载再显示，所以需要实现接口，自行下载好后回调刷新
 */
package com.fun.ex.app.htmltext;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import com.fun.ex.app.htmltext.interfase.HtmlInterface;
import com.fun.ex.app.htmltext.interfase.IHtmlTagHandle;
import com.fun.ex.app.htmltext.score.HHtml;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/25
 * 修改: 2018/12/25
 * 版本: v1.0.0
 * 描述: 自定义TextView，实现一些自定义功能，功能详见顶部注释
 */
public class HtmlTextView extends AppCompatTextView {

    private Activity mActivity;
    private HtmlGetter mTextGetter;
    private IHtmlTagHandle mTagHandle = null;
    private HtmlInterface mHtmlInterface;
    private int mDrawable = 0;
    private String currentHtml;

    public HtmlTextView(Context context) {
        super(context);
        this.initViews(context);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initViews(context);
    }

    public HtmlTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initViews(context);
    }

    private void initViews(Context context) {
        this.mActivity = getActivity(context);
    }

    /**
     * 设置监听器：如果要显示网络图片就必须实现监听器，在外部下载图片后回调刷新
     */
    public HtmlTextView setHtmlInterface(HtmlInterface mHtmlInterface) {
        this.mHtmlInterface = mHtmlInterface;
        return this;
    }

    /**
     * 设置默认图片：当图片获取失败的时候，显示默认图片
     */
    public HtmlTextView setDefaultDrawable(int drawable) {
        this.mDrawable = drawable;
        return this;
    }

    /**
     * 要显示的、包含HTML标签的字符串
     */
    public void setHtml(String html) {
        this.currentHtml = html;
        this.setHtml(html, mDrawable, mHtmlInterface);
    }

    public void setHtml(String html, int defaultDrawable, HtmlInterface htmlInterface) {
        this.setDefaultDrawable(defaultDrawable);
        this.setHtmlInterface(htmlInterface);
        this.mTextGetter = new HtmlGetter(mActivity);
        String path = Environment.getExternalStorageDirectory().getPath()+"/ddg/";
        this.mTextGetter.setSavePath(path);
        this.mTextGetter.setHtmlInterface(mHtmlInterface);
        this.mTextGetter.setDefaultDrawable(mDrawable);
        this.setText(HHtml.fromHtml(html, mTextGetter, mTagHandle));
    }

    public void refreshHtml() {
        Log.d("common", "HtmlTextView刷新");
        if (mTextGetter != null) {
            this.setText(HHtml.fromHtml(currentHtml, mTextGetter, mTagHandle));
        }
    }

    /**
     * 自定义HTML标签
     */
    public void setTagHandle(IHtmlTagHandle mTagHandle) {
        // TODO 外部可以实现IHtmlTagHandle接口，添加标签的识别类型
        this.mTagHandle = mTagHandle;
    }

    /**
     * 通过Context 获取 Activity
     */
    public Activity getActivity(@NonNull Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }

        if (context instanceof ContextWrapper) {
            ContextWrapper wrapper = (ContextWrapper) context;
            return getActivity(wrapper.getBaseContext());
        }
        return null;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("common", "===========onAttachedToWindow");
        if (mTextGetter != null) {
            mTextGetter.handleDownload();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("common", "+++++++++++onAttachedToWindow");
        if (mTextGetter != null) {
            mTextGetter.onDestroy();
        }
    }
}