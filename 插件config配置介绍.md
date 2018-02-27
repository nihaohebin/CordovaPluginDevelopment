# 插件config介绍
---
## config.xml文件详细说明
Config.xml是一个全局配置文件，用于控制Cordova应用程序行为的许多方面。这个与平台无关的XML文件是基于W3C的打包Web应用程序（Widgets）规范进行安排的，并且扩展为指定核心Cordova API功能，插件和平台特定的设置。

对于使用Cordova CLI创建的项目（在命令行界面中介绍），可以在顶层目录中找到该文件：  
```xml
app/config.xml
```
请注意，在版本3.3.1-0.2.0之前，文件存在于app/www/config.xml此处，并且在此处仍然受支持。
当使用CLI构建项目时，该文件的版本被动复制到各个platforms/子目录中。例如：
```xml
app/platforms/ios/AppName/config.xml
app/platforms/blackberry10/www/config.xml
app/platforms/android/res/xml/config.xml
```
**完整样例：**
```xml
<?xml version='1.0' encoding='utf-8'?>
<widget id="com.toone.test" version="1.0.0"
    xmlns="http://www.w3.org/ns/widgets">

    <name>CordovaTest</name>
    <description>
        A sample Apache Cordova application that responds to the deviceready event.
    </description>
    <author email="dev@cordova.apache.org" href="http://cordova.io">
        Apache Cordova Team
    </author>
    <content src="index.html" />
    <access origin="*" />
    <allow-intent href="http://*/*" />
    <allow-intent href="https://*/*" />
    <allow-intent href="tel:*" />
    <allow-intent href="sms:*" />
    <allow-intent href="mailto:*" />
    <allow-intent href="geo:*" />
    <allow-intent href="market:*" />
    <preference name="loglevel" value="DEBUG" />

    <feature name="Whitelist">
        <param name="android-package" value="org.apache.cordova.whitelist.WhitelistPlugin" />
        <param name="onload" value="true" />
    </feature>

    <feature name="AppVersion">
        <param name="android-package" value="uk.co.whiteoctober.cordova.AppVersion" />
    </feature>

    <feature name="Device">
        <param name="android-package" value="org.apache.cordova.device.Device" />
    </feature>
</widget>

```

### widget
    config.xml文件的根元素。

|属性(类型)|描述|
|:-----|:---|
|id(string)|必需，指定应用程序的反向域标识符，以及version以主要/次要/补丁符号表示的完整版本号。|
|version(string)|必填，完整版本号以主要/次要/补丁符号表示。|
|android-versionCode(string)|Android的替代版本。设置应用程序的版本代码。|
|ios-CFBundleVersion(string)|iOS的替代版本。|
|osx-CFBundleVersion(string)|OS X的替代版本。|
|windows-packageVersion(string)|Windows的替代版本。|
|packageName(string)|Windows的软件包名称。|
|xmlns(string)|命名空间为config.xml文档。|
|xmlns:cdv(string)|命名空间前缀。|

**例子：**
```xml
<!-- Android -->
<widget id="io.cordova.hellocordova" version="0.0.1" android-versionCode="0.1.3" xmlns="http://www.w3.org/ns/widgets" xmlns:cdv="http://cordova.apache.org/ns/1.0">
</widget>

<!-- iOS -->
<widget id="io.cordova.hellocordova" version="0.0.1" ios-CFBundleVersion="0.1.3" xmlns="http://www.w3.org/ns/widgets" xmlns:cdv="http://cordova.apache.org/ns/1.0">
</widget>
```
### name
    指定应用程序的正式名称，因为它出现在设备的主屏幕和应用程序商店界面中。

**例子：**
```xml
<widget ...>
  <name>HelloCordova</name>
</widget>
```
### description
    指定应用程序的正式名称，因为它出现在设备的主屏幕和应用程序商店界面中。

**例子：**
```xml
<widget ...>
  <description>A sample Apache Cordova application</description>
</widget>
```
### author
    指定可能出现在应用商店lisitng中的联系信息。

|属性(类型)|描述|
|:-----|:---|
|email(string)|作者所需的电子邮件。|
|href(string)|作者所需的网站。|

