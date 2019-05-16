package org.tjut.xsl.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Create by majietao on 2019/4/18
 */
public class Tea {

    public static Configurator init(Context context) {
        getConfigurations().put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<Object, Object> getConfigurations() {
        return Configurator.getInstance().getTeaConfigs();
    }

    public static <T> T getConfiguration(ConfigKeys configKeys) {
        return (T) Configurator.getInstance().getConfiguration(configKeys);
    }

    public static Context getAppContext() {
        return Configurator.getInstance().getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }
}