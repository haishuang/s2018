package example.hais.s2018.rich;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.lu.lubottommenu.LuBottomMenu;
import com.lu.richtexteditorlib.SimpleRichEditor;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.hais.s2018.R;
import example.hais.s2018.base.BasePermissionActivity;
import example.hais.s2018.utils.ToastUtils;

public class RichActivity extends BasePermissionActivity implements SimpleRichEditor.OnEditorClickListener {

    @Bind(R.id.rich_text_view)
    SimpleRichEditor richTextView;
    @Bind(R.id.lu_bottom_menu)
    LuBottomMenu luBottomMenu;
    @Bind(R.id.activity_rich)
    RelativeLayout activityRich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich);
        ButterKnife.bind(this);

        // richTextView.addCustomItem(luBottomMenu)
        richTextView.setBottomMenu(luBottomMenu);
        richTextView.setOnEditorClickListener(this);
    }

    @Override
    public void onLinkButtonClick() {

    }

    @Override
    public void onInsertImageButtonClick() {

    }

    @Override
    public void onLinkClick(String name, String url) {

    }

    @Override
    public void onImageClick(Long id) {

    }

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        ToastUtils.showLong(activity,richTextView.getHtml()+"---");
        Log.e("TAG","---------"+richTextView.getHtml());

        richTextView.setHtml("<b>扣扣空间</b>");

    }
}
