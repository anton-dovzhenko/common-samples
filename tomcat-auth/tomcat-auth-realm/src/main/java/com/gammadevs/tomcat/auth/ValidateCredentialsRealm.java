package com.gammadevs.tomcat.auth;

import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ValidateCredentialsRealm extends RealmBase {

    private final Map<String, String> userCredentials = new HashMap<>();
    {
        userCredentials.put("aaa", "111");
        userCredentials.put("bbb", "222");
    }

    @Override
    public Principal authenticate(String username, String credentials) {
        if (credentials != null && credentials.equals(userCredentials.get(username))) {
            if(this.containerLog.isTraceEnabled()) {
                this.containerLog.trace(sm.getString("realmBase.authenticateSuccess", username));
            }
            return new GenericPrincipal(username, credentials, Collections.singletonList("user"));
        } else {
            if(this.containerLog.isTraceEnabled()) {
                this.containerLog.trace(sm.getString("realmBase.authenticateFailure", username));
            }
            return null;
        }
    }

    @Override
    protected String getName() {
        return null;
    }

    @Override
    protected String getPassword(String s) {
        return null;
    }

    @Override
    protected Principal getPrincipal(String s) {
        return null;
    }

}
