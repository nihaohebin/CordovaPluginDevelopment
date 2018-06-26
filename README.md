# Cordova插件开发
## 一、Cordova插件开发流程简介
![](https://i.imgur.com/fHfjWWK.png)
## 二、版本要求及开发工具下载
- 1、AndroidStudio 3.0 及以上版本：[下载与安装教程](https://juejin.im/post/59c879a3f265da064703fbff)
- 2、Java 7 及以上版本：[jdk下载与安装配置教程](https://blog.csdn.net/u012934325/article/details/73441617)
- 3、node.js 3.10.10及以上版本：[node.js与cordova下载与安装教程](https://blog.csdn.net/weixin_37730482/article/details/74388056)
## 三、快速开发
### 请有Android开发基础的工程师进行开发
### 请有Android开发基础的工程师进行开发
### 请有Android开发基础的工程师进行开发
### 重要的事情说三遍

[Android快速开发自定义cordova插件文档](https://github.com/nihaohebin/CordovaPluginDevelopment/blob/master/%E6%8F%92%E4%BB%B6%E5%BC%80%E5%8F%91%E4%B8%8E%E5%AE%89%E8%A3%85.md)
## 四、已配置好的插件开发Android工程环境
[开发Android插件工程下载](https://github.com/nihaohebin/CordovaPluginDevelopment.git)
## 五、注意事项
- 1、命名文件目录等，禁止出现中文
- 2、plugin.xml配置规范模板：[插件plugin配置规范模板](https://github.com/nihaohebin/CordovaPluginDevelopment/blob/master/%E6%8F%92%E4%BB%B6plugin%E9%85%8D%E7%BD%AE%E8%A7%84%E8%8C%83%E6%A8%A1%E6%9D%BF.md)
- 3、plugin.xml配置dependency时，请检查源工程已有的插件清单，避免冲突：[已有插件清单查询](https://github.com/nihaohebin/CordovaPluginDevelopment/blob/master/%E6%BA%90%E5%B7%A5%E7%A8%8B%E6%8F%92%E4%BB%B6%E6%B8%85%E5%8D%95.md)
- 4、plugin.xml配置lib-file和framework时，请检查源工程已有的jar包清单，避免冲突：[已有jar包清单查询](https://github.com/nihaohebin/CordovaPluginDevelopment/blob/master/%E6%BA%90%E5%B7%A5%E7%A8%8B%E7%AC%AC%E4%B8%89%E6%96%B9jar%E5%8C%85%E6%B8%85%E5%8D%95.md)
- 5、开发环境的**包名**要与打包平台创建的**包名**一致，以免一些.R文件因包名不一致，导致打包失败。善用FakeR来动态导入R文件，例如[下载并查看MultiImageChooserActivity中FakeR使用方法](https://github.com/nihaohebin/CordovaPluginDevelopment/tree/master/app/src/main/java/com/synconset)
## 六、附录
- 1、plugin.xml文件详细配置介绍：
[plugin.xml 配置查询](https://github.com/nihaohebin/CordovaPluginDevelopment/blob/master/%E6%8F%92%E4%BB%B6plugin%E9%85%8D%E7%BD%AE%E4%BB%8B%E7%BB%8D.md)
- 2、config.xml文件详细配置介绍：
[config.xml 配置查询](https://github.com/nihaohebin/CordovaPluginDevelopment/blob/master/%E6%8F%92%E4%BB%B6config%E9%85%8D%E7%BD%AE%E4%BB%8B%E7%BB%8D.md)
- 3、java 和 js 文件详细开发介绍：[   java 和 js 详细开发文档](http://cordova.axuer.com/docs/zh-cn/latest/guide/platforms/android/plugin.html)
- 4、建议浏览器安装Octotree插件，便于阅读github文档