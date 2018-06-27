package com.toone.hello;

import android.app.Activity;
import android.content.Context;

/**
 * 使用介绍
 *
 * 初始化
 *   FakeR qrFakeR = new FakeR(this);
 *
 * 一些资源引用场景使用用例
 *   setContentView(qrFakeR.getId("layout","activity_qr_capture"));
 *   surfaceView = (SurfaceView) findViewById(qrFakeR.getId("id","preview_view"));
 *   imageView_cancel = (ImageView) findViewById(qrFakeR.getId("id","image_view_cancel"));
 *   getResources().openRawResourceFd(qrFakeR.getId("raw","beep"));
 *   getResources().getColor(qrFakeR.getId("color", "viewfinder_mask"));
 *
 *
 */
public class FakeR {
    private Context context;
    private String packageName;

    public FakeR(Activity activity) {
        context = activity.getApplicationContext();
        packageName = context.getPackageName();
    }

    public FakeR(Context context) {
        this.context = context;
        packageName = context.getPackageName();
    }

    public static int getId(Context context, String group, String key) {
        return context.getResources().getIdentifier(key, group, context.getPackageName());
    }

    public int getId(String group, String key) {
        return context.getResources().getIdentifier(key, group, packageName);
    }
}
