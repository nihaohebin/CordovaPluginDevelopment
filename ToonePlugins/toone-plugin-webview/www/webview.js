

    var exec = require('cordova/exec');
    var channel = require('cordova/channel');

    function eventObj() {
        this.channels = {
            'exit': channel.create('exit')
        };
    }

    eventObj.prototype = {
        _eventHandler: function (eventName, retValue) {
            if (eventName && (eventName in this.channels)) {
                this.channels[eventName].fire(retValue);
            }
        },
        addEventListener: function (eventname, f) {

            if (eventname in this.channels) {
                this.channels[eventname].subscribe(f);
            }
        },
        removeEventListener: function (eventname, f) {
            if (eventname in this.channels) {
                this.channels[eventname].unsubscribe(f);
            }
        }
    };

    function setLocalStrage(key, value) {
        localStorage.setItem(key, value);
    }

    function getLocalStrage(key) {
        localStorage.getItem(key);
    }

    function Webview() {
    }

    Webview.prototype = {
        /*
         * 创建一个新的webview,并打开第三方开发的H5页面
         * @param config<Object>
         *
         *  eg.
         *  {
         *      type:ordova.plugins.webview.TYPE.H5PAGE   //打开H5页面（传ordova.plugins.webview.TYPE.VPAGE打开V页面）      （必填）
         *      componentCode : "code1"                   //被打开窗体的构件编码                                            （打开V窗体时必填）
         *      windowCode    : "code2"                   //被打开窗体的窗体编码                                            （打开V窗体时必填）
         *      url:"http://www.baidu.com",               //创建webview后请求的url地址，需要带http://或https://             （打开H5窗体时必填）
         *      inputParam : {"a":"data"}                 //窗体入参                                                        （可选）
         *      exescript : "window.AA = 1"               //创建webview后需要执行的js脚本                                   （暂时没用）
         *  }
         *
         * @param eventobj<Object>          事件注册，目前仅支持关闭webview事件注册                  （可选）
         *
         *  eg.
         *  {
         *      "exit":function(){}         //注册一个关闭事件，当目标webview关闭时，会触发此事件
         *  }
         *
         */
        open: function (config, eventobj) {

            if (typeof(config) === 'object') {
                var inputParam = config.inputParam || {};
                var vars = inputParam.variable || {};
                vars["formulaOpenMode"] = "retrunValues";
                inputParam.variable = vars;
                config.inputParam = inputParam;

                //通过localstrage 在webview间传参
                localStorage.setItem("integration_windowCode", config.windowCode);
                localStorage.setItem("integration_componentCode", config.componentCode);

                var platformID = cordova.platformId;
                var url = config.url;
                // if (url) {
                //     if (platformID == "ios") {
                //         //iOS版无需传递jessionid
                //         var jsidLocatoin = url.indexOf("&JSESSIONID");
                //         if (jsidLocatoin > 0) {
                //             var pre = url.substring(0, jsidLocatoin);
                //             var aftertmp = url.substring(jsidLocatoin + 1, url.length);
                //             var after = aftertmp.substring(aftertmp.indexOf("&"), aftertmp.length);
                //             url = pre + after;
                //         }
                //     }

                    localStorage.setItem("integration_targetUrl", url);
                // }
                localStorage.setItem("integration_inputParam", JSON.stringify(inputParam));

                config = [config];
            } else {
                throw new error("cordova webview 插件 --> 【open】方法传参类型错误！");
            }

            var eventObject = new eventObj();
            //注册事件
            var eventobj = eventobj || {};
            if (typeof(eventobj.exit) == "function") {
                for (var callbackName in eventobj) {
                    eventObject.addEventListener(callbackName, eventobj[callbackName]);
                }
            }
            errorCallback = function () {

            };
            //当成功打开webview时，向实例池中压入当前实例
            successCallback = function () {
                cordova.plugins.webview.instancePool.push(eventObject);
            };

            exec(successCallback, errorCallback, "Webview", "open", config);
        },
        /*
         * 关闭当前窗体（关闭根窗体无效）
         */
        close: function (retValue) {
            //置空localstrage
            localStorage.setItem("integration_windowCode", "");
            localStorage.setItem("integration_componentCode", "");
            //localStorage.setItem("integration_targetUrl","");
            localStorage.setItem("integration_inputParam", "");

            var successCallback = function () {
            };
            var errorCallback = function () {
            };
            if (!retValue) {
                retValue = "";
            }
            if (window.global_sandbox) {
                var datasourceFactory = global_sandbox.getService("vjs.framework.extension.platform.interface.model.datasource.DatasourceFactory");
                var values = retValue.values;
                // 对values中的对象进行遍历
                for (var value in values) {
                    var isDatasource = datasourceFactory.isDatasource(values[value]);
                    if (isDatasource) {
                        values[value] = values[value].serialize();
                        values[value].isDatasource = true;
                    }
                }
            }
            retValue = JSON.stringify(retValue);
            exec(successCallback, errorCallback, "Webview", "close", [retValue]);
        },
        reload: function () {
            var successCallback = function () {
            };
            var errorCallback = function () {
            };
            exec(successCallback, errorCallback, "Webview", "reload", []);
        },
        /*
         * 触发事件
         * @param eventName<String> 事件名称
         */
        fireEvent: function (eventName, retValue) {
            var innerObject = cordova.plugins.webview.instancePool.pop(innerObject); //弹出当前实例池中最后一个实例，触发其注册方法
            setTimeout(function () { //防止注册的事件中包含alert 阻塞主线程 导致界面卡死
                var sandBox = GlobalVariables.getSandbox();
                var sb = sandBox.create({
                    extensions: [
                        "vjs.framework.extension.platform.interface.model.datasource",
                        "vjs.framework.extension.platform.datasource.taffy",
                        "vjs.framework.extension.platform.interface.scope"
                    ]
                });
                sb.active().done(function () {
                    var scopeManager = sb.getService("vjs.framework.extension.platform.interface.scope.ScopeManager");
                    var factory = sb.getService("vjs.framework.extension.platform.interface.model.datasource.DatasourceFactory");
                    var scopeId = scopeManager.createWindowScope({"series": "integration"});
                    scopeManager.openScope(scopeId);
                    if (retValue) {
                        var values = retValue.values;
                        for (var value in values) {
                            if (values[value].isDatasource) {
                                values[value] = factory.unSerialize(values[value]);
                            }
                        }
                    }
                    scopeManager.closeScope();
                    scopeManager.destroy(scopeId);
                });
                innerObject._eventHandler(eventName, retValue);
            }, 1)
        },
        TYPE: {
            VPAGE: "VPAGE",
            H5PAGE: "H5PAGE"
        },
        instancePool: [] //事件实例池
    };

    module.exports = new Webview();

