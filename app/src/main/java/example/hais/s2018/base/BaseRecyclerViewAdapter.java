package example.hais.s2018.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Huanghs on 2017/7/4.
 */

public abstract class BaseRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //定义接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }//定义接口
    public interface OnItemLongClickListener {

        void onItemLongClick(View view, int position);
    }

    //声明接口变量
    private OnItemClickListener listener;
    //声明接口变量
    private OnItemLongClickListener longListener;

    public void setLongListener(OnItemLongClickListener longListener) {
        this.longListener = longListener;
    }

    //暴露给使用者调用
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        onBindView(viewHolder, position);

        if(listener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view,position);
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longListener.onItemLongClick(view,position);
                    //如果是长按事件，那么久不用再触发点击事件了，所以返回true
                    //将事件直接消费掉
                    return true;
                }
            });
        }
    }
    public abstract void onBindView(RecyclerView.ViewHolder holder, int position);
}
