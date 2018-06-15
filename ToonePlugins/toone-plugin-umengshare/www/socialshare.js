

    var exec = require('cordova/exec');

    var getShortUrl = function (long_url, callback) {
//        	var apiKey = 4033790602;
        var apiKey = 3661469344;
        long_url = encodeURIComponent(long_url);
        var apiUrl = "https://api.weibo.com/2/short_url/shorten.json?access_token=2.002NQvCCK8JnzD026bff2b81ORw6GD&url_long=" + long_url;
        $.ajax(apiUrl, {
            type: "GET",
            async: false,
            dataType: 'text json',
            crossDomain: true,
            success: function (data, state) {
                var urls = data.urls;
                var short_url = urls[0].url_short
                callback(short_url);
            }
        });
    }

    module.exports = {
        platform: {
            wxsession: '微信好友',
            wxtimeline: '微信朋友圈',
            qzone: 'QQ空间',
            sina: '新浪微博'
        },
        init: function (successHandler, errorHandler) {
            exec(successHandler, errorHandler, "UMengSharePlugin", "init", []);
        },
        share: function (text, title, url, imgUrl, platforms, successHandler, errorHandler) {
            var plts = ['wxsession', 'wxtimeline', 'qzone', 'sina'];
            if (platforms) {
                plts = platforms;
            }
            var errCB = function (errmessage) {
                alert("分享失败:" + errmessage);
            }
//            if (url) {
//                getShortUrl(url, function (short_url) {
//                    exec(successHandler, errorHandler, "UMengSharePlugin", "share", [text, title, short_url, imgUrl, plts]);
//                });
//            } else {
                exec(successHandler, errorHandler, "UMengSharePlugin", "share", [text, title, url, imgUrl, plts]);
//            }

        },
        auth: function (platformName, successHandler, errorHandler) {
            exec(successHandler, errorHandler, "UMengSharePlugin", "auth", [platformName]);
        },
        /**
         判断某平台是否安装，支持的检测平台：qq,sina,wechat
         @param platformName<String>    //qq  或  wechat  （目前仅支持检测这两个平台，传其他值返回false）
         @param callback<Function>      //例：var callback = function(isInstall){}
         return                          isInstall<String>：安装返回"true" 未安装返回"false"
         */
        isInstall: function (platformName, callback) {
            exec(callback, function () {
            }, "UMengSharePlugin", "isInstall", [platformName]);
        }

    };

