
  /**
   * ping++, cordova, module
   */
  module.exports = {
    /**
     * 开启/关闭 Ping++ debug 模式
     * @param boolean true|false
     */
    setDebugMode: function (enabled) {
      cordova.exec(function () {
      }, null, "PingppPlugin", "setDebugMode", [enabled]);
    },
    /**
     * 获取当前SDK的版本号
     * @param {Function} successCallback (version)
     */
    getVersion: function (successCallback) {
      cordova.exec(successCallback, function () {
      }, "PingppPlugin", "getVersion", []);
    },
    /**
     * 调用支付
     * @param object charge 或 order
     * @param function completionCallback  支付结果回调 (result, error)
     */
    createPayment: function (object, successCallback) {
      cordova.exec(function (response) {
        successCallback(response.result, response.error);
      },null, "PingppPlugin", "createPayment", [object]);
    },
    
    /**
     * 调用支付 UI 版 (不含渠道选择页面)
     * @param object charge 或 order
     * @param function completionCallback  支付结果回调
     */
    createPay: function (object, successCallback) {
      cordova.exec(function (response) {
        successCallback(response.result, response.error);
      }, null, "PingppPlugin", "createPay", [object]);
    },

    /**
     *  目前支持的支付平台: 微信 : "wx", 支付宝 : "alipay"
     */
    supportChannel: {
        "wx": "微信",
        "alipay": "支付宝"
    },

    /**
     *  支付
     *  @param config<Object> 配置项 例.
     {
         channel      : <String>,   //   支付平台 : 目前支持微信和支付宝，微信传"wx",支付宝传"alipay"
         amount       : <String>,   //   支付金额 : 单位：元
         subject      : <String>,   //   支付标题
         body         : <String>,   //   支付内容描述
         liveMode     : <boolean>,  //   支付模式 : true为真是的支付场景，false为测试场景，会调用一个虚拟的支付页面来模拟支付

     }
     *  @param successCallback<Function>  成功回调  return  result<String>  直接返回charge对象  详细见：https://www.pingxx.com/api#charges-支付
     *  @param errorCallback<Function>    失败回调  return errorMsg<String> 错误信息
     *  用例：navigator.Pingpp.pay({channel:"wx",amount:"0.01",subject:"支付标题",body:"支付内容aaa",liveMode:true},function(rs){alert(rs)},function(error){alert(error)})
     */
    pay: function (config, successCallback, errorCallback) {

        config.channel = this.supportChannel[config.channel] ? config.channel : "";
        if (!config.channel) {
            alert("支付失败：不支持的支付平台->" + channel);
            return;
        }
        //ping++后台传入的金额单位为分，需*100
        if (config.amount && !isNaN(parseFloat(config.amount))) {
            config.amount = Math.round(parseFloat(config.amount) * 100) + "";
        } else {
            alert("支付失败：请输入正确的金额");
            return;
        }
        config.liveMode = config.liveMode == "true" ? "true" : "false";
        //请求charge对象的应用服务器地址
        config.getChargeServerURL = GlobalVariables.getServerUrl() + "/module-operation!executeOperation?operation=PingppService";

        if (!errorCallback || typeof errorCallback != "function") {
            errorCallback = function () {
            };
        }

        if (!successCallback || typeof successCallback != "function") {
            successCallback = function () {
            };
        }
         cordova.exec(successCallback, errorCallback, 'PingppPlugin', 'pay', [config]);
    }
  };

