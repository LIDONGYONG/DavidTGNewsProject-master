package newsdemo.pullRecycleView.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import newsdemo.app.MyApplication;
import newsdemo.utils.OkHttpUtils;
public abstract class BaseActivity extends Activity {
    protected Context mContext;
    protected OkHttpUtils okHttpUtils = MyApplication.getApp().getOkHttpUtils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initView();
        initData();
        setOnclickListener();

    }

    protected void setOnclickListener() {
    }

    protected abstract void initView();

    protected abstract void initData();

    protected void startActivity(Class<?> activity) {
        Intent intent = new Intent(getBaseContext(), activity);
        startActivity(intent);
    }

}
