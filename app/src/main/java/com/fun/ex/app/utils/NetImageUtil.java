package com.fun.ex.app.utils;

import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 作者: Created by AdminFun
 * 邮箱: 614484070@qq.com
 * 创建: 2018/12/24
 * 修改: 2018/12/24
 * 版本: v1.0.0
 */
public class NetImageUtil extends AsyncTask<String, Integer, File> {

    private String httpUrl;
    private String imageName;
    private File localFile;
    private String failMsg = "";
    private OnImageDownloadListener downloadListener;

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setDownloadListener(OnImageDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!TextUtils.isEmpty(imageName)) {
            this.localFile = new File(Environment.getExternalStorageDirectory(), imageName);
        }
        if (downloadListener != null) {
            // TODO 该方法运行在主线程，一般用于显示进度条之类的操作
            downloadListener.onPreExecute();
        }
    }

    @Override
    protected File doInBackground(String... strings) {
        // 该方法是独立的子线程，执行耗时操作
        if (!TextUtils.isEmpty(httpUrl) && localFile != null) {
            excuteDownLoad();
        }
        return localFile;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(File s) {
        super.onPostExecute(s);
        if (downloadListener != null) {
            if (TextUtils.isEmpty(failMsg)) {
                downloadListener.onComplete(localFile);
            } else {
                downloadListener.onFailed(failMsg);
            }
        }
    }

    public interface OnImageDownloadListener {
        void onPreExecute();

        void onComplete(File file);

        void onFailed(String message);
    }

    private void excuteDownLoad() {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connUrl = (HttpURLConnection) url.openConnection();
            connUrl.setConnectTimeout(5000);
            connUrl.setRequestMethod("GET");
            if (connUrl.getResponseCode() == 200) {
                in = connUrl.getInputStream();
                out = new FileOutputStream(localFile);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } else {
                failMsg = "请求失败！";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
