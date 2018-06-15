# 获取应用版本信息
## 使用样例
```javascript
//获取应用版本
cordova.getAppVersion.getVersionNumber(function (version) {
    alert(version);
});

//获取应用名称
cordova.getAppVersion.getAppName(function (name) {
    alert(name);
});

//获取应用包名
cordova.getAppVersion.getPackageName(function (packageName) {
    alert(packageName);
});

//获取应用代码版本
cordova.getAppVersion.getVersionCode(function (versionCode) {
    alert(versionCode);
});

```