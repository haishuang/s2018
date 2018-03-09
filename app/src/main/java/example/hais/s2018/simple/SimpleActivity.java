package example.hais.s2018.simple;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.hais.s2018.R;
import example.hais.s2018.base.BaseActivity;
import example.hais.s2018.utils.LogUtils;
import example.hais.s2018.utils.SharedPrefUtil;

public class SimpleActivity extends BaseActivity {

    @Bind(R.id.tv_01)
    TextView tv01;
    @Bind(R.id.tv_light)
    TextView tvLight;
    @Bind(R.id.tv_battery_info)
    TextView tvBatteryInfo;
    @Bind(R.id.seekBar)
    SeekBar seekBar;
    @Bind(R.id.rb_keep_on)
    CheckBox cbKeepOn;

    private BatteryChangedReceiver batteryChangedReceiver;
    WindowManager.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPrefUtil.get(this, "keepOn", false)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);
        seekBar.setMax(100);
        lp = getWindow().getAttributes();
        //LogUtils.e("--------------------" + lp.screenBrightness);
        seekBar.setProgress((int) (lp.screenBrightness * 100));
            cbKeepOn.setChecked(SharedPrefUtil.get(this, "keepOn", false));
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                lp.screenBrightness = Float.valueOf(progress) * (1f / 100f);
                LogUtils.e("--------------------" + lp.screenBrightness);
                // 调节亮度
                getWindow().setAttributes(lp);
                tvLight.setText("当前亮度：" + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        cbKeepOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPrefUtil.put(activity, "keepOn", isChecked);
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegistBatter();
    }

    private void initView() {
        //1.View四周设置图片
        Drawable drawableCai = getResources().getDrawable(R.drawable.simple_zan);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        drawableCai.setBounds(0, 0, drawableCai.getMinimumWidth(), drawableCai.getMinimumHeight());
        tv01.setCompoundDrawables(drawableCai, drawableCai, drawableCai, drawableCai); //设置左图标
        batteryChangedReceiver = new BatteryChangedReceiver();
        registBatter();
    }

    //注册电量监听的Broadcastreceiver
    public void registBatter() {
        IntentFilter intentFilter = getFilter();
        registerReceiver(batteryChangedReceiver, intentFilter);
        Toast.makeText(this, "电量变化的receiver已经注册成功", Toast.LENGTH_SHORT).show();
    }

    //取消注册电量监听的Broadcastreceiver
    public void unRegistBatter() {
        unregisterReceiver(batteryChangedReceiver);
        Toast.makeText(this, "电量变化的receiver已经取消注册",
                Toast.LENGTH_SHORT).show();
    }

    ///获取IntentFilter对象
    private IntentFilter getFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        return filter;
    }

    public class BatteryChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            final String action = intent.getAction();
            String batteryInfo = "";
            if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_CHANGED)) {
                // 当前电池的电压
                int voltage = intent.
                        getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
                // 电池的健康状态
                int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
                batteryInfo = "健康状态:";
                switch (health) {
                    case BatteryManager.BATTERY_HEALTH_GOOD:
                        batteryInfo += "很好";
                    case BatteryManager.BATTERY_HEALTH_COLD:
                        batteryInfo += "BATTERY_HEALTH_COLD";
                        break;
                    case BatteryManager.BATTERY_HEALTH_DEAD:
                        batteryInfo += "BATTERY_HEALTH_DEAD";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                        batteryInfo += "BATTERY_HEALTH_OVERHEAT";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                        batteryInfo += "BATTERY_HEALTH_OVER_VOLTAGE";
                        break;
                    case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                        batteryInfo += "BATTERY_HEALTH_UNKNOWN";
                        break;
                    case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                        batteryInfo += "BATTERY_HEALTH_UNSPECIFIED_FAILURE";
                        break;
                    default:
                        break;
                }
                // 电池当前的电量, 它介于0和 EXTRA_SCALE之间
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                batteryInfo += "\n当前电量:" + level;

                // 电池电量的最大值
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                batteryInfo += "\n最大电量:" + scale;
                // 当前手机使用的是哪里的电源
                int pluged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                batteryInfo += "\n耗电来源:" + (pluged == BatteryManager.BATTERY_PLUGGED_AC ? "AC" : "USB");

                batteryInfo += "\n电量状态:" + scale;
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                switch (status) {
                    case BatteryManager.BATTERY_STATUS_CHARGING:
                        // 正在充电
                        batteryInfo += "正在充电";
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        batteryInfo += "BATTERY_STATUS_DISCHARGING";
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL:
                        // 充满
                        batteryInfo += "充满";
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                        // 没有充电
                        batteryInfo += "没有充电";
                        break;
                    case BatteryManager.BATTERY_STATUS_UNKNOWN:
                        // 未知状态
                        batteryInfo += "未知状态";
                        break;
                    default:
                        break;
                }
                // 电池使用的技术。比如，对于锂电池是Li-ion
                String technology = intent.
                        getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
                // 当前电池的温度
                int temperature = intent.
                        getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
                String str = "voltage = " + voltage + " technology = "
                        + technology + " temperature = " + temperature;
                batteryInfo += "\n" + str;
            } else if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_LOW)) {
                // 表示当前电池电量低
            } else if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_OKAY)) {
                // 表示当前电池已经从电量低恢复为正常
                System.out.println(
                        "BatteryChangedReceiver ACTION_BATTERY_OKAY---");
            }

            tvBatteryInfo.setText(batteryInfo);
        }

    }
}
