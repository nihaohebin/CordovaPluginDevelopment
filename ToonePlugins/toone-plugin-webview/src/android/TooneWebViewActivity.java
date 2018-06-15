package toone.v3.plugins.webview;

import android.os.Bundle;
import android.util.Log;

import com.tencent.smtt.sdk.CookieSyncManager;

import org.apache.cordova.CordovaActivity;

import toone.main.MainActivity;
import toone.util.ScreenUtil;

public class TooneWebViewActivity extends CordovaActivity {

    private static TooneWebViewActivity instance;
    public static final String integrationIndexHtml = "file:///android_asset/www/integrationIndex.html";
    private final String TAG = "TooneWebViewPlugin";
    private boolean isFirst = true;
    private boolean isH5Page = true;

    public static TooneWebViewActivity getInstance() {
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        instance = this;
//        FakeR fakeR = new FakeR(this);
//        setContentView(fakeR.getId("layout", "activity_main"));

        //启用Cordova应用程序在后台启动
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }

        CookieSyncManager.createInstance(getApplicationContext());

        String type = getIntent().getStringExtra(TooneWebViewPlugin.extra_type);

        String url;

        if ("H5PAGE".equalsIgnoreCase(type)) {
            ScreenUtil.setFullScreen(this);
            isH5Page = true;
            url = getIntent().getStringExtra(TooneWebViewPlugin.extra_url);
        } else {
            ScreenUtil.quitFullScreen(this);
            isH5Page = false;
            url = integrationIndexHtml;
        }

        if (appView == null) {
            super.init();
        }
        appView.loadUrl(url);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        CookieSyncManager.getInstance().startSync();
//        CookieSyncManager.getInstance().sync();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
        CookieSyncManager.getInstance().stopSync();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.i(TAG, "hasFocus = " + hasFocus + "----isH5Page = " + isH5Page);
        if (hasFocus && isFirst) {
            isFirst = false;
            if (!isH5Page) {
                appView.loadUrl(MainActivity.indexHtml);
            }
        }
    }
}
