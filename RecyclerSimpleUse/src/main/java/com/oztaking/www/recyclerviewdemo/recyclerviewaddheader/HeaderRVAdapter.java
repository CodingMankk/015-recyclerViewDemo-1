package com.oztaking.www.recyclerviewdemo.recyclerviewaddheader;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.oztaking.www.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @function: 【思路】通过控制 Adapter的 itemType来设置的，思路就是根据不同的itemType去加载不同的布局。
 * 具体实现：getItemViewType获取类型，然后在onCreateViewHolder中通过类型判断来create相应类型的item；
 */

public class HeaderRVAdapter extends RecyclerView.Adapter<HeaderRVAdapter.Holder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;

    private List<String> mDatas = new ArrayList<>();

    private View mHeaderView;

    private OnItemClickListener mListener;


    private List<Integer> mHeights; //瀑布流布局的随机高度记录集合
    private final LayoutInflater mInflater;


    public HeaderRVAdapter(Context context, List<String> data) {
        this.mDatas = data;
        mInflater = LayoutInflater.from(context);
        mHeights = new ArrayList<>();
        int size = mDatas.size();
//        int size = 100;
        Logger.d("mDatas size:" + mDatas.size());
        for (int i = 0; i < size; i++) {
            int random = (int) (Math.random() * 300 + 300);
            mHeights.add(random);

        }
        Logger.d("mHeights size:" + mHeights.size());
    }

    @Override
    public HeaderRVAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new Holder(mHeaderView);
        }
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_recyclersimple, parent, false);
        return new Holder(view);
    }

    // 设置数据
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Logger.d("position:" + position);
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }

        //考虑到headerView 需要position -1；
        final int pos = getRealPosition(holder);
        Logger.d("pos:" + pos);
        //设置瀑布流的随机高度
        ViewGroup.LayoutParams layoutParams = holder.mTv.getLayoutParams();
        if (mHeights != null && mHeights.size() > 0) {
            layoutParams.height = mHeights.get(pos);
        }

        final String data = mDatas.get(pos);
        Logger.d("data:" + data);
        if (holder instanceof Holder) {
            holder.mTv.setLayoutParams(layoutParams);
            holder.mTv.setText(data);
            if (mListener == null) {
                return;
            }

            //点击之后将数据pos、data传递出去
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos, data);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    return false;
                    mListener.onItemLongClick(pos);
                    return true;
                }
            });
        }
    }


    public int getRealPosition(ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    /**
     * 返回item的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }

        if (position == 0) {
            return TYPE_HEADER;
        }
        //默认返回普通item
        return TYPE_NORMAL;
    }

    public void addDatas(List<String> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addHeaderData(int position) {
        mDatas.add(position, "我是头部");
        notifyItemInserted(position);
    }

    public void removeHeaderData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }


    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position, String data);

        void onItemLongClick(int position);
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView mTv;

        public Holder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }

            mTv = (TextView) itemView.findViewById(R.id.num);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                //方法的返回值决定每个position上的item占据的单元格个数
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(HeaderRVAdapter.Holder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            params.setFullSpan(holder.getAdapterPosition() == 0);

        }
    }
}
