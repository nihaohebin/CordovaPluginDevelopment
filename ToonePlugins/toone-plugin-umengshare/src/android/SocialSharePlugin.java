package toone.v3.plugins.socialshare;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

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
 * 创建日期： 16/9/23
 * 说明：
 * 修改：
 */

public class SocialSharePlugin extends CordovaPlugin {

    private static final String TAG = "SocialSharePlugin";
    private CallbackContext callbackContext;
    private SHARE_MEDIA platform;
    private UMShareAPI mShareAPI;
    private ShareAction shareAction;
    private ShareBoardConfig config;
    private UMWeb web;
    private static final int SocialSharePlugin_PERMISSION_CODE = 1109;

    private final static String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.i(TAG, "shareListener  onStart platform = " + platform);
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.i(TAG, platform + "分享成功");
            Toast.makeText(cordova.getActivity(),platform.name() + "分享成功了",Toast.LENGTH_SHORT).show();
            callbackContext.success("success");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.i(TAG, "t = " + t.toString());
            Toast.makeText(cordova.getActivity(), "失败:" + t.getMessage(), Toast.LENGTH_LONG).show();
            callbackContext.error("fail");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(cordova.getActivity(), "取消了", Toast.LENGTH_LONG).show();
            Log.i(TAG, platform + "分享取消");
            callbackContext.error("cancel");
        }
    };


    private UMAuthListener umAuthListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.i(TAG, "authListener  onStart platform = " + platform);
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.i(TAG, "Authorize succeed  platform = " + platform + "\ndata = " + data.toString());

            String username = data.get("screen_name");
            String usid = data.get("openid");
            String iconURL = data.get("profile_image_url");

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", username);
                jsonObject.put("usid", usid);
                jsonObject.put("iconURL", iconURL);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            callbackContext.success(jsonObject);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(cordova.getActivity(), "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
            callbackContext.error("fail");
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
//            Toast.makeText(cordova.getActivity(), "取消了", Toast.LENGTH_LONG).show();
            callbackContext.error("cancel");
        }
    };

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        String weixinId = getXMLMetaData(cordova.getActivity(),"UMENG_WEIXIN_ID").trim();
        String weixinSecret = getXMLMetaData(cordova.getActivity(),"UMENG_WEIXIN_SECRET").trim();
        String sinaKey = getXMLMetaData(cordova.getActivity(),"UMENG_SINA_KEY").trim();
        String sinaSecret = getXMLMetaData(cordova.getActivity(),"UMENG_SINA_SECRET").trim();
        String qqId = getXMLMetaData(cordova.getActivity(),"UMENG_QQ_ID").trim();
        String qqKey = getXMLMetaData(cordova.getActivity(),"UMENG_QQ_KEY").trim();

