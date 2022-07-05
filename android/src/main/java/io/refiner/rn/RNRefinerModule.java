
package io.refiner.rn;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.util.HashMap;
import java.util.LinkedHashMap;

import io.refiner.Refiner;
import io.refiner.RefinerConfigs;
import io.refiner.rn.utils.MapUtil;

public class RNRefinerModule extends ReactContextBaseJavaModule {

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

    private void registerCallback(){
        System.out.println("registerCallback.....");
        Refiner.INSTANCE.onShow(new Function1<Object, Unit>() {
            @Override
            public Unit invoke(Object o) {
                System.out.println("onShow....." + o);
                return null;
            }
        });

        Refiner.INSTANCE.onDismiss(new Function1<Object, Unit>() {
            @Override
            public Unit invoke(Object o) {
                System.out.println("onDismiss..... " + o);
                return null;
            }
        });

        Refiner.INSTANCE.onClose(new Function1<Object, Unit>() {
            @Override
            public Unit invoke(Object o) {
                System.out.println("onClose..... " + o);
                return null;
            }
        });

        Refiner.INSTANCE.onComplete(new Function2<Object, Object, Unit>() {
            @Override
            public Unit invoke(Object o, Object o2) {
                System.out.println("onComplete..... o" + o);
                System.out.println("onComplete..... o2" + o2);
                return null;
            }
        });
    }
}
