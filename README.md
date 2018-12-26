# HTMLTextView

#### 介绍
Android TextView在展示HTML富文本时，对img标签的支持不是很理想，会遇到如下几个问题：
<br>1、图片不能居中显示；
<br>2、图片的大小被缩小；<br>
<br>3、图片是在ImageGetter中获取，ImageGetter不能和ImageSpan同时使用；
<br>该项目就是解决这几个问题的。

#### 软件架构
1、图片不能居中显示
    android.text.Html 源码中对图片的处理使用了ImageSpan，而ImageSpan的对齐方式只有BottomLine和BaseLine两种，没有居中。所以需要自定义ImageSpan，重写draw()方法，在draw方法中位移Y轴偏移量，X轴不变，使图片达到上下居中效果。
2、图片尺寸被缩小
    Drawable.setBounds()时，输入Bitmap的真实宽高即可防止图片被缩放。
3、ImageGetter和ImageSpan的冲突
    上面说到android.text.Html源码中通过 ImageGetter 获取到Drawable后，使用了ImageSpan 将 Drawable 绘制到文本上，遂强行重写Html类，并将自定义 ImageSpan 强行注入到自定义Html类中，促成一段姻缘。。。


#### 安装教程

1. xxxx
2. xxxx
3. xxxx

#### 使用说明

使用
 1、this.htmlTextView = this.findViewById(R.id.activity3_text1);
 2、this.htmlTextView.setHtmlInterface(this)             // 设置下载回调
 3、    .setDefaultDrawable(R.drawable.mine_order_s1)    // 设置默认图
 4、    .setHtml(str);                                   // 设置要展示的HTML字符串

 下载回调：
 htmlTextView.invalidate(file);

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)