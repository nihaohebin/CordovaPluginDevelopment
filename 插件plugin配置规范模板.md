## 插件plugin.xml文件配置规范(照着葫芦画个瓢)
### 以下命名规范都是为了避免与原有资源冲突覆盖
```xml
<?xml version="1.0" encoding="UTF-8"?>  

<!-- 配置pluginId和pluginVersion-->
<!-- 插件包名（插件包文件夹名称）要与插件pluginId一致，插件id命名规范：企业名称 + 项目名称 + 插件名  如：toone-office-camera -->
<plugin 
   xmlns="http://www.phonegap.com/ns/plugins/1.0"
   xmlns:android="http://schemas.android.com/apk/res/android"
   id="toone-office-camera"   
   version="1.0.0">

<!--配置pluginName-->
<name>MyCamera</name>

<!--插件描述-->
<description>我的相机</description>

<!--插件证书-->
<license>MIT</license>

<!--插件支持cordova版本-->
<engines>
    <engine name="cordova" version=">=6.0.0" />
</engines>

<!-- src：指定js接口源路径，target：指定web端调用全局变量 -->
<!-- 命名规范：企业名称 + 项目名称 + 全局变量名  如：toone.office.opencamera -->
<js-module src="www/MyCamera.js">
    <clobbers target="toone.office.opencamera" />
</js-module>

<!-- 在添加插件时，由于需要动态加入第三方APPKEY参数时-->
<!-- 开发调试命令：cordova plugin add pluginPath(插件路径) --variable APP_KEY=hello --variable APP_SECRET=123456-->
<!-- 执行完命令时，在android的config-file标签中的value值中，"hello"值便会赋值变量"$APP_KEY","123456"值便会赋值变量"$APP_SECRET"-->
<!-- 首先定义传参名,default为默认参数值  必须配置默认值并不能填充 空值和中文-->
<preference name="APP_KEY" default="123" />
<preference name="APP_SECRET" default="ABC" />

<!-- android 平台配置-->
<platform name="android">
    <!-- 配置config.xml文件 ， value：指定android源代码java端接口（继承CordovaPlugin的Java类） 命名规范：企业名称 + 项目名称 + 分类名 + .java文件名  如：toone.office.camera.MyCamera  -->
    <config-file target="res/xml/config.xml" parent="/*">
        <feature name="MyCamera">
            <param name="android-package" value="toone.office.camera.MyCamera"/>
        </feature>
    </config-file>

    <!-- 配置AndroidManifest.xml文件  ， 权限 -->
    <config-file target="AndroidManifest.xml" parent="/manifest" mode="merge">
        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="android.permission.WAKE_LOCK" />
    </config-file>

    <!-- 配置AndroidManifest.xml文件  ， activity，service，receiver，provider，meta-data 等-->
    <config-file target="AndroidManifest.xml" parent="/manifest/application" mode="merge">
        <!-- activity -->
        <activity 
            android:name="cn.com.vapp.MainActivity"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"
            android:configChanges="orientation|keyboardHidden">
            <intent-filter>
                <action android:name="cn.com.vapp.ui.MainActivity" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>

        <!-- service -->
        <service 
            android:name="cn.com.vapp.service.MainService"
            android:enabled="true"
            android:exported="false"
            android:process=":remote">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.REGISTER" />
                <action android:name="cn.jpush.android.intent.PUSH_TIME" />
            </intent-filter>
        </service>

        <!-- receiver-->
        <receiver
            android:name="cn.jpush.android.service.PushReceiver"
            android:enabled="true"
            android:exported="false">
            <intent-filter android:priority="1000">
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

    <!--添加java资源，路径命名规范：企业名称 + 项目名称 + 分类名  如：toone/office/camera  
    注意：java文件都必须以开发调试路径一致，并且继承CordovaPlugin的Java类的资源路径必须与上述配置config.xml的vaule值相对应 -->
    <source-file src="src/android/toone/MyCamera.java" target-dir="src/toone/office/camera" />

    <!-- so库资源，target-dir为app/libs/ + 架构包目录  文件名命名规范：企业名称 + 项目名称 + 资源名称 如：toone_office_camera.so-->
    <source-file src="libs/armeabi/toone_office_camera.so" target-dir="app/libs/armeabi"/>
    <source-file src="libs/armeabi-v7a/toone_office_camera.so" target-dir="app/libs/armeabi-v7a"/>

    <!-- 配置jar包资源两种方式 -->
    <lib-file src="src/android/libs/xxx.jar" />
    <source-file src="libs/xxx.jar" target-dir="app/libs"/> 

    <!--插件 framework配置-->
    <framework src="com.google.code.gson:gson:2.8.2" />

    <!-- 资源文件命名规范：企业名称 + 项目名称 + 资源名称 如：toone_office_camera.png -->
    <!-- res资源，target-dir为app/src/main/res/ + 资源目录-->
    <source-file src="src/android/LibraryProject/res/drawable/toone_office_camera2.xml" target-dir="app/src/main/res/drawable"/>
    <source-file src="src/android/LibraryProject/res/drawable/toone_office_camera.png" target-dir="app/src/main/res/drawable-hdpi"/>
 
    <!---布局文件-->
    <source-file src="src/android/LibraryProject/res/layout/activity_toone_office_cameraxml" target-dir="app/src/main/res/layout"/>
 
    <!-- -音频文件 -->
    <source-file src="src/android/LibraryProject/res/raw/toone_office_beep.ogg" target-dir="app/src/main/res/raw"/>
  
    <!---value文件-->
    <source-file src="src/android/LibraryProject/res/values/toone_office_colors.xml" target-dir="app/src/main/res/values"/>
    <source-file src="src/android/LibraryProject/res/values/toone_office_theme.xml" target-dir="app/src/main/res/values"/>
    <source-file src="src/android/LibraryProject/res/values/toone_office_style.xml" target-dir="app/src/main/res/values"/>
    <source-file src="src/android/LibraryProject/res/values/toone_officec_strings.xml" target-dir="app/src/main/res/values"/>
    <!--注意：仔细检查 source-file 无重复资源导入-->

    <!--第二种资源导入方式-->
    <resource-file src="src/android/res/drawable-hdpi/toone_traffic_actionbar_divider.png" target="res/drawable-hdpi/toone_traffic_actionbar_divider.png" />
    <resource-file src="src/android/res/layout/toone_traffic_popwin_layout.xml"  target="res/layout/toone_traffic_layout.xml" />

</platform>
</plugin>
```
