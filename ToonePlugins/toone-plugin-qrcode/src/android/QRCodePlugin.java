package toone.v3.plugins.qrcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 *                 ┏┓　  　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　   ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 *          　   ┃　　　　　　　┃ + +
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
 * 创建日期： 2016/4/29
 * 说明： 二维码扫描
 * 修改：
 *
 */
public class QRCodePlugin extends CordovaPlugin {

    //二位扫描回调
    private static final int QR_REQ_CODE = 111;
    private static final int QR_PERMISSION_CODE = 110;
    private static final String TAG = "QRCodePlugin";
    private CallbackContext callbackContext;
    private final static String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final int code_launch_product_query = 1001;
    public static final int code_quit = 1002;
    public static final int code_restart_preview = 1003;
    public static final int code_return_scan_result = 1004;
    public static final int code_auto_focus = 1005;
    public static final int code_decode = 1006;
    public static final int code_decode_failed = 1007;
    public static final int code_decode_succeeded = 1008;
    public static final int code_encode_failed = 1009;
    public static final int code_ncode_succeeded = 1010;
    public static final int code_search_book_contents_failed = 1011;
    public static final int code_search_book_contents_succeeded = 1012;
    public static final int code_gridview = 1013;
    public static final int code_webview = 1014;


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.i(TAG, "action = " + action + "\nargs = " + args);
        this.callbackContext = callbackContext;
        if (hasPermisssion()){
            Intent intent = new Intent(cordova.getActivity(), MipcaActivityCapture.class);
            cordova.setActivityResultCallback(this);
            cordova.startActivityForResult(this, intent, QR_REQ_CODE);
        }else {
            PermissionHelper.requestPermissions(this, QR_PERMISSION_CODE, permissions);
        }

        return true;
    }

    public boolean hasPermisssion() {
        for (String p : permissions) {
            if (!PermissionHelper.hasPermission(this, p)) {
                return false;
            }
        }
        return true;
    }


    public void requestPermissions(int requestCode) {
        PermissionHelper.requestPermissions(this, requestCode, permissions);
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        PluginResult result;
        //This is important if we're using Cordova without using Cordova, but we have the geolocation plugin installed
        if (callbackContext != null) {
            for (int r : grantResults) {
                if (r == PackageManager.PERMISSION_DENIED) {
                    Log.i(TAG, "权限申请被拒绝!");
                    result = new PluginResult(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION);
                    callbackContext.sendPluginResult(result);
                    return;
                }
            }
            Intent intent = new Intent(cordova.getActivity(), MipcaActivityCapture.class);
            cordova.setActivityResultCallback(this);
            cordova.startActivityForResult(this, intent, QR_REQ_CODE);

            result = new PluginResult(PluginResult.Status.OK);
            callbackContext.sendPluginResult(result);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.i(TAG, "requestCode = " + requestCode + "----- resultCode = " + resultCode);
        if (requestCode == QR_REQ_CODE) {
            if (intent != null) {
                String resultValue = intent.getStringExtra(MipcaActivityCapture.extra_QR);
                if (!TextUtils.isEmpty(resultValue)) {
//                    if (TextUtil.isURL(resultValue)){
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("text", resultValue);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callbackContext.success(jsonObject);
//                    }else {
//                        Toast.makeText(MainActivity.this,"Invalid Iink",Toast.LENGTH_LONG).show();
//                    }
                }
            }
        }
    }
}
