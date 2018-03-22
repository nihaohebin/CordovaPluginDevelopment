## 1、在配置plugim.xml时注意资源命名规范和路径规范  
```xml
例：
  <source-file src="src/android/LibraryProject/res/drawable/button_confirm_normal.xml" target-dir="res/drawable"/>
  <source-file src="src/android/LibraryProject/res/drawable/button_confirm_press.xml" target-dir="res/drawable"/>

  <source-file src="src/android/LibraryProject/res/drawable/dialog_close.png" target-dir="app/src/main/res/drawable-hdpi"/>
  <source-file src="src/android/LibraryProject/res/drawable/help_type_home.png" target-dir="app/src/main/res/drawable-hdpi"/>

  <!---布局文件-->
  <source-file src="src/android/LibraryProject/res/layout/activity_help_dialog.xml" target-dir="res/layout"/>
  <source-file src="src/android/LibraryProject/res/layout/activity_main.xml" target-dir="res/layout"/>
  <source-file src="src/android/LibraryProject/res/layout/activity_page_capture.xml" target-dir="res/layout"/>
  <!-- -音频文件 -->
  <source-file src="src/android/LibraryProject/res/raw/beep.ogg" target-dir="app/src/main/res/raw"/>
  <!---value文件-->
  <source-file src="src/android/LibraryProject/res/values/colors.xml" target-dir="res/values"/>
  <source-file src="src/android/LibraryProject/res/values/ids.xml" target-dir="res/values"/>
  <source-file src="src/android/LibraryProject/res/values/style.xml" target-dir="res/values"/>
```
- 1、资源文件命名尽可能以项目名称（或企业名称）+ 图片名称，避免与主工程资源覆盖或冲突,如：
```
 toone_icon_head
```
- 2、结尾为.png或.jpeg等媒体资源，target-dir为app/src/main/res + 资源类型，如：
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
- 4、配置java层级目录时尽可能以项目（或企业名称）+ 使用类型 + java ，如一个toone开发的相机插件：  
```xml
<source-file src="src/android/toone/CameraLauncher.java" target-dir="src/android/com/toone/v3/plugins/camera" />
```
对比原生相机插件，以避免与原有的java冲突：   
```xml
<source-file src="src/android/CameraLauncher.java" target-dir="src/org/apache/cordova/camera" />
```
