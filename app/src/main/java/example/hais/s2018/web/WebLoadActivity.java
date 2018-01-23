package example.hais.s2018.web;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.hais.s2018.R;
import example.hais.s2018.base.BaseActivity;
import example.hais.s2018.utils.AppOperator;
import example.hais.s2018.utils.AssetsUtils;

public class WebLoadActivity extends BaseActivity {

    @Bind(R.id.web)
    WebView web;

    private String context = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_load);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);    //允许加载javascript
        //这个是给图片设置点击监听的，如果你项目需要webview中图片，点击查看大图功能，可以这么添加
        web.addJavascriptInterface(new JavaScriptInterface(this), "imagelistner");
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                context = AssetsUtils.getFromAssets(activity, "web.txt");
                //针对部分页面返回的图片属性设置过大，如1204px，会超出边框，统一转换为宽度百分比
                context = context.replace("<img", "<img style='max-width:100%;height:auto;'");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setWebData(context);
                    }
                });
            }
        });
    }


    private void setWebData(String webData) {
        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //遍历图片节点
                web.loadUrl("javascript:(function(){" +
                        "var objs = document.getElementsByTagName(\"img\"); " +
                        "var img=[];" +
                        "for(var y=0;y<objs.length;y++){" +
                        "img[y] = objs[y].src;" +
                        "}" +
                        "for(var i=0;i<objs.length;i++)  " +
                        "{"
                        +
                        "objs[i].onclick=function()  " +
                        "    {  "
                        + "        window.imagelistner.openImage(img);  " +
                        "    }  " +
                        "}" +
                        "})()");
            }
        });
        web.loadDataWithBaseURL(null, webData, "text/html", "utf-8", null);
    }

        public static class JavaScriptInterface {

        private Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(String[] imgs) {//String img
            Log.e("TAG", "响应点击事件!" + imgs.length);
            Intent intent = new Intent();
            intent.putExtra("image", imgs);
            intent.setClass(context, ViewPagerActivity.class);//BigImageActivity查看大图的类，自己定义就好
            context.startActivity(intent);
        }
    }
}
