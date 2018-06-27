# Cordova插件开发
## 一、Cordova插件开发流程简介
![](https://i.imgur.com/fHfjWWK.png)
## 二、版本要求及开发工具下载
- 1、AndroidStudio 3.0 及以上版本：[下载与安装教程](https://juejin.im/post/59c879a3f265da064703fbff)
- 2、Java 7 及以上版本：[jdk下载与安装配置教程](https://blog.csdn.net/u012934325/article/details/73441617)
- 3、node.js 3.10.10及以上版本：[node.js与cordova下载与安装教程](https://blog.csdn.net/weixin_37730482/article/details/74388056)
- 4、开发人员配置：必须有一定android开发基础
## 三、快速开发
[Android快速开发自定义cordova插件文档](./插件开发与安装.md)
## 四、已配置好的Android工程环境（用于开发Android Cordova插件包）
- 下载该工程，使用AndroidStudio 3.0及以上版本打开进行开发即可
## 五、注意事项
- 1、命名文件目录等，禁止出现中文和空格
- 2、plugin.xml配置规范模板：[快速配置plugin.xml文件](./插件plugin配置规范模板.md)
- 3、plugin.xml配置dependency时，请检查源工程已有的插件清单，避免冲突：[已有插件清单查询](./源工程插件清单.md)
- 4、plugin.xml配置lib-file和framework时，请检查源工程已有的jar包清单，避免冲突：[已有jar包清单查询](./源工程第三方jar包清单.md)
- 5、开发环境的**包名**要与打包平台创建的**包名**一致，以免一些.R文件因包名不一致，导致打包失败。善用FakeR来动态导入R文件。
## 六、附录
- 1、plugin.xml文件详细配置介绍：
[plugin.xml 配置详解](./插件plugin配置介绍.md)
- 2、config.xml文件详细配置介绍：
[config.xml 配置详解](./插件config配置介绍.md)
- 3、java 和 js 文件详细开发介绍：[   java 和 js 详细开发文档](http://cordova.axuer.com/docs/zh-cn/latest/guide/platforms/android/plugin.html)
- 4、建议浏览器安装Octotree插件，便于阅读github结构