## 插件plugin.xml文件配置规范(照着葫芦画个瓢)
```xml
<?xml version="1.0" encoding="UTF-8"?>  

<!-- 配置pluginId和pluginVersion-->
<!-- 插件包名（插件包文件夹名称）要与插件pluginId一致，插件id命名规范：企业名称 + 项目名称 + 插件名 -->
<plugin xmlns="http://www.phonegap.com/ns/plugins/1.0"
        xmlns:android="http://schemas.android.com/apk/res/android"
        id="cordova-plugin-toone-test-version"   
        version="1.0.0">
   
    <!--配置pluginName-->
    <name>AppVersion</name>
   
    <!--插件描述-->
    <description>
        This plugin will return the version of your App that you have set in
        packaging it. I.e. it will always match the version in the app store.
    </description>
  
    <!--插件证书-->
    <license>MIT</license>
   
    <!--插件支持cordova版本-->
    <engines>
        <engine name="cordova" version=">=6.0.0" />
    </engines>
  
    <!--插件js接口资源和全局调用关键字 clobbers命名规范：企业名称 + 项目 + 调用名 -->
    <js-module src="www/AppVersionPlugin.js">
       <clobbers target="toone.test.getAppVersion" />
    </js-module>
    
    <!-- 在添加插件时，由于需要动态加入第三方APPKEY参数 -->
    <!-- 命令：cordova plugin add to/plugin/path --variable APP_KEY=hello --variable APP_SECRET=123456-->
    <!-- 执行完命令时，在android的config-file标签中的value值中，"hello"值便会赋值变量"$APP_KEY","123456"值便会赋值变量"$APP_SECRET"-->
    <!-- 首先定义传参名,default为默认参数值-->
    <preference name="APP_KEY" default="123" />
    <preference name="APP_SECRET" default="ABC" />
    
    <!-- android 平台配置-->
    <platform name="android">
    
        <!-- 配置config.xml文件  该文件继承CordovaPlugin的java端接口资源-->
        <config-file target="res/xml/config.xml" parent="/*">
            <feature name="AppVersion">
                <param name="android-package" value="uk.co.whiteoctober.cordova.AppVersion"/>
            </feature>
        </config-file>
        
        <!-- 配置权限 -->
       <config-file target="AndroidManifest.xml" parent="/manifest" mode="merge">
            <uses-permission android:name="android.permission.RECEIVE_USER_PRESENT" />
            <uses-permission android:name="android.permission.INTERNET" />
            <uses-permission android:name="android.permission.WAKE_LOCK" />
       </config-file>
       
       <!-- 配置activity service receiver provider meta-data 等-->
       <config-file target="AndroidManifest.xml" parent="/manifest/application" mode="merge">
          
          <!-- activity -->
           <activity android:name="cn.jpush.android.ui.PushActivity"
               android:theme="@android:style/Theme.Translucent.NoTitleBar"
               android:configChanges="orientation|keyboardHidden">
               <intent-filter>
                   <action android:name="cn.jpush.android.ui.PushActivity" />
                   <category android:name="android.intent.category.DEFAULT" />
                   <category android:name="$PACKAGE_NAME" />
               </intent-filter>
           </activity>

            <!-- service -->
           <service android:name="cn.jpush.android.service.PushService"
             android:enabled="true"
             android:exported="false"
             android:process=":remote">
               <intent-filter>
                   <action android:name="cn.jpush.android.intent.REGISTER" />
                   <action android:name="cn.jpush.android.intent.PUSH_TIME" />
               </intent-filter>
           </service>

           <!-- receiver-->
           <receiver android:name="cn.jpush.android.service.PushReceiver"
             android:enabled="true"
             android:exported="false">
               <intent-filter android:priority="1000">
                   <!--Required  显示通知栏 -->
                   <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED_PROXY" />
                   <category android:name="$PACKAGE_NAME" />
               </intent-filter>
           </receiver>

           <!-- provider -->
           <provider
               android:authorities="$PACKAGE_NAME.DataProvider"
               android:name="cn.jpush.android.service.DataProvider"
               android:exported="true" />

           <!-- meta-data -->
           <meta-data android:name="APP_KEY" android:value="$APP_KEY" />
           <meta-data android:name="APP_SECRET" android:value="$APP_SECRET" />
       </config-file>
       
       <!-- 添加assets资源 -->
      <source-file src="sdk/android/lib/pingpp/libs/data.bin" target-dir="app/src/main/assets"/>
        
      <!--配置java层级目录命名规范：企业名称 + 项目名称 + 类型目录 -->
      <source-file src="src/android/toone/CameraLauncher.java" target-dir="src/cn/com/toone/v3/plugins/camera" />
        
      <!-- libsSO库资源，target-dir为app/libs/ + 架构包目录 -->
      <source-file src="libs/armeabi/toone_traffic_so_crypho.so" target-dir="app/src/main/jniLibs/armeabi/"/>
      <source-file src="libs/armeabi-v7a/toone_traffic_so_crypho.so" target-dir="app/src/main/jniLibs/armeabi-v7a/"/>
      
      <!-- 资源文件命名规范：企业名称 + 项目名称 + 资源名称，最大程度避免与主工程资源覆盖或冲突 -->
      <!-- res资源，target-dir为app/src/main/res/ + 资源目录-->
      <source-file src="src/android/LibraryProject/res/drawable/toone_traffic_name.xml" target-dir="app/src/main/res/drawable"/>
      <source-file src="src/android/LibraryProject/res/drawable/toone_traffic_head.png" target-dir="app/src/main/res/drawable-hdpi"/>
      <!---布局文件-->
      <source-file src="src/android/LibraryProject/res/layout/activity_toone_traffic_name.xml" target-dir="app/src/main/res/layout"/>
      <!-- -音频文件 -->
      <source-file src="src/android/LibraryProject/res/raw/toone_traffic_beep.ogg" target-dir="app/src/main/res/raw"/>
      <!---value文件-->
      <source-file src="src/android/LibraryProject/res/values/toone_traffic_colors.xml" target-dir="app/src/main/res/values"/>
      <source-file src="src/android/LibraryProject/res/values/toone_traffic_ids.xml" target-dir="app/src/main/res/values"/>
      <source-file src="src/android/LibraryProject/res/values/toone_traffic_style.xml" target-dir="app/src/main/res/values"/>
      <source-file src="src/android/LibraryProject/res/values/toone_traffic_strings.xml" target-dir="app/src/main/res/values"/>
      <!--注意：仔细检查 source-file 无重复资源导入-->
      
      <!--第二种导入方式-->
      <resource-file src="src/android/res/drawable-hdpi/toone_traffic_actionbar_divider.png" target="res/drawable-hdpi/toone_traffic_actionbar_divider.png" />
      <resource-file src="src/android/res/layout/toone_traffic_popwin_layout.xml"  target="res/layout/toone_traffic_layout.xml" />
        
      <!-- 配置jar包资源两种方式 -->
      <lib-file src="src/android/libs/xxx.jar" />
      <source-file src="libs/xxx.jar" target-dir="app/libs"/> 
      
      <!--插件 framework配置-->
      <framework src="com.google.code.gson:gson:2.8.2" />
    </platform>
</plugin>
```





