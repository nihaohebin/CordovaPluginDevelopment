# Cordova插件开发
## 一、Cordova插件开发流程简介
![](https://i.imgur.com/fHfjWWK.png)
## 二、版本要求及开发工具下载
- 1、Android SDK 要求 Android 3.0 及以上版本：[国内android studio下载地址](http://www.android-studio.org/)
- 2、Java 7 或以上版本：[jdk下载地址](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- 3、node.js官网：[node.js下载地址](https://nodejs.org/)
## 三、快速开发
[快速自定义cordova插件开发文档](https://github.com/nihaohebin/CordovaPluginDevelopment/blob/master/%E6%8F%92%E4%BB%B6%E5%BC%80%E5%8F%91%E4%B8%8E%E5%AE%89%E8%A3%85.md)
## 四、已配置好的插件开发Android工程环境
[插件开发Android工程环境下载](https://github.com/nihaohebin/CordovaPluginDevelopment.git)
## 五、注意事项
- 1、开发自定义插件时，注意plugin.xml配置dependency时，请检查源工程已有的插件清单，避免冲突：[已有插件清单查询](https://github.com/nihaohebin/CordovaPluginDevelopment/blob/master/%E6%BA%90%E5%B7%A5%E7%A8%8B%E6%8F%92%E4%BB%B6%E6%B8%85%E5%8D%95.md)
- 2、开发自定义插件时，注意plugin.xml配置lib-file和framework时，请检查源工程已有的jar包清单，避免冲突：[已有jar包清单查询](https://github.com/nihaohebin/CordovaPluginDevelopment/blob/master/%E6%BA%90%E5%B7%A5%E7%A8%8B%E7%AC%AC%E4%B8%89%E6%96%B9jar%E5%8C%85%E6%B8%85%E5%8D%95.md)
- 3、注意资源命名规范和路径规范：[资源命名规范和路径规范](https://github.com/nihaohebin/CordovaPluginDevelopment/blob/master/%E9%85%8D%E7%BD%AEplugin%E6%96%87%E4%BB%B6%E6%B3%A8%E6%84%8F%E4%BA%8B%E9%A1%B9.md)
- 4、开发环境的**包名**要与打包平台创建的**包名**一致，以免一些.R文件因包名不一致，导致打包失败。善用FakeR来动态导入R文件，例如[下载并查看MultiImageChooserActivity中FakeR使用方法](https://github.com/nihaohebin/CordovaPluginDevelopment/tree/master/app/src/main/java/com/synconset)
## 六、附录
- 1、plugin.xml文件详细配置介绍：
[plugin.xml 配置查询](https://github.com/nihaohebin/CordovaPluginDevelopment/blob/master/%E6%8F%92%E4%BB%B6plugin%E9%85%8D%E7%BD%AE%E4%BB%8B%E7%BB%8D.md)
- 2、config.xml文件详细配置介绍：
[config.xml 配置查询](https://github.com/nihaohebin/CordovaPluginDevelopment/blob/master/%E6%8F%92%E4%BB%B6config%E9%85%8D%E7%BD%AE%E4%BB%8B%E7%BB%8D.md)
- 3、java 和js 文件详细开发介绍：[java 和 js 详细开发文档](http://cordova.axuer.com/docs/zh-cn/latest/guide/platforms/android/plugin.html)
