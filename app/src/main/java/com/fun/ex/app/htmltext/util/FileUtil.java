package com.fun.ex.app.htmltext.util;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/26
 * 修改: 2018/12/26
 * 版本: v1.0.0
 * 描述: 文件工具类
 */
public class FileUtil {

    /**
     * 根据图片地址创建唯一的本地路径
     */
    public static File createImagePath(@NonNull String url, @Nullable String savePath) {
        String imageName = MD5.MD5(url) + ".png";
        File saveFile;
        if (TextUtils.isEmpty(savePath)) {
            saveFile = new File(Environment.getExternalStorageDirectory(), imageName);
        } else {
            if (!savePath.endsWith("/")) {
                savePath += "/";
            }

            final File temp = new File(savePath);
            if (!temp.exists()) {
                if (!temp.mkdir()) {
                    saveFile = new File(Environment.getExternalStorageDirectory(), imageName);
                } else {
                    saveFile = new File(savePath + imageName);
                }
            } else {
                saveFile = new File(savePath + imageName);
            }
        }
        return saveFile;
    }
}