package toone.v3.plugins.webview;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import toone.main.MainActivity;
import toone.util.SPUtil;
import toone.v3.plugins.systemconfig.SystemConfigPlugin;


/**
 * ┏┓　  　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　   ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　   ┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the CNM protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * 创建人：Joker
 * 创建日期： 2016/7/14
 * 说明：
 * 修改：
 */
public class TooneWebViewPlugin extends CordovaPlugin {
    //tooneWebView回调
    private final static int WEBVIEW_REQ_CODE = 112;
    //webView数量
    private final static String webViewCount = "webViewCount";
    private final static String TAG = "TooneWebViewPlugin";
    public static int i = 1;

    //webViewPlugin
    public final static String extra_type = "extra_type";
    public final static String extra_url= "extra_url";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.i(TAG, "action = " + action + "\nargs = " + args);

        if ("open".equalsIgnoreCase(action)) {

            String type = args.getJSONObject(0).getString("type");
            String url = args.getJSONObject(0).getString("url");
            if (!TextUtils.isEmpty(url)){
                url = url.substring(0,url.indexOf("?server_url="));
            }

            Intent intent = new Intent(cordova.getActivity(), TooneWebViewActivity.class);
            intent.putExtra(extra_type, type);
            intent.putExtra(extra_url,url);
            cordova.startActivityForResult(this, intent, WEBVIEW_REQ_CODE);

        } else if ("close".equalsIgnoreCase(action)) {
            if (!args.get(0).toString().equalsIgnoreCase("null")) {
                String returnValue = (String) args.get(0);
                Log.i(TAG, "retValue = " + returnValue);
                SPUtil.getInstance(MainActivity.getInstance()).putString(returnValue, returnValue);
            }
        }
        callbackContext.success("success");
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.i(TAG, "requestCode = " + requestCode + " ---- resultCode = " + resultCode);
        if (requestCode == WEBVIEW_REQ_CODE) {
            if (SPUtil.getInstance(cordova.getActivity()).getInt(webViewCount) == 2) {
                webView.loadUrl("javascript:cordova.plugins.webview.fireEvent(\"exit\"," + SPUtil.getInstance(MainActivity.getInstance()).getString(SystemConfigPlugin.returnValue) + ");");
                TooneWebViewPlugin.i = SPUtil.getInstance(MainActivity.getInstance()).getInt(webViewCount);
                TooneWebViewPlugin.i--;
                SPUtil.getInstance(cordova.getActivity()).putInt(webViewCount, TooneWebViewPlugin.i);
            } else {
                if (TooneWebViewActivity.getInstance() != null) {
                    webView.loadUrl("javascript:cordova.plugins.webview.fireEvent(\"exit\"," + SPUtil.getInstance(MainActivity.getInstance()).getString(SystemConfigPlugin.returnValue) + ");");
                    TooneWebViewPlugin.i = SPUtil.getInstance(MainActivity.getInstance()).getInt(webViewCount);
                    TooneWebViewPlugin.i--;
                    SPUtil.getInstance(cordova.getActivity()).putInt(webViewCount, TooneWebViewPlugin.i);
                }
            }
        }
    }

}
