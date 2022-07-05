
package io.refiner.rn;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;

import java.util.HashMap;
import java.util.LinkedHashMap;

import io.refiner.Refiner;
import io.refiner.RefinerConfigs;
import io.refiner.rn.utils.MapUtil;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

import android.util.Log;

public class RNRefinerModule extends ReactContextBaseJavaModule {

    public static final String LOG_TAG = "ReactNativeRefiner";
    private final ReactApplicationContext reactContext;

    public RNRefinerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNRefiner";
    }

    @ReactMethod
    public void initialize(String projectId) {
        if (projectId != null) {
            Refiner.INSTANCE.initialize(reactContext, new RefinerConfigs(projectId));
            this.registerCallback();
        }
    }

    @ReactMethod
    public void identifyUser(String userId, ReadableMap userTraits, String locale) {
        LinkedHashMap<String, Object> userTraitsMap = null;
        if (userTraits != null) {
            userTraitsMap = new LinkedHashMap<>(MapUtil.toMap(userTraits));
        }
        if (userId != null) {
            Refiner.INSTANCE.identifyUser(userId, userTraitsMap, locale);
        }
    }

    @ReactMethod
    public void resetUser() {
        Refiner.INSTANCE.resetUser();
    }

    @ReactMethod
    public void trackEvent(String eventName) {
        if (eventName != null) {
            Refiner.INSTANCE.trackEvent(eventName);
        }
    }

    @ReactMethod
    public void trackScreen(String screenName) {
        if (screenName != null) {
            Refiner.INSTANCE.trackScreen(screenName);
        }
    }

    @ReactMethod
    public void showForm(String formUuid, boolean force) {
        if (formUuid != null) {
            Refiner.INSTANCE.showForm(formUuid, force);
        }
    }

    @ReactMethod
    public void attachToResponse(ReadableMap contextualData) {
        HashMap<String, Object> contextualDataMap = null;
        if (contextualData != null) {
            contextualDataMap = new HashMap<>(MapUtil.toMap(contextualData));
        }
        Refiner.INSTANCE.attachToResponse(contextualDataMap);
    }

    private void sendEvent(String eventName) {
        getReactApplicationContext().getJSModule(RCTNativeAppEventEmitter.class).emit(eventName, null);
	}

    private void registerCallback(){
        Refiner.INSTANCE.onBeforeShow(new Function2<String, Object, Unit>() {
            @Override
            public Unit invoke(String s, Object o) {
                Log.d(LOG_TAG, "Invoked onBeforeShow");
                sendEvent("onBeforeShow");
                return null;
            }
        });

        Refiner.INSTANCE.onShow(new Function1<Object, Unit>() {
            @Override
            public Unit invoke(Object o) {
                Log.d(LOG_TAG, "Invoked onShow");
                sendEvent("onShow");
                return null;
            }
        });

        Refiner.INSTANCE.onClose(new Function1<Object, Unit>() {
            @Override
            public Unit invoke(Object o) {
                Log.d(LOG_TAG, "Invoked onClose");
                sendEvent("onClose");
                return null;
            }
        });

        Refiner.INSTANCE.onComplete(new Function2<Object, Object, Unit>() {
            @Override
            public Unit invoke(Object o, Object o2) {
                Log.d(LOG_TAG, "Invoked onComplete");
                sendEvent("onComplete");
                return null;
            }
        });
    }
}
