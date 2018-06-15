
    var exec = require('cordova/exec');
    module.exports = {
        getImage: function (url, successCB, errorCB, option) {
            exec(successCB, errorCB, 'ImageTransfer', 'getImage', [url, option]);
        }
    };
