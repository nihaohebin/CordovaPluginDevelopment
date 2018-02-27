# 插件包中plugin.xml文件详细配置介绍
### plugin.xml  
    plugin.xml文件定义了你的插件所需的结构和设置。它有几个元素来提供有关你的插件的详细信息。

### plugin  
    这个plugin元素是插件清单的顶级元素。

| 属性（类型） | 描述  |
| :--- |  :---   |  
| xmlns(string)    |  该插件的命名空间：<http://apache.org/cordova/ns/plugins/1.0>。如果该文件包含来自其他名称空间，如要加入到Android中的情况下，AndroidManifest.xml文件标记的XML，这些命名空间也应包括在该元素。 |
| id(string)   |  标识符插件。   |
|version(string)	|该插件的版本号.|

**例子:**
```xml
<plugin xmlns="http://www.phonegap.com/ns/plugins/1.0"
        xmlns:android="http://schemas.android.com/apk/res/android"
        id="cordova-plugin-app-version"
        version="0.1.9">
```
- id：插件ID 在3.2 由pluginID确定
- version：插件版本 在3.2 由version确定  

### engines and engine  
    在<engines>元素的子元素指定此插件支持基于cordova的Apache框架版本。

| 属性(类型) | 描述 |
|:---|:---|
|name(string)|引擎的名称。以下是所支持的默认引擎：cordova-android,cordova-ios等等，您也可以除了默认的指定自定义框架。|
|version(string)|你的框架必须拥有安装的版本|

**例子：**  
```xml
    <engines>
         <engine name="cordova-android" version=">=6.3.0" />
    </engines>
```
engines元件也可以使用'>'，'>='等，以避免重复指定模糊匹配，并且当底层的平台被更新，以减少维护。

```xml
<name>AppVersion</name>

<description>
    This plugin will return the version of your App that you have set in
    packaging it. I.e. it will always match the version in the app store.
</description>

<license>MIT</license>

<keywords>cordova,notification</keywords>

<repo>https://git-wip-us.apache.org/repos/asf/cordova-plugin-dialogs.git</repo>

<issue>https://issues.apache.org/jira/browse/CB/component/12320642</issue>  
```
- name：插件名 在3.2 由pluginName确定
- description：插件描述
- license：插件认证
- keywords：关键字
- repo：插件下载远程地址
- issue：插件反馈远程地址

### asset
    这个元素用来列出文件或目录被复制到cordova的应用程序的www目录。嵌套内的任何<asset>元素<platform>元素指定特定于平台的网络资源。

|属性(类型)|描述|
|:---|:---|
|src(string)|源文件或目录位于插件包，相对于该插件包www下的 js文件。|
|target(string)|在该文件或目录应位于cordova应用程序，相对于android工程www目录。|

**例子：**
```xml
<!-- 源文件复制到目标文件路径 -->
<asset src="www/foo.js" target="foo.js" />
<!-- 源目录复制到目标目录 -->
<asset src="www/foo" target="foo" />
```
asset可以设置为有针对性地子目录为好。这将创建www目录内的js/experimental，除非已经存在，并复制新foo.js文件，重命名为foo.js.

```xml
<asset src="www/new-foo.js" target="js/experimental/foo.js" />
```
### js-module

     大部分插件包括一个或多个JavaScript文件。每个JS-module标签对应一个JavaScript文件,并防止插件的用户不必添加script标记为每个文件。不要用保鲜cordova.define
     的文件，因为它会自动添加。该模块被包裹在一个闭包，用模块，出口和范围要求，因为是正常的AMD模块。嵌套JS-module在platform声明特定平台的JavaScript绑定的模块元素。

| 属性(类型)|描述|  
|:-----|:---|
|src(string)|参考相对该插件包www下 js文件。|
|name(string)|一般与该 js文件名一致。|

### clobbers
    JS-module元素内标记。用于指定module.exports被插入在window对象的命名空间。你可以有很多的clobbers只要你喜欢。创建window上的任何对象不可用。

