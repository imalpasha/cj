package com.fly.cj.ui.object;

import io.realm.RealmObject;

public class CachedResult extends RealmObject {

    private String cachedResult;
    private String cachedAPI;

    public String getCachedAPI() {
        return cachedAPI;
    }

    public void setCachedAPI(String cachedAPI) {
        this.cachedAPI = cachedAPI;
    }

    public String getCachedResult() {
        return cachedResult;
    }

    public void setCachedResult(String cachedResult) {
        this.cachedResult = cachedResult;
    }

}
