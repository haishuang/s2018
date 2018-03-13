package example.hais.s2018.simple;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.hais.s2018.R;
import example.hais.s2018.base.BaseActivity;
import example.hais.s2018.bean.AppInfo;
import example.hais.s2018.simple.adapter.AppInfoAdapter;

public class AppInfoActivity extends BaseActivity {
    @Bind(R.id.xrv)
    com.hs.lib.xrecyclerview.XRecyclerView xrv;
    private List<AppInfo> appInfos = new ArrayList<>();
    AppInfoAdapter appInfoAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_app_info);
        ButterKnife.bind(this);
        appInfoAdapter = new AppInfoAdapter(getApplication(), appInfos);
        xrv.setLayoutManager(new LinearLayoutManager(this));
        xrv.setAdapter(appInfoAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAppInfos();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        appInfoAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    // 获取包名信息
    public List<AppInfo> getAppInfos() {
        PackageManager pm = getApplication().getPackageManager();
        List<PackageInfo> packgeInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
       // appInfos = new ArrayList<AppInfo>();
        /* 获取应用程序的名称，不是包名，而是清单文件中的labelname
            String str_name = packageInfo.applicationInfo.loadLabel(pm).toString();
            appInfo.setAppName(str_name);
         */
        for (PackageInfo packgeInfo : packgeInfos) {
            String appName = packgeInfo.applicationInfo.loadLabel(pm).toString();
            String packageName = packgeInfo.packageName;
            Drawable drawable = packgeInfo.applicationInfo.loadIcon(pm);
            AppInfo appInfo = new AppInfo(appName, packageName, drawable);
            appInfos.add(appInfo);
        }
        return appInfos;
    }
}