**例子：**
```xml
<widget ...>
  <author email="dev@cordova.apache.org" href="http://cordova.io"></author>
</widget>
```
### content
    在顶级Web资产目录中定义应用的起始页面。默认值是index.html，通常出现在项目的顶级www目录中。

|属性(类型)|描述|
|:-----|:---|
|email(string)|必需，在顶级Web资产目录中定义应用程序的起始页面。默认值是index.html，习惯上|
出现在项目的顶层www目录中。

**例子：**
```xml
<widget ...>
  <content src="startPage.html"></content>
</widget>
```
### access
    定义应用程序允许与之通信的一组外部域。上面显示的默认值允许它访问任何服务器。有关详细信息，请参阅域白名单指南。

|属性(类型)|描述|
|:-----|:---|
|origin(string)|必需，定义应用程序允许与之通信的一组外部域。|
上面显示的默认值允许它访问任何服务器。有关详细信息，请参阅域白名单指南。

**例子：**
```xml
<widget ...>
    <access origin="*"></content>
</widget>

<widget ...>
    <access origin="http://google.com"></content>
</widget>
```
### allow-navigation
    控制WebView本身可以导航到哪个URL。仅适用于顶级导航。

|属性(类型)|描述|
|:-----|:---|
|href(string)|必需，定义WebView允许导航到的一组外部域。|

**例子：**
```xml
<!-- Allow links to example.com -->
<allow-navigation href="http://example.com/*" />

<!-- Wildcards are allowed for the protocol, as a prefix to the host, or as a suffix to the path -->
<allow-navigation href="*://*.example.com/*" />
```
### allow-intent
    控制允许应用程序打开系统的URL。默认情况下，不允许使用外部URL。

|属性(类型)|描述|
|:-----|:---|
|href(string)|必需，定义应用程序允许系统打开的URL。|

**例子：**
```xml
<allow-intent href="http://*/*" />
<allow-intent href="https://*/*" />
<allow-intent href="tel:*" />
<allow-intent href="sms:*" />
```
### engine
    指定准备过程中要恢复的平台的详细信息。

|属性(类型)|描述|
|:---|:---|
|name(string)|必需，要恢复的平台的名称。|
|spec(string)|必需，有关要恢复的平台的详细信息。这可能是一个major.minor.patch版本号，一个包含平台的目录或一个指向git仓库的url。这些信息将用于检索从NPM，本地目录或git存储库中恢复的平台代码。|

**例子：**
```xml
<engine name="android" spec="https://github.com/apache/cordova-android.git#5.1.1" />
<engine name="ios" spec="^4.0.0" />
```
### plugin
    指定准备过程中要恢复的插件的详细信息。

|属性(类型)|描述|
|:-----|:---|
|name(string)|必需，要恢复的插件的名称。|
|spec(string)|必需，有关要恢复的插件的详细信息。这可能是一个major.minor.patch版本号，一个包含插件的目录或一个指向git仓库的url。这些信息将被用来检索从NPM，本地目录或者git仓库中恢复的插件代码。|

**例子：**
```xml
<plugin name="cordova-plugin-device" spec="^1.1.0" />
<plugin name="cordova-plugin-device" spec="https://github.com/apache/cordova-plugin-device.git#1.0.0" />
```
### preference
    将各种选项设置为成对的名称/值属性。每个首选项的名称是不区分大小写的。许多偏好对于特定的平台是独特的，并且将被如此指示。

|属性(类型)(支持平台)|描述|
|:-----|:-----|
|AndroidLaunchMode(string)(Android)|默认值：singleTop,允许的值：standard，singleTop，singleTask，singleInstance;设置Activity android：launchMode属性。这改变了应用程序从应用程序图标或意图启动，并已经运行时发生的事情。 |
|android-maxSdkVersion(integer)(Android)|默认：未指定 ,设置项目中标签的maxSdkVersion属性|
|android-minSdkVersion(integer)(Android)|默认值：依赖于cordova-android版本,设置项目中标签的minSdkVersion属性|
|android-targetSdkVersion(integer)(Android)|默认值：依赖于cordova-android版本 ,设置项目中标签的targetSdkVersion属性|
|BackgroundColor(string)(Android)|设置应用的背景颜色。支持一个四字节的十六进制值，第一个字节表示alpha通道，后面三个字节的标准RGB值。|
|DefaultVolumeStream(string)(Android)|默认:default,在cordova-android 3.7.0中添加，此首选项设置硬件音量按钮链接到哪个音量。默认情况下，这是手机的“通话”和平板电脑的“媒体”。将其设置为“媒体”，让您的应用程序的音量按钮始终更改媒体音量。请注意，使用Cordova的媒体插件时，当任何媒体对象处于活动状态时，音量按钮将动态更改为控制媒体音量。|
|ErrorUrl(URL)(Android)|默认：null ,如果设置，则会在应用程序出现错误时显示引用的页面，而不是标题为“应用程序错误”的对话框。|
|FullScreen(boolean)(Android)|默认：false ,允许您隐藏屏幕顶部的状态栏。|
|InAppBrowserStorageEnabled (boolean)(Android)|默认值：true ,控制在InAppBrowser中打开的页面是否可以访问与使用默认浏览器打开的页面相同的localStorage和WebSQL存储。|
|KeepRunning(boolean)(Android)|默认值：true ,确定即使在暂停事件触发后，应用程序是否仍在后台运行。将其设置为false不会在暂停事件后终止应用程序，而只是在应用程序处于后台时停止在cordova webview中执行代码。|
|LoadUrlTimeoutValue(number in milliseconds)(Android) |默认值：20000，20秒 ,加载页面时，在抛出超时错误之前等待的时间量。|
|LoadingDialog(string)(Android)|默认：null ,如果设置，当加载应用程序的第一页时，显示一个带有指定标题和消息的对话框和一个微调器。标题和消息在此值字符串中用逗号分隔，并且在显示对话框之前删除该逗号。|
|LogLevel(string)(Android)|默认值：ERROR ,允许的值：错误，警告，信息，调试，VERBOSE设置应用程序中的日志消息将被过滤的最小日志级别。|
|OverrideUserAgent(string)(Android)|如果设置，该值将取代webview的旧UserAgent。请求远程页面时，识别来自应用/浏览器的请求是有帮助的。谨慎使用，这可能会导致与Web服务器相关的问题。对于大多数情况下，请使用AppendUserAgent。|
|SetFullscreen(boolean)(Android)|默认：false ,与此xml文件的全局配置中的Fullscreen参数相同。这个特定于Android的元素已被弃用，以支持全局全屏元素，并将在未来的版本中被删除。|
|ShowTitle(boolean)(Android)|默认：false ,在屏幕顶部显示标题。|
|AllowInlineMediaPlayback(boolean)(iOS)|默认：false ,设置为true以允许HTML5媒体播放在屏幕布局内部显示，使用浏览器提供的控件而不是本机控件。|
|BackupWebStorage(string)(iOS)|Default: cloud ,Allowed values: none, local, cloud. 设置为允许通过iCloud备份网络存储数据。设置为本地，只允许通过iTunes同步进行本地备份。设置为不阻止网络存储备份。|
|CordovaWebViewEngine(string)(iOS)|默认值：CDVUIWebViewEngine ,这将设置WebView引擎插件用于呈现主机应用程序。该插件必须符合CDVWebViewEngineProtocol协议。这里的“值”应该与安装的WebView引擎插件的“功能”名称匹配。这个首选项通常会由自动安装的WebView引擎插件设置。|
|EnableViewportScale(boolean)(iOS)|默认：false ,设置为true以允许视口元标记禁用或限制默认启用的用户缩放范围。在HTML中放置一个如下所示的视口，以禁止在渲染WebView中缩放和适应内容：<meta name='viewport' content='width=device-width, initial-scale=1, user-scalable=no' />|
|ErrorUrl(string)(IOS)|如果设置，则会在应用程序出现错误时显示引用的本地页面。|
|GapBetweenPages(float)(iOS)|默认值：0 ,页面之间的差距的大小，以磅为单位。|
|KeyboardDisplayRequiresUserAction(boolean)(iOS)|默认值：true ,设置为false以允许在对表单输入调用focus（）时出现键盘。|
|MediaPlaybackAllowsAirPlay(boolean)(iOS)|默认值：true ,设置为false以防止在此视图中使用Air Play。在默认的UIWebView和WKWebView中可用。|
|MediaPlaybackRequiresUserAction(boolean)(iOS)|默认：false ,设置为true可防止使用自动播放属性或通过JavaScript自动播放HTML5视频或音频。|
|PageLength(string)(iOS)|默认值：0 ,每个页面的大小，以页面流动的方向为单位。当PaginationMode是从左到右或从左到右时，此属性表示每个页面的宽度。当PaginationMode是topToBottom或bottomToTop时，此属性表示每个页面的高度。默认值为0，这意味着布局使用视口的大小来确定页面的尺寸。|
|PaginationBreakingMode(string)(iOS)|Default: page ,Allowed values: page, column 有效值是页面和列。列或页面断开的方式。这个属性决定是否允许某些关于列和页面断开的CSS属性。当此属性设置为列时，内容将尊重与列分页相关的CSS属性而不是分页。|
|PaginationMode(string)(iOS)|Default: unpaginated ,Allowed values: unpaginated, leftToRight, topToBottom, bottomToTop, rightToLeft;该属性确定Web视图中的内容是否分解为一次填充一个屏幕的视图的页面，或显示为一个长滚动视图。如果设置为分页形式，则此属性切换内容的分页布局，从而使Web视图使用PageLength和GapBetweenPages的值来重新传播其内容。|
|Suppresses3DTouchGesture(boolean)(iOS)|默认：false ,设置为true以避免3D触摸功能的iOS设备渲染放大镜小部件，当用户长时间使用webview时施加力量。因为这会禁用onclick处理程序，所以请彻底测试您的应用程序，但在ontouchend上播放效果不错。如果这个设置是真的，SuppressesLongPressGesture也将是真实的。|
|SuppressesIncrementalRendering(boolean)(iOS)|默认：false ,设置为true，等待所有内容在呈现到屏幕前收到。|
|SuppressesLongPressGesture(boolean)(iOS)|默认：false ,设置为true以避免iOS9 +在用户按下webview时渲染放大镜窗口小部件。彻底测试您的应用程序，因为这可能会干扰文本选择功能。|
|TopActivityIndicator(string)(iOS)|Default: gray ,Allowed values: whiteLarge, white, gray. 控制状态栏中显示重要处理器活动的小旋转图标的外观。|
|UIWebViewDecelerationSpeed(string)(iOS)|	Default: normal ,Allowed values: normal, fast 此属性控制动量滚动的减速速度。normal是大多数本地应用程序的默认速度，而fast是Mobile Safari的默认速度。|
|deployment-target(string)(iOS)|这将在版本中设置IPHONEOS 部署目标，最终转换为ipa中的MinimumOSVersion。有关更多详细信息，请参阅Apple关于部署目标设置的文档|
|target-device(string)(iOS)|	Default: universal ,Allowed values: handset, tablet, universal ,该属性直接映射到xcode项目中的TARGETED DEVICE FAMILY。请注意，如果您的目标是通用（这是默认设置），则需要为iPhone和iPad提供屏幕截图，否则您的应用可能会被拒绝。|
|AppendUserAgent(string)(Android,IOS)|如果设置，则该值将附加到webview的旧UserAgent的末尾。与OverrideUserAgent一起使用时，该值将被忽略。|
|DisallowOverscroll(boolean)(Android,IOS)|默认：false ,如果您不希望界面在用户滚动浏览内容的开始或结尾时显示任何反馈，则设置为true。在iOS上，超滚动手势会导致内容恢复到原始位置。在Android上，它们沿内容的顶部或底部边缘产生更微妙的发光效果。|
|Orientation(string)(Android,IOS)|默认值：default ,允许值：default，landscape，portait ,允许您锁定方向，并防止界面旋转以响应方向的变化。注：默认值意味着Cordova将从平台的清单/配置文件中去除定向首选项，从而允许平台回退到其默认行为。对于iOS，要指定纵向和横向模式，您可以使用特定于平台的值“all”。 |

