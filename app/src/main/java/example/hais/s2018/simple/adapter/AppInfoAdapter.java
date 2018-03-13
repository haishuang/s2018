package example.hais.s2018.simple.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.hais.s2018.R;
import example.hais.s2018.base.BaseRecyclerViewAdapter;
import example.hais.s2018.bean.AppInfo;

/**
 * Created by Administrator on 2018/3/13.
 */

public class AppInfoAdapter extends BaseRecyclerViewAdapter<AppInfoAdapter.AppInfoVH> {
    private Context context;
    private List<AppInfo> appInfos;

    public AppInfoAdapter(Context context, List<AppInfo> appInfos) {
        this.context = context;
        this.appInfos = appInfos;
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AppInfoVH) {
            AppInfo appInfo = appInfos.get(position);
            ((AppInfoVH) holder).tvTitle.setText(TextUtils.isEmpty(appInfo.getAppName()) ? "" : appInfo.getAppName());
            ((AppInfoVH) holder).tvPackage.setText(TextUtils.isEmpty(appInfo.getPackageName()) ? "" : appInfo.getPackageName());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppInfoVH(LayoutInflater.from(context).inflate(R.layout.item_simple_app_info, null));
    }

    @Override
    public int getItemCount() {
        return null == appInfos ? 0 : appInfos.size();
    }

    class AppInfoVH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPackage;

        public AppInfoVH(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.appinfo_text);
            tvPackage = (TextView) itemView.findViewById(R.id.appinfo_package);
        }
    }
}
