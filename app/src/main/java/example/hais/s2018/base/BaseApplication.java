package example.hais.s2018.base;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import example.hais.s2018.utils.CrashHandler;

/**
 * Created by Huanghs on 2018/1/17.
 * 基础的BaseApplication
 */

public class BaseApplication extends Application {
    private static BaseApplication mInstance = null;
    //一个用来记录开启过的Activity
    private List<Activity> activities = new ArrayList<>();
    //是否使用调试
    public static boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        initCrashHandler(true);
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    /***
     * 初始化奔溃处理器
     *
     * @param isDebug
     */
    public void initCrashHandler(boolean isDebug) {
        // 初始化自定义奔溃异常处理器
        if (isDebug) {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());
        }
    }

    //---------------------------Activty 栈管理开始-----------------------------------------------

    /**
     * 添加一个activity到集合中
     *
     * @param activity
     */
    public void add(Activity activity) {
        activities.add(activity);
    }

    /**
     * 从集合中移除一个activity
     *
     * @param activity
     */
    public void remove(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 从集合中移除所有activity
     *
     * @param activity
     */
    public void removeAllActivity(Activity activity) {
        for (Activity atv : activities) {
            if (atv != null) {
                atv.finish();
            }
        }
        activities.clear();
    }

    /**
     * 清空关闭所有activity，并重启程序
     */
    public void ExitApp() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        activities.clear();
        // 退出程序
        System.exit(0);
    }
    //---------------------------Activty 栈管理结束-----------------------------------------------
}
