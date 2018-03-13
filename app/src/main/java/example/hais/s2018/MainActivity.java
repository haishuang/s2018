package example.hais.s2018;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.hais.s2018.base.BaseActivity;
import example.hais.s2018.base.BaseRecyclerViewAdapter;
import example.hais.s2018.mine.LoginActivity;
import example.hais.s2018.permission.PermissionActivity;
import example.hais.s2018.simple.SimpleActivity;
import example.hais.s2018.web.WebLoadActivity;

public class MainActivity extends BaseActivity {

    @Bind(R.id.rcv)
    RecyclerView rcv;

    private List<String> menus = new ArrayList<>();
    MainAdapter mainAdapter;

    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getData();
        rcv.setLayoutManager(new GridLayoutManager(this, 2));
        rcv.setAdapter(mainAdapter = new MainAdapter());
        rcv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
    }

    class MainAdapter extends BaseRecyclerViewAdapter<MainAdapter.MyViewHolder> {

        @Override
        public void onBindView(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolder) {
                String text = menus.get(position);
                ((MyViewHolder) holder).tv.setText(TextUtils.isEmpty(text) ? "" : text);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_main, null));
        }

        @Override
        public int getItemCount() {
            return menus.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv_main);
            }
        }
    }

    private void getData() {
        menus.add("简单功能集成");
        menus.add("web加载");
        menus.add("登录");
        menus.add("权限");
        menus.add("手机应用");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        showActivity(SimpleActivity.class);
                        break;
                    case 1:
                        showActivity(WebLoadActivity.class);
                        break;
                    case 2:
                        showActivity(LoginActivity.class);
                        break;
                    case 3:
                        showActivity(PermissionActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
