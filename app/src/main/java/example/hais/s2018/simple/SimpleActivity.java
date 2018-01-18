package example.hais.s2018.simple;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.hais.s2018.R;
import example.hais.s2018.base.BaseActivity;

public class SimpleActivity extends BaseActivity {

    @Bind(R.id.tv_01)
    TextView tv01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        //1.View四周设置图片
        Drawable drawableCai = getResources().getDrawable(R.drawable.simple_zan);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        drawableCai.setBounds(0, 0, drawableCai.getMinimumWidth(), drawableCai.getMinimumHeight());
        tv01.setCompoundDrawables(drawableCai, drawableCai, drawableCai, drawableCai); //设置左图标
    }
}
