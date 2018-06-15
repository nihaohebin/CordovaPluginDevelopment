

    var exec = require('cordova/exec');
    module.exports = {
        execute: function () {
            exec(function () {}, function () {}, 'TalkingDataPlugin', 'execute', []);
        }
    };

