package toone.v3.plugins.webview;

import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieSyncManager;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.PluginEntry;

import java.util.ArrayList;
import java.util.Locale;

import toone.main.MainActivity;

public class TooneWebViewActivity extends CordovaActivity {

    private static TooneWebViewActivity instance;
    public static final String integrationIndexHtml = "file:///android_asset/www/integrationIndex.html";
    private final String TAG = "TooneWebViewActivity";
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

        //启用Cordova应用程序在后台启动
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }

        String type = getIntent().getStringExtra(TooneWebViewPlugin.extra_type);

        CookieSyncManager.createInstance(getApplicationContext());

        if ("H5PAGE".equalsIgnoreCase(type)) {
            isH5Page = true;
            if (appView == null) {
                super.init();
            }
            appView.loadUrl(integrationIndexHtml);
        } else {
            if (appView == null) {
                this.init();
            }
            appView.loadUrl(MainActivity.indexHtml);
            isH5Page = false;
        }
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
        CookieSyncManager.getInstance().stopSync();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void init() {
        appView = makeWebView();
        createViews();
        if (!appView.isInitialized()) {
            if (!isSplashScreenPlugin(pluginEntries)) {
                appView.init(cordovaInterface, pluginEntries, preferences);
            }
        }
        cordovaInterface.onCordovaInit(appView.getPluginManager());

        // Wire the hardware volume controls to control media if desired.
        String volumePref = preferences.getString("DefaultVolumeStream", "");
        if ("media".equals(volumePref.toLowerCase(Locale.ENGLISH))) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
        }
    }

    /**
     * 判断是否为闪屏插件
     *
     * @param pluginEntries
     * @return false 不是闪屏插件
     */
    private boolean isSplashScreenPlugin(ArrayList<PluginEntry> pluginEntries) {
        for (PluginEntry pluginEntry : pluginEntries) {
            if ("org.apache.cordova.splashscreen.SplashScreen".equalsIgnoreCase(pluginEntry.pluginClass)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.i(TAG, "hasFocus = " + hasFocus + "----isH5Page = " + isH5Page);
        if (hasFocus && isFirst) {
            if (isH5Page) {
                isFirst = false;
            } else {
                appView.loadUrl(MainActivity.indexHtml);
                isFirst = false;
            }
        }
    }
}
