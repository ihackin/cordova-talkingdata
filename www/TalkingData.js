

var exec = require('cordova/exec');

var TalkingData = {
    init: function (appKey, channelId) {
        exec(null, null, "TalkingData", "sessionStarted", [appKey, channelId]);
    },
    trackEvent: function (eventId) {
        exec(null, null, "TalkingData", "trackEvent", [eventId]);
    },
    trackEventWithLabel: function (eventId, eventLabel) {
        exec(null, null, "TalkingData", "trackEventWithLabel", [eventId, eventLabel]);
    },
    trackEventWithParameters: function (eventId, eventLabel, eventData) {
        var eventDataJson = JSON.stringify(eventData);
        exec(null, null, "TalkingData", "trackEventWithParameters", [eventId, eventLabel, eventDataJson]);
    },
    trackPage: function (pageName) {
        exec(null, null, "TalkingData", "trackPage", [pageName]);
    },
    trackPageBegin: function (pageName) {
        exec(null, null, "TalkingData", "trackPageBegin", [pageName]);
    },
    trackPageEnd: function (pageName) {
        exec(null, null, "TalkingData", "trackPageEnd", [pageName]);
    },
    setLocation: function (latitude, longitude) {
        exec(null, null, "TalkingData", "setLocation", [latitude, longitude]);
    },
    getDeviceId: function (callBack) {
        exec(callBack, null, "TalkingData", "getDeviceId", []);
    },
    openDebugLog: function (enabled) {
        exec(null, null, "TalkingData", "openDebugLog", [enabled]);
    },
    setExceptionReportEnabled: function (enabled) {
        exec(null, null, "TalkingData", "setExceptionReportEnabled", [enabled]);
    },
    setSignalReportEnabled: function (enabled) {
        exec(null, null, "TalkingData", "setSignalReportEnabled", [enabled]);
    },
    setLogEnabled: function (enabled) {
        exec(null, null, "TalkingData", "setLogEnabled", [enabled]);
    }
};

module.exports = TalkingData;

