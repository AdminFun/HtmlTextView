# HTMLTextView

### 介绍
Android TextView在展示HTML富文本时，对img标签的支持不是很理想，会遇到如下几个问题：
<br>1、图片不能居中显示；
<br>2、图片的大小被缩小；
<br>3、图片是在ImageGetter中获取，ImageGetter不能和ImageSpan同时使用；
<br>该项目就是解决这几个问题的。

### 软件架构
##### 1、图片不能居中显示
    > android.text.Html 源码中对图片的处理使用了ImageSpan，而ImageSpan的对齐方式只有BottomLine和BaseLine两种，
    没有居中对齐方式。所以需要自定义ImageSpan，重写draw()方法，在draw方法中位移Y轴偏移量，X轴不变，
    使图片达到上下居中效果。

##### 2、图片尺寸被缩小
    > Drawable.setBounds()时，输入Bitmap的真实宽高即可防止图片被缩放。

##### 3、ImageGetter和ImageSpan的冲突
    > 上面说到android.text.Html源码中通过 ImageGetter 获取到Drawable后，使用了ImageSpan 将 Drawable 绘制到文本上，
    想要自定义ImageSpan，遂强行重写Html类，并将自定义 ImageSpan 强行注入到自定义Html类中，促成一段姻缘。。。


### 使用说明

**导入依赖**
导入项目前需要先导入一个依赖，此依赖并非是组件的地址！！！
```
implementation 'org.ccil.cowan.tagsoup:tagsoup:1.2.1'
```
**组件使用**
```
1、this.htmlTextView = this.findViewById(R.id.activity3_text1);
2、this.htmlTextView.setHtmlInterface(this) // 设置下载回调
3、    .setDefaultDrawable(R.drawable.mine_order_s1) // 设置默认图
4、    .setHtml(str); // 设置要展示的HTML字符串
```
**下载回调**
```
htmlTextView.invalidate(file);
```