# 使用应用角标
## 使用样例
```javascript
//设置应用角标数量
cordova.plugins.notification.badge.set(10);

//设置角标数量增加
cordova.plugins.notification.badge.increase(1, function (badge) {
    // badge is now 11 (10 + 1)
});

//设置角标数量减少
cordova.plugins.notification.badge.decrease(2, function (badge) {
    // badge is now 9 (11 - 2)
});

//清除角标
cordova.plugins.notification.badge.clear();

//获取角标数量
cordova.plugins.notification.badge.get(function (badge) {
    ...
});

//配置点击应用图标自动清除角标
cordova.plugins.notification.badge.configure({ autoClear: true });

//权限申请
cordova.plugins.notification.badge.requestPermission(function (granted) {
    ...
});

//检查是否有权限
cordova.plugins.notification.badge.hasPermission(function (granted) {
    ...
});

```
