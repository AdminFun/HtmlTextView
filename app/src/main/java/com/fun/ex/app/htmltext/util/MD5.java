package com.fun.ex.app.htmltext.util;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/25
 * 修改: 2018/12/25
 * 版本: v1.0.0
 */
public class MD5 {

    public static String MD5(String text) {
        if (!TextUtils.isEmpty(text)) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                byte[] md5Byte = md5.digest(text.getBytes("UTF8"));
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < md5Byte.length; i++) {
                    sb.append(HEX[(md5Byte[i] & 0xff) / 16]);
                    sb.append(HEX[(md5Byte[i] & 0xff) % 16]);
                }
                text = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                return text;
            } catch (Exception e) {
                return text;
            }
        }
        return text;

    }
}