package example.hais.s2018.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import example.hais.s2018.simple.SimpleActivity;
import example.hais.s2018.utils.ToastUtils;

/**
 * Created by Huanghs on 2018/1/17.
 */

public class BaseActivity extends AppCompatActivity {
    protected Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BaseApplication.getInstance().add(this);
        activity = this;
        super.onCreate(savedInstanceState);
        // 竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
        BaseApplication.getInstance().remove(this);
    }

    /**
     * 简单Activity跳转
     *
     * @param cla
     */
    public void showActivity(Class cla) {
        startActivity(new Intent(this, cla));
    }

    /**
     * 简单Activity跳转
     *
     * @param cla
     */
    public void showActivity(Class cla, int requestCode) {
        startActivityForResult(new Intent(this, cla), requestCode);
    }

    /**
     * 长时间Toast
     *
     * @param str
     */
    protected void toastLong(String str) {
        ToastUtils.showLong(getApplicationContext(), str);
    }

    /**
     * 短时间Toast
     *
     * @param str
     */
    protected void toastShort(String str) {
        ToastUtils.showShort(getApplicationContext(), str);
    }

    /**
     * 提供给子类的显示和隐藏View，避免空指针
     *
     * @param view
     * @param isShow
     */
    protected void showView(View view, boolean isShow) {
        if (view != null)
            view.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //添加fragment
    protected void addFragment(Fragment fragment, int resId) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(resId, fragment)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
