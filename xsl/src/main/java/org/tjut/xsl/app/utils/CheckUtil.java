package org.tjut.xsl.app.utils;

public class CheckUtil {
    public static boolean notNull(String s) {
        return s != null && !s.isEmpty();
    }

    public static boolean notNull(String... s) {
        boolean b = true;
        for (String s1 : s) {
            b = s1 != null && !s1.isEmpty();
            if (!b) {
                return b;
            }
        }
        return b;
    }

}