**例子：**
```xml
<preference name="DisallowOverscroll" value="true"/>
<preference name="Fullscreen" value="true" />
<preference name="BackgroundColor" value="0xff0000ff"/>
<preference name="HideKeyboardFormAccessoryBar" value="true"/>
<preference name="Orientation" value="landscape" />

<!-- iOS only preferences -->
<preference name="EnableViewportScale" value="true"/>
<preference name="MediaPlaybackAllowsAirPlay" value="false"/>
<preference name="MediaPlaybackRequiresUserAction" value="true"/>
<preference name="AllowInlineMediaPlayback" value="true"/>
<preference name="BackupWebStorage" value="local"/>
<preference name="TopActivityIndicator" value="white"/>
<preference name="SuppressesIncrementalRendering" value="true"/>
<preference name="GapBetweenPages" value="0"/>
<preference name="PageLength" value="0"/>
<preference name="PaginationBreakingMode" value="page"/>
<preference name="PaginationMode" value="unpaginated"/>
<preference name="UIWebViewDecelerationSpeed" value="fast" />
<preference name="ErrorUrl" value="myErrorPage.html"/>
<preference name="OverrideUserAgent" value="Mozilla/5.0 My Browser" />
<preference name="AppendUserAgent" value="My Browser" />
<preference name="target-device" value="universal" />
<preference name="deployment-target" value="7.0" />
<preference name="CordovaWebViewEngine" value="CDVUIWebViewEngine" />
<preference name="SuppressesLongPressGesture" value="true" />
<preference name="Suppresses3DTouchGesture" value="true" />

<!-- Android only preferences -->
<preference name="KeepRunning" value="false"/>
<preference name="LoadUrlTimeoutValue" value="10000"/>
<preference name="InAppBrowserStorageEnabled" value="true"/>
<preference name="LoadingDialog" value="My Title,My Message"/>
<preference name="ErrorUrl" value="myErrorPage.html"/>
<preference name="ShowTitle" value="true"/>
<preference name="LogLevel" value="VERBOSE"/>
<preference name="AndroidLaunchMode" value="singleTop"/>
<preference name="DefaultVolumeStream" value="call" />
<preference name="OverrideUserAgent" value="Mozilla/5.0 My Browser" />
<preference name="AppendUserAgent" value="My Browser" />
```
### feature
    如果使用CLI构建应用程序，则使用plugin命令启用设备API。这不会修改顶层的config.xml文件，所以元素不适用于您的工作流程。如果直接在SDK中工作并使用特定于平台的
    config.xml文件作为源，则可以使用标记来启用设备级的API和外部插件。它们经常在特定于平台的config.xml文件中显示自定义值。

|属性(类型)|描述|
|:---|:---|
|name(string)|必需，启用的插件的名称。|

### param
    用于指定某些插件参数，例如：从哪个包中检索插件代码，以及在Webview初始化期间是否要初始化插件代码。

|属性(类型)|描述|
|:---|:---|
|name(string)|允许的值：android-package，ios-package，osx-package，onload。使用'ios-package'，'osx-package'和'android-package'来指定包的名称（由'value'属性指定）来初始化插件代码，而'onload'是用于指定在初始化控制器时是否要实例化相应的插件（如'value'属性中指定的）。|
|value(string or boolean)|必需，指定要用于初始化插件代码的包的名称（当'name'属性是android-package，ios-package或osx-package时），指定要在控制器初始化期间加载的插件的名称（当'名称'属性设置为'onload'）。|

**例子：**
```xml
<!-- Here is how to specify the Device API for Android projects -->
<feature name="Device">
  <param name="android-package" value="org.apache.cordova.device.Device" />
</feature>

<!-- Here's how the element appears for iOS projects -->
<feature name="Device">
  <param name="ios-package" value="CDVDevice" />
  <param name="onload" value="true" />
</feature>
```
### platform
    使用CLI构建应用程序时，有时需要指定特定于特定平台的首选项或其他元素。使用 元素来指​​定应该只出现在单个平台特定的config.xml文件中的配置。

|属性(类型)|描述|
|:---|:---|
|name(string)|正在定义首选项的平台。|

**例子：**
```xml
<platform name="android">
   <preference name="Fullscreen" value="true" />
</platform>
```
### hook
    表示您的自定义脚本，在发生某些操作时（例如，在添加插件或调用平台准备逻辑之后）将由Cordova调用。当您需要扩展默认的Cordova功能时，这非常有用。

|属性(类型)|描述|
|:---|:---|
|type(string)|指定要在其中调用自定义脚本的操作。|
|src(string)|指定发生特定操作时要调用的脚本的位置。|

**例子：**
```xml
<hook type="after_plugin_install" src="scripts/afterPluginInstall.js" />
```