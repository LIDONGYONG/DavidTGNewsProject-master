package newsdemo.app;

import android.app.Application;

import newsdemo.utils.OkHttpUtils;


public class MyApplication extends Application {
    private static MyApplication app;
    private OkHttpUtils mOkHttpUtils;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initOkHttpUtils();

    }


    /**
     * 初始化OkHttp
     */
    private void initOkHttpUtils() {
        mOkHttpUtils = OkHttpUtils.getInstance();

    }


    public static MyApplication getApp() {
        return app;
    }


    public OkHttpUtils getOkHttpUtils() {
        return this.mOkHttpUtils;
    }


}