|属性（类型）|描述|
|:-----|:---|
|target(string|module.exports被插入的命名空间。(指定前端调用关键字)|

**例子：**
```xml
<js-module src="www/AppVersionPlugin.js" name = "AppVersionPlugin">
   <clobbers target="cordova.getAppVersion" />
</js-module>
```
- src：指代 该 js 文件所在当前插件包相对路径
- target：指代api调用接口关键字

### merges
    JS-module元素内标记。用来指定在哪里module.exports获取与任何现有的价值合并window对象的命名空间。如果已经存在，模块的版本取代原来的。你可以有很多的merges
    只要你喜欢。创建window上的任何对象不可用。

|属性（类型）|描述|
|:-----|:---|
|target(string|这module.exports命名空间被合并。|

**例子：**
```xml
<js-module src="socket.js" name="Socket">
     <merges target="chrome.socket" />
</js-module>
```
这里module.exports得到与window.chrome.socket的任何现有值合并。

### runs
    runs 是JS-module元素内标记。这意味着你的代码应与cordova.require指定，但窗口对象上没有安装。初始化模块时，附加的事件处理程序或其他方式，这非常有用。您最多
    只能有一个runs标记。请注意，包括runs与<clobbers/>或<merges/>是多余的，因为它们也cordova.require您的模块。

**例子：**
```xml
<js-module src="socket.js" name="Socket">
     <runs/>
</js-module>
```
### dependency
    在dependency标签允许你指定在其当前插件依赖其他插件。该插件被其独特的NPM的ID或URL GitHub的引用。

|属性（类型）|描述|
|:-----|:---|
|Attributes(type) Descriptionid(string)	|提供插件的ID。|
|url(string)	|一种插件URL。这应该引用一个Git仓库，其中CLI尝试克隆|
|commit(string)	|这是git的结帐理解的任何git的参考：一个分支或标记的名称,(e.g., master, 0.3.1), or a commit hash (e.g., 975ddb228af811dd8bb37ed1dfd092a3d05295f9)|
|subdir(string)|指定目标插件存在依赖的Git仓库的子目录。这是有帮助的，因为它允许包含几个相关的插件库，每个单独指定。-----如果设置了dependency>标签的网址“”。并提供一个子目录，依赖插件是从同一个本地或远程的Git仓库为指定的dependency标记父插件安装。-------请注意，子目录始终指定相对于git仓库，不是父插件的根目录的路径。这是真实的，即使你安装了一个直接本地路径后援CLI插件找到Git仓库的根目录，然后从那里找到的其他插件。|
|version(string)|该插件的版本依赖。|

**例子：**
```xml
<dependency id="cordova-plugin-someplugin" url="https://github.com/myuser/someplugin" commit="428931ada3891801" subdir="some/path/here" />
<dependency id="cordova-plugin-someplugin" version="1.0.1">
```
### platform
    标识具有相关联的本机代码或需要修改其配置文件的平台。工具使用这个规范可以识别支持的平台和代码安装到cordova项目。无platform标记插件被假定为JavaScript的唯一
    的，因此在所有平台安装。

|属性(类型)|描述|
|:-----|:---|
|name(string)|允许值:ios, android, blackberry10, amazon-fireos, wp8, windows标识为支撑，其子元素与平台相关联的平台。|

### source-file
    标识应安装到一个项目中的可执行文件的源代码。

|属性(类型)|描述|
|:-----|:---|
|src(string)|需要相对于该插件包到文件的位置。|
|target-dir(string)|目录到其中的文件应该被复制，相对于cordova项目的根。在实践中，这是基于Java的平台，其中，在所述com.alunny.foo包的文件必须位于Ccom/alunny/foo目录最重要的。对于平台所在的源目录并不重要，这个属性应该被忽略。|
|framework(boolean)|默认值：false iOS版 如果设置为true，还增加了指定的文件作为该项目的框架。|
|compiler-flags(string)|iOS 如果设置，将指定的编译器选项为特定的源文件|

**例子：**
```xml
<!-- android -->
<source-file src="src/android/Foo.java" target-dir="src/com/alunny/foo" />
<!-- ios -->
<source-file src="src/ios/CDVFoo.m" />
<source-file src="src/ios/someLib.a" framework="true" />
<source-file src="src/ios/someLib.a" compiler-flags="-fno-objc-arc" />
```
### header-file
    这就像source-file元素，但专门为平台，例如iOS和Android的源文件，头文件和资源加以区分。这不是由Windows支持。

|属性(类型)|描述|
|:-----|:---|
|src(string)|需要 相对于该插件包到文件的位置。|
|target(string)	|路径在该文件将在你的目录进行复制。|

**例子：**
```xml
<!-- ios -->
<header-file src="CDVFoo.h" />
```
### resource-file
    这就像source-file元素，但专门为平台，例如iOS和Android的源文件，头文件和资源加以区分。

|属性(类型)|描述|
|:-----|:---|
|src(string)|需要 相对于该插件包到文件的位置。|
|target(string)	|路径在该文件将在你的目录进行复制。|

**例子：**
```xml
<!-- android -->
<resource-file src="FooPluginStrings.xml" target="res/values/FooPluginStrings.xml" />
```
### config-file
    标识一个基于XML的配置文件进行修改，修改AndroidManifest.xml配置文件，修改与此元素的两个文件类型是XML和的plist文件。在配置文件中的元素只允许新的配置信息追加
    到XML文档树。子项在目标文档中插入XML文本。

|属性(类型)|描述|
|:-----|:---|
|target(string)|该文件被修改，并且相对于cordova项目的根的路径。如果指定的文件不存在，该工具会忽略配置变化，并继续安装。目标可以包括通配符（*）的元素。在这种情况下，CLI递归项目的目录结构进行搜索，并使用第一个匹配。在iOS上，配置的位置，相对于项目根目录是不知道，因此指定config.xml中的目标文件解析到cordova-ios-project/MyAppName/config.xml中。|
|parent(string)	|一个XPath选择引用的元素的父要添加到配置文件。如果您使用的绝对选择，你可以使用通配符（*）指定的根元素，例如/ * /插件。如果选择器不能解决到指定文档的孩子，工具停止并反转安装过程中，会发出警告，并用非零代码退出。对于文件的plist，父在什么父键指定的XML应插入决定。|
|after(string)|接受兄弟姐妹的优先列表之后，添加XML片段。可用于指定在需要这样的XML元素的严格的顺序文件中的更改。|

**例子：**
```xml
<!-- android -->
<config-file target="AndroidManifest.xml" parent="/manifest/application">
 <activity android:name="com.foo.Foo" android:label="@string/app_name">
   <intent-filter>
   </intent-filter>
 </activity>
</config-file>

<!-- ios -->
<config-file target="*-Info.plist" parent="CFBundleURLTypes">
<array>
  <dict>
      <key>PackageName</key>
      <string>$PACKAGE_NAME</string>
  </dict>
</array>
</config-file>
```
**完整例子**
```xml
<!-- android -->
<platform name="android">
    <config-file target="res/xml/config.xml" parent="/*">
        <feature name="AppVersion">
            <param name="android-package" value="uk.co.whiteoctober.cordova.AppVersion"/>
        </feature>
    </config-file>
    <source-file src="src/android/AppVersion.java" target-dir="src/uk/co/whiteoctober/cordova" />
</platform>
```
- config-file：与Java交互的 js 配置文件，param属性的value值代表java文件位置
- source-file：src值代表JAVA源文件路径 target-dir值代表目标工程目录

### plugins-plist
    指定键和值将追加到在在iOScordova项目的正确AppInfo.plist文件。这是过时，因为它仅适用于cordova瓦-IOS2.2.0及以下。使用config-file标记cordova的较新版本。

**例子：**
```xml
<!-- ios -->
<plugins-plist key="Foo" string="CDVFoo" />
```

### lib-file
    链接源，资源和头文件。

|属性(类型)|描述|
|:-----|:---|
|src(string)|需要 相对于文件的位置到plugin.xml。|
|arch(string)	|对于其中的.so文件已建成的建筑，无论是设备或模拟器。对于Windows，则表示该<SDKReference>构建为指定的结构时只应被包括在内。支持的值是86，64或ARM。|

**例子：**
```xml
<lib-file src="src/BlackBerry10/native/device/libfoo.so" arch="device" />
<lib-file src="src/BlackBerry10/native/simulator/libfoo.so" arch="simulator" />
```
### framework
    标识的框架（通常是系统/平台的一部分）该插件依赖。

|属性(类型)|描述|
|:-----|:---|
|src(string)|需要 系统框架或者被包括作为你的插件文件的一部分给一个相对路径名。|
|custom(boolean)|表示框架是否是作为你的插件文件的一部分。|
|weak(boolean)|默认值：false 指示是否该框架应弱链接。|
|type(string)	|表示框架添加的类型。|
|parent(string)	|默认值：设置到包含子项目在其中添加参考的目录的相对路径。默认。意味着应用程序项目。|

**例子：**
```xml
<!-- android -->
<!-- Depend on latest version of GCM from play services -->
<framework src="com.google.android.gms:play-services-gcm:+" />
<!-- Depend on v21 of appcompat-v7 support library -->
<framework src="com.android.support:appcompat-v7:21+" />
<!-- Depend on library project included in plugin -->
<framework src="relative/path/FeedbackLib" custom="true" />

<!-- ios -->
<framework src="libsqlite3.dylib" />
<framework src="social.framework" weak="true" />
<framework src="relative/path/to/my.framework" custom="true" />
```
在Android（如cordova-android@4.0.0的），使用框架的标签，包括Maven依赖，或者包括捆绑的库项目。

### info
    向用户提供更多的信息。当你需要不容易自动或超出了CLI的范围，额外的步骤，这是非常有用的。此标记的内容被打印出来的时候，CLI安装插件。

**例子：**
```xml
<info>
  You need to install __Google Play Services__ from the `Android  Extras` section using the Android SDK manager (run `android`).

  You need to add the following line to the `local.properties`:

  android.library.reference.1=PATH_TO_ANDROID_SDK/sdk/extras/google/google_play_services/libproject/google-play-services_lib
</info>
```

### hook
    表示将由cordova当某些行为发生时被调用自定义脚本（例如，插件添加或平台准备逻辑后调用）。当你需要扩展默认cordvoa的功能，
    这非常有用。

**例子：**
```xml
<hook type="after_plugin_install" src="scripts/afterPluginInstall.js" />
```

### uses-permission
    在某些情况下，插件可能需要使依赖于目标应用程序配置的改变。例如，要在Android，应用程序的包ID是我-APP-ID将需要的权限，如注册C2DM：

**例子：**
```xml
<uses-permission android:name="my-app-id.permission.C2D_MESSAGE"/>
```
在从plugin.xml文件中插入的内容是不是提前知道这样的情况下，变量可以通过一个美元符号后面是一系列大写字母，数字或下划线的表示。对于上面的例子中，plugin.xml文件将包括此标记：
```xml
<uses-permission android:name="$PACKAGE_NAME.permission.C2D_MESSAGE"/>
```
如果没有找到该CLI替换指定的值，或空字符串变量的引用。可变基准的值可以被检测（在此情况下，从AndroidManifest.xml文件），或者由工具的用户指定;确切的过程是依赖于特定的工具。

Plugman可以要求用户指定插件的必需的变量。例如，对于C2M和谷歌地图API密钥可以被指定为一个命令行参数：

    plugman --platform android
    --project /path/to/project
    --plugin name|git-url|path
    --variable
    API_KEY=!@CFATGWE%^WGSFDGSDFW$%^#$%YTHGsdfhsfhyer56734

### preference
    正如上一节中看到的，有时插件可能需要用户为他们的变量赋值。为了使这些变量强制性的，在<平台>标签必须包含一个<优先>标记。在CLI检查，这些要求的偏好中通过。如果
    不是，则应当警告用户如何在可变和出口通过带有非零码。

|属性(类型)|描述|
|:-----|:---|
|name(string)|需要 变量的名称..|
|default(string)|变量的默认值。如果存在的话，它的值将被使用，并没有错误将万一用户发出不输入任何值。|

**例子：**
```xml
<preference name="API_KEY" default="default-value" />
```