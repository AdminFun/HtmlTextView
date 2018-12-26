package com.fun.ex.app.htmltext.score;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import com.fun.ex.app.htmltext.interfase.IHtmlImageGetter;

import org.ccil.cowan.tagsoup.HTMLSchema;
import org.ccil.cowan.tagsoup.Parser;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/25
 * 修改: 2018/12/25
 * 版本: v1.0.0
 * 描述: 自定义HTML工具类，可以解决一些系统不能解决的问题：比如居中显示Image
 */
public class HHtml {

    private HHtml() {
    }

    private static class HtmlParser {
        private static final HTMLSchema schema = new HTMLSchema();
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Spanned fromHtml(String source, IHtmlImageGetter imageGetter, Html.TagHandler tagHandler) {
        return fromHtml(source, Html.FROM_HTML_MODE_LEGACY, imageGetter, tagHandler);
    }

    public static Spanned fromHtml(String source, int flags, IHtmlImageGetter imageGetter, Html.TagHandler tagHandler) {
        Parser parser = new Parser();
        try {
            parser.setProperty(Parser.schemaProperty, HtmlParser.schema);
        } catch (org.xml.sax.SAXNotRecognizedException e) {
            throw new RuntimeException(e);
        } catch (org.xml.sax.SAXNotSupportedException e) {
            throw new RuntimeException(e);
        }
        HTMLToSpannedConverter converter = new HTMLToSpannedConverter(source, imageGetter, tagHandler, parser, flags);
        return converter.convert();
    }
}