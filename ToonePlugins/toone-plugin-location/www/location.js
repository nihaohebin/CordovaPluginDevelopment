
    var exec = require('cordova/exec');
    module.exports = {
        getCurrentPosition: function (successCallback, errorCallback, params) {
            if (!errorCallback || typeof errorCallback != "function") {
                errorCallback = function () {
                };
            }

            if (!successCallback || typeof successCallback != "function") {
                successCallback = function () {
                };
            }
            exec(successCallback, errorCallback, 'Location', 'execute', [params]);
        }
    };