//        Log.i("MainActivity","weixinId = " + weixinId + "  ---weixinSecret = " + weixinSecret + "\nsinaKey = " + sinaKey + "  ---sinaSecret = "+sinaSecret+"\nqqId = " + qqId+"  ---qqKey = "+qqKey);
        PlatformConfig.setWeixin(weixinId, weixinSecret);
        PlatformConfig.setQQZone(qqId,qqKey );
        PlatformConfig.setSinaWeibo(sinaKey, sinaSecret, "http://sns.whalecloud.com");


        //友盟初始化
        Config.DEBUG = true;
        UMShareAPI.get(cordova.getActivity().getApplication());
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.i(TAG, "action = " + action + "\nargs = " + args);
        this.callbackContext = callbackContext;

        if(hasPermisssion()){
            if ("share".equalsIgnoreCase(action)) {
                String description = args.getString(0);
                String title = args.getString(1);
                String url = args.getString(2);
                String thumb_url = args.getString(3);
                JSONArray platform = args.getJSONArray(4);
                SHARE_MEDIA[] plaforms = new SHARE_MEDIA[platform.length()];

                if (platform.length() != 0) {
                    for (int i = 0; i < platform.length(); i++) {
                        if (platform.getString(i).equals("wxsession")) {
                            plaforms[i] = SHARE_MEDIA.WEIXIN;
                        } else if (platform.getString(i).equals("wxtimeline")) {
                            plaforms[i] = SHARE_MEDIA.WEIXIN_CIRCLE;
                        } else if (platform.getString(i).equals("qzone")) {
                            plaforms[i] = SHARE_MEDIA.QZONE;
                        } else if (platform.getString(i).equals("qq")) {
                            plaforms[i] = SHARE_MEDIA.QQ;
                        } else if (platform.getString(i).equals("sina")) {
                            plaforms[i] = SHARE_MEDIA.SINA;
                        }
                    }
                } else {
                    Toast.makeText(cordova.getActivity(), "请添加分享平台", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if (TextUtils.isEmpty(url) || TextUtils.isEmpty(title) || TextUtils.isEmpty(thumb_url) || TextUtils.isEmpty(description)) {
                    Toast.makeText(cordova.getActivity(), "请检查参数是否填写完整", Toast.LENGTH_SHORT).show();
                    return false;
                }

                Log.i(TAG, "after thumb_url = " + thumb_url + "\nurl = " + url + "\ntitle = " + title);

                //1、配置分享链接
                web = new UMWeb(url);
                web.setTitle(title);//标题
                web.setThumb(new UMImage(cordova.getActivity(), thumb_url));  //缩略图
                web.setDescription(description);  //内容

                //2、配置分享面板
                config = new ShareBoardConfig();
                config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
                config.setCancelButtonVisibility(true);
                config.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Log.i(TAG, "分享面板消失了");
                    }
                });

                //3、配置分享内容
                shareAction = new ShareAction(cordova.getActivity())
                        .setDisplayList(plaforms)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                if (snsPlatform.mShowWord.equals("umeng_sharebutton_copy")) {
                                    Toast.makeText(cordova.getActivity(), "复制文本按钮", Toast.LENGTH_LONG).show();
                                } else if (snsPlatform.mShowWord.equals("umeng_sharebutton_copyurl")) {
                                    Toast.makeText(cordova.getActivity(), "复制链接按钮", Toast.LENGTH_LONG).show();

                                } else if (share_media == SHARE_MEDIA.SMS) {
                                    new ShareAction(cordova.getActivity())
                                            .withText("来自分享面板标题")
                                            .setPlatform(share_media)
                                            .setCallback(umShareListener)
                                            .share();
                                } else {
                                    new ShareAction(cordova.getActivity())
                                            .withMedia(web)
                                            .setPlatform(share_media)
                                            .setCallback(umShareListener)
                                            .share();
                                }
                            }
                        });

                //4、分享启动
                cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shareAction.open(config);//传入分享面板中
                    }
                });

            } else if ("auth".equalsIgnoreCase(action)) {
                String platforms = args.getString(0);
                if (platforms.startsWith("w") || platforms.startsWith("W")) {
                    platform = SHARE_MEDIA.WEIXIN;
                }
                if (platforms.startsWith("q") || platforms.startsWith("Q")) {
                    platform = SHARE_MEDIA.QQ;
                }
                if (platforms.startsWith("s") || platforms.startsWith("S")) {
                    platform = SHARE_MEDIA.SINA;
                }

                mShareAPI = UMShareAPI.get(cordova.getActivity().getApplicationContext());

                cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mShareAPI.doOauthVerify(cordova.getActivity(), platform, umAuthListener);
                    }
                });

            } else if ("isInstall".equalsIgnoreCase(action)) {
                if ("wechat".equalsIgnoreCase(args.getString(0))) {
                    boolean isInstall = isInstallApp(cordova.getActivity(), "com.tencent.mm");
                    callbackContext.success(isInstall + "");
                } else if ("qq".equalsIgnoreCase(args.getString(0))) {
                    boolean isInstall = isInstallApp(cordova.getActivity(), "com.tencent.mobileqq");
                    callbackContext.success(isInstall + "");
                } else {
                    callbackContext.success("false");
                }
            }

        }else{
            PermissionHelper.requestPermissions(this, SocialSharePlugin_PERMISSION_CODE, permissions);
        }
        return true;
    }

    /**
     * 获取Meta值
     */
    private String getXMLMetaData(Context context, String metaKey) {
        if (TextUtils.isEmpty(metaKey)) {
            return "";
        }
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(),pm.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String metaData = bundle.getString(metaKey);
            return metaData;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    private boolean isInstallApp(Activity activity, String packageName) {
        try {
            activity.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
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

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
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
            result = new PluginResult(PluginResult.Status.OK);
            callbackContext.sendPluginResult(result);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.i(TAG, "回调友盟接口");
        UMShareAPI.get(cordova.getActivity()).onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(cordova.getActivity()).release();
    }
}
