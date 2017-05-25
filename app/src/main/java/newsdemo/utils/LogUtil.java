package newsdemo.utils;

import newsdemo.constants.Constants;

public final class LogUtil {

    private final static boolean all = true;

    private final static boolean i = true;

    private final static boolean d = true;

    private final static boolean e = true;

    private final static boolean v = true;

    private final static boolean w = true;

    private final static String defaultTag = Constants.LOG_TAG;

    private LogUtil() {
    }

    public static void I(String msg) {
        if (all && i) {
            android.util.Log.i(defaultTag, msg);
        }
    }

    public static void I(String tag, String msg) {
        if (all && i) {
            android.util.Log.i(tag, msg);
        }
    }


    public static void D(String msg) {
        if (all && d) {
            android.util.Log.d(defaultTag, msg);
        }
    }

    public static void D(String tag, String msg) {
        if (all && d) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void E(String msg) {
        if (all && e) {
            try {
                android.util.Log.e(defaultTag, msg);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void E(String tag, String msg) {
        if (all && e) {
            android.util.Log.e(tag, msg);
        }
    }

    public static void V(String msg) {
        if (all && v) {
            android.util.Log.v(defaultTag, msg);
        }
    }

    public static void V(String tag, String msg) {
        if (all && v) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void W(String msg) {
        if (all && w) {
            android.util.Log.w(defaultTag, msg);
        }
    }

    public static void W(String tag, String msg) {
        if (all && w) {
            android.util.Log.w(tag, msg);
        }
    }

}
