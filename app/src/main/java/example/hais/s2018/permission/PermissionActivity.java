package example.hais.s2018.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.hais.s2018.R;
import example.hais.s2018.base.BasePermissionActivity;
import example.hais.s2018.utils.FileUtils;
import example.hais.s2018.utils.ToastUtils;

public class PermissionActivity extends BasePermissionActivity {

    @Bind(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.textView, R.id.tv_permission_call, R.id.tv_permission_sd, R.id.tv_permission_camera})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView:
                break;
            case R.id.tv_permission_call:
                checkCall();
                break;
            case R.id.tv_permission_sd:
                checkSd();
                break;
            case R.id.tv_permission_camera:
                break;
        }
    }

    private void checkCall() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},new String[]{"拨打电话"}, new RequestPermissionCallBack() {
                @Override
                public void granted() {
                    callPhone();
                }

                @Override
                public void denied() {
                    ToastUtils.showLong(activity, "你禁止了打电话权限");
                }
            });
        } else {
            callPhone();
        }
    }
    /**
     * 拨打电话
     */
    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);//直接拨打电话，需要权限
        // Intent intent = new Intent(Intent.ACTION_DIAL);//跳到拨号界面，不需要权限
        Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        startActivity(intent);
    }

    private void checkSd(){
        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.M){
            requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},new String[]{"写入内存设备"}, new RequestPermissionCallBack() {
                @Override
                public void granted() {
                    sd();
                }

                @Override
                public void denied() {
                    ToastUtils.showLong(activity, "你禁止了写入内存设备权限");
                }
            });
        }
    }

    void sd(){
        if(FileUtils.isSdCardExist()){
            ToastUtils.showLong(activity, "SD卡正常可用");
        }else {
            ToastUtils.showLong(activity, "SD卡未挂载");
        }
    }
}
