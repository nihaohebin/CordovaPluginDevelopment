## 1、在配置plugim.xml时注意资源命名规范和路径规范  
```xml
例：
  <source-file src="src/android/LibraryProject/res/drawable/button_confirm_normal.xml" target-dir="app/src/main/res/drawable"/>
  <source-file src="src/android/LibraryProject/res/drawable/button_confirm_press.xml" target-dir="app/src/main/res/drawable"/>

  <source-file src="src/android/LibraryProject/res/drawable/dialog_close.png" target-dir="app/src/main/res/drawable-hdpi"/>
  <source-file src="src/android/LibraryProject/res/drawable/help_type_home.png" target-dir="app/src/main/res/drawable-hdpi"/>

  <!---布局文件-->
  <source-file src="src/android/LibraryProject/res/layout/activity_help_dialog.xml" target-dir="app/src/main/res/layout"/>
  <source-file src="src/android/LibraryProject/res/layout/activity_main.xml" target-dir="app/src/main/res/layout"/>
  <source-file src="src/android/LibraryProject/res/layout/activity_page_capture.xml" target-dir="app/src/main/res/layout"/>
  <!-- -音频文件 -->
  <source-file src="src/android/LibraryProject/res/raw/beep.ogg" target-dir="app/src/main/res/raw"/>
  <!---value文件-->
  <source-file src="src/android/LibraryProject/res/values/colors.xml" target-dir="app/src/main/res/values"/>
  <source-file src="src/android/LibraryProject/res/values/ids.xml" target-dir="app/src/main/res/values"/>
  <source-file src="src/android/LibraryProject/res/values/style.xml" target-dir="app/src/main/res/values"/>
```
- 1、资源文件命名一定要以企业名称 + 项目名称 + 资源类型 +  资源名称，最大程度避免与主工程资源覆盖或冲突,如：
```
 toone_traffic_png_head.png
 toone_traffic_xml_name.xml
```
- 2、res资源，target-dir为app/src/main/res/ + 资源目录，如：
```xml
 <!-- 图片 -->
 <source-file src="src/android/LibraryProject/res/drawable/title_help.png" target-dir="app/src/main/res/drawable-hdpi"/>

 <!-- 音频文件 -->
 <source-file src="src/android/LibraryProject/res/raw/beep.ogg" target-dir="app/src/main/res/raw"/>
```
- 3、libsSO库资源，target-dir为app/libs/ + 架构包目录，如：
```xml
 <source-file src="libs/arm64-v8a/libscrypt_crypho.so" target-dir="app/libs/arm64-v8a/"/>
 <source-file src="libs/armeabi/libscrypt_crypho.so" target-dir="app/libs/armeabi/"/>
 <source-file src="libs/armeabi-v7a/libscrypt_crypho.so" target-dir="app/libs/armeabi-v7a/"/>
```
- 总结：在配置source-file文件的target-dir路径时是以工程android为相对根目录的。在配置lib-file时是以app/libs为相对根目录如：
```xml
<!-- 最终插件安装时会复制到app/libs目录下-->
<lib-file src="libs/xxx.jar" />
<!-- 等同以下配置-->
<source-file src="libs/xxx.jar" target-dir="app/libs">
```

- 4、仔细检查 source-file 无重复资源导入 错误配置如下：
```xml
<!-- 图片 -->
<source-file src="src/android/LibraryProject/res/drawable/title_help.png" target-dir="app/src/main/res/drawable-hdpi"/>
<source-file src="src/android/LibraryProject/res/drawable/title_help.png" target-dir="app/src/main/res/drawable-hdpi"/>
```
- 5、配置java层级目录时一定要以企业名称 + 项目名称 + 类型目录 ，如下target-dir配置：
```xml
<source-file src="src/android/toone/CameraLauncher.java" target-dir="src/android/com/toone/v3/plugins/camera" />
```
对比原生相机插件，最大程度与原工程java资源覆盖冲突：
```xml
<source-file src="src/android/CameraLauncher.java" target-dir="src/org/apache/cordova/camera" />
```
- 6、插件包名（插件包文件夹名称）要与插件pluginId一致，并且插件ID命名规则为企业名称 + 项目名称 + 插件名，如

```xml
     <plugin xmlns="http://apache.org/cordova/ns/plugins/1.0"
             id="cordova-plugin-toone-traffic-camera"
	     version="2.0.2">
```
- 7、在添加插件时，由于需要动态加入第三方APPKEY参数，如下可这么做：  

命令：cordova plugin add to/plugin/path --variable APP_KEY=hello --variable APP_SECRET=123456

```xml
<!-- 首先定义传参名,default为默认参数值-->
<preference name="APP_KEY" default="123" />
<preference name="APP_SECRET" default="ABC" />

<!-- 在android平台定义变量 -->
<platform name="android">

    <config-file parent="/*" target="res/xml/config.xml">
        <feature name="BadgerPlugin">
            <param name="android-package" value="toone.v3.plugins.badger.BadgerPlugin" />
        </feature>
    </config-file>

     <config-file target="AndroidManifest.xml" parent="/manifest/application">
          <meta-data android:name="APP_KEY" android:value="$APP_KEY" />
          <meta-data android:name="APP_SECRET" android:value="$APP_SECRET" />
    </config-file>
    <source-file src="src/android/BadgerPlugin.java" target-dir="src/toone/v3/plugins/badger" />
</platform>

```
执行完命令时，"hello"值便会赋值变量"$APP_KEY","123456"值便会赋值变量"$APP_SECRET";


