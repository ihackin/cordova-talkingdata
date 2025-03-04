package com.talkingdata.analytics;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.tendcloud.tenddata.TCAgent;;

public class TalkingDataPlugin extends CordovaPlugin {
	private Activity mActivity;
	private Context mAppContext;
	private String mCurrPageName;
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		this.mActivity = cordova.getActivity();
		this.mAppContext = cordova.getActivity().getApplicationContext();
	}
	
	@Override
	public void onResume(boolean multitasking) {
		super.onResume(multitasking);
		TCAgent.onResume(mActivity);
	}
	
	@Override
	public void onPause(boolean multitasking) {
		super.onPause(multitasking);
		TCAgent.onPause(mActivity);
	}
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("sessionStarted")) {
			String appKey = args.getString(0);
			String channelId = args.getString(1);
			TCAgent.init(mAppContext, appKey, channelId);
			return true;
		} else if (action.equals("trackEvent")) {
			String eventId = args.getString(0);
			TCAgent.onEvent(mAppContext, eventId);
			return true;
		} else if (action.equals("trackEventWithLabel")) {
			String eventId = args.getString(0);
			String eventLabel = args.getString(1);
			TCAgent.onEvent(mAppContext, eventId, eventLabel);
			return true;
		} else if (action.equals("trackEventWithParameters")) {
			String eventId = args.getString(0);
			String eventLabel = args.getString(1);
			String eventDataJson = args.getString(2);
			if (eventDataJson != null) {
				Map<String, Object> eventData = this.toMap(eventDataJson);
				TCAgent.onEvent(mAppContext, eventId, eventLabel, eventData);
			}
			return true;
		} else if (action.equals("trackPage")) {
			String pageName = args.getString(0);
			if (!TextUtils.isEmpty(mCurrPageName)) {
				TCAgent.onPageEnd(mActivity, mCurrPageName);
			}
			mCurrPageName = pageName;
			TCAgent.onPageStart(mActivity, pageName);
			return true;
		} else if (action.equals("trackPageBegin")) {
			String pageName = args.getString(0);
			mCurrPageName = pageName;
			TCAgent.onPageStart(mActivity, pageName);
			return true;
		} else if (action.equals("trackPageEnd")) {
			String pageName = args.getString(0);
			mCurrPageName = null;
			TCAgent.onPageEnd(mActivity, pageName);
			return true;
		} else if (action.equals("getDeviceId")) {
			String deviceId = TCAgent.getDeviceId(mAppContext);
			callbackContext.success(deviceId);
			return true;
		} else if (action.equals("setSignalReportEnabled")) {
			TCAgent.setReportUncaughtExceptions(args.getBoolean(0));
			return true;
		} else if (action.equals("setLogEnabled")) {
			TCAgent.LOG_ON = args.getBoolean(0);
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> toMap(String jsonStr)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObj = new JSONObject(jsonStr);
			Iterator<String> keys = jsonObj.keys();
			String key = null;
			Object value = null;
			while (keys.hasNext())
			{
				key = keys.next();
				value = jsonObj.get(key);
				result.put(key, value);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}

