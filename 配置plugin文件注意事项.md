## 1、在配置资源名称时注意命名规范  
```xml
例：
  <source-file src="src/android/LibraryProject/res/drawable/button_confirm_normal.xml" target-dir="res/drawable"/>
  <source-file src="src/android/LibraryProject/res/drawable/button_confirm_press.xml" target-dir="res/drawable"/>
  <source-file src="src/android/LibraryProject/res/drawable/button_uncheck.xml" target-dir="res/drawable"/>
  <source-file src="src/android/LibraryProject/res/drawable/progress_rotate.xml" target-dir="res/drawable"/>
  <source-file src="src/android/LibraryProject/res/drawable/selector_button.xml" target-dir="res/drawable"/>
  <source-file src="src/android/LibraryProject/res/drawable/shape_dialog.xml" target-dir="res/drawable"/>

  <source-file src="src/android/LibraryProject/res/drawable/dialog_close.png" target-dir="app/src/main/res/drawable-hdpi"/>
  <source-file src="src/android/LibraryProject/res/drawable/help_type_home.png" target-dir="app/src/main/res/drawable-hdpi"/>
  <source-file src="src/android/LibraryProject/res/drawable/help_type_home2.png" target-dir="app/src/main/res/drawable-hdpi"/>
  <source-file src="src/android/LibraryProject/res/drawable/help_type_vin.png" target-dir="app/src/main/res/drawable-hdpi"/>
  <source-file src="src/android/LibraryProject/res/drawable/help_type_vin2.png" target-dir="app/src/main/res/drawable-hdpi"/>
  <source-file src="src/android/LibraryProject/res/drawable/ic_launcher_round.png" target-dir="app/src/main/res/drawable-hdpi"/>
  <source-file src="src/android/LibraryProject/res/drawable/icon_arrow.png" target-dir="app/src/main/res/drawable-hdpi"/>
  <source-file src="src/android/LibraryProject/res/drawable/icon_input.png" target-dir="app/src/main/res/drawable-hdpi"/>
  <source-file src="src/android/LibraryProject/res/drawable/icon_light_close.png" target-dir="app/src/main/res/drawable-hdpi"/>
  <source-file src="src/android/LibraryProject/res/drawable/icon_light_open.png" target-dir="app/src/main/res/drawable-hdpi"/>
  <source-file src="src/android/LibraryProject/res/drawable/title_help.png" target-dir="app/src/main/res/drawable-hdpi"/>
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
- 资源文件命名尽可能以项目名称（或企业名称）+ 图片名称，避免与资源覆盖和冲突,如：
```
 toone_icon_head
```
- 结尾为.png或.jpeg等媒体资源，target-dir为app/src/main/res + 资源类型，如：
```xml
 <!-- 图片 -->
 <source-file src="src/android/LibraryProject/res/drawable/title_help.png" target-dir="app/src/main/res/drawable-hdpi"/>

 <!-- 音频文件 -->
 <source-file src="src/android/LibraryProject/res/raw/beep.ogg" target-dir="app/src/main/res/raw"/>
```
- 仔细检查 source-file 无重复资源导入 如：
```xml
<!-- 图片 -->
<source-file src="src/android/LibraryProject/res/drawable/title_help.png" target-dir="app/src/main/res/drawable-hdpi"/>
<source-file src="src/android/LibraryProject/res/drawable/title_help.png" target-dir="app/src/main/res/drawable-hdpi"/>
```
