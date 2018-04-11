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
- 3、仔细检查 source-file 无重复资源导入 如：
```xml
<!-- 图片 -->
<source-file src="src/android/LibraryProject/res/drawable/title_help.png" target-dir="app/src/main/res/drawable-hdpi"/>
<source-file src="src/android/LibraryProject/res/drawable/title_help.png" target-dir="app/src/main/res/drawable-hdpi"/>
```
- 4、配置java层级目录时一定要以企业名称 + 项目名称 + 类型目录 ，如下target-dir配置：
```xml
<source-file src="src/android/toone/CameraLauncher.java" target-dir="src/android/com/toone/v3/plugins/camera" />
```
对比原生相机插件，最大程度与原工程java资源覆盖冲突：
```xml
<source-file src="src/android/CameraLauncher.java" target-dir="src/org/apache/cordova/camera" />
```
- 5、插件包名（插件包文件夹名称）要与插件pluginId一致，并且插件ID命名规则为企业名称 + 项目名称 + 插件名，如

```xml
     <plugin xmlns="http://apache.org/cordova/ns/plugins/1.0"
             id="cordova-plugin-toone-traffic-camera"
	         version="2.0.2">
```
