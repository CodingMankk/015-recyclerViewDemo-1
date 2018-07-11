package com.oztaking.www.recyclerviewdemo.recyclerbaseuse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oztaking.www.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @function
 */

public class StaggeredHomeAdapter extends RecyclerView.Adapter<StaggeredHomeAdapter.MyViewHolder> {

    private List<String> mDatas;
    private LayoutInflater mInflater;

    private List<Integer> mHeights;

    public StaggeredHomeAdapter(Context context, List<String> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;

        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < mDatas.size(); i++) {
            mHeights.add((int) (100 + Math.random() * 300));
        }
    }


    @Override
    public StaggeredHomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder holder = new MyViewHolder(mInflater.inflate(
                R.layout.item_staggered_preessed, parent, false));
        return holder;

    }

    @Override
    public void onBindViewHolder(final StaggeredHomeAdapter.MyViewHolder holder, final int position) {
        //设置了tv的高度
        ViewGroup.LayoutParams lp = holder.tv.getLayoutParams();
        lp.height = mHeights.get(position);
        holder.tv.setLayoutParams(lp);

        holder.tv.setText(mDatas.get(position));

        //点击事件-4:设置监听
        if (mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    //调用方法
                    mOnItemClickListener.onItemClick(holder.itemView,pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView,pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_staggerNum);

        }
    }

    public void addData(int position){
        mDatas.add(position,"Insert one");
        notifyItemInserted(position);
    }

    public void removeData(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }


    /**
     * 增加点击事件
     */

    //点击事件-1
    public interface onItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }


    //点击事件-2
    private onItemClickListener mOnItemClickListener;
    //点击事件-3
    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }


}