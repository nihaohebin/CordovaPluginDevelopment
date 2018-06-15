
var exec = require('cordova/exec');

exports.scan = function(success, error, arg0) {
    exec(success, error, "QRcodeScanner", "scan", [arg0]);
};


