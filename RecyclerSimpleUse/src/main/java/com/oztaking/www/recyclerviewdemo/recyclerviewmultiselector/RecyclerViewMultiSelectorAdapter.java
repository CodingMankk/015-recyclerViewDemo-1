package com.oztaking.www.recyclerviewdemo.recyclerviewmultiselector;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.oztaking.www.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @function
 * @author
 */

public class RecyclerViewMultiSelectorAdapter extends RecyclerView.Adapter<RecyclerViewMultiSelectorAdapter.Holder>{


    private Context mContext;
    private boolean mShowCheckBox;
    private int screen;
    private GridLayoutManager.LayoutParams mParams;
    private List<String> mList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;

    private onItemClickListener mListener;

//    private boolean showCheckBox;

    /**
     * 防止Checkbox错乱 做setTag getTag操作
     */
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();
    /**
     * frasco 使用
     */
    private Uri uri;

    public RecyclerViewMultiSelectorAdapter(Context context, List<String> list,int screenWidth) {
        this.mContext = context;
        this.screen = screenWidth;
        this.mList = list;
        mLayoutInflater = LayoutInflater.from(context);
        //frasco 使用
//        uri = Uri.parse("res:///" + R.drawable.tomcat);
        uri = Uri.parse("res:///" + R.mipmap.imgv_fitness_girl);
    }

    public boolean isShowCheckBox(){
        return mShowCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {

        this.mShowCheckBox = showCheckBox;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_recyclerviewmutiselector, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.mCBItem.setTag(position);

        mParams = (GridLayoutManager.LayoutParams)holder.mRllayout.getLayoutParams();
        mParams.width = screen/3;
        mParams.height = screen/3;

        if (mShowCheckBox){
            holder.mCBItem.setVisibility(View.VISIBLE);
            holder.mCBItem.setChecked(mCheckStates.get(position,false));
        }else{
            holder.mCBItem.setVisibility(View.GONE);
            holder.mCBItem.setChecked(false);
            mCheckStates.clear();;
        }

        //
        holder.mRllayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShowCheckBox){
                    holder.mCBItem.setChecked(!holder.mCBItem.isChecked());
                }
                mListener.onItemClick(v,position);

            }
        });

        holder.mRllayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mListener.onItemLongClick(v,position);
            }
        });

        holder.mCBItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos  = (int)buttonView.getTag();
                if (isChecked){
                    mCheckStates.put(pos,true);
                }else {
                    mCheckStates.delete(pos);
                }
            }
        });

        holder.mSimpleDraweeView.setImageURI(uri);
//        holder.mSimpleDraweeView.setImageResource(R.drawable.tomcat);

    }


    @Override
    public int getItemCount() {

        //        return mList.size();
        return 20;

    }

    public void setListener(onItemClickListener listener) {
        this.mListener = listener;
    }

    public interface onItemClickListener{
        public void onItemClick(View view, int pos);
        public boolean onItemLongClick(View view, int pos);
    }


    class Holder extends RecyclerView.ViewHolder{

        private SimpleDraweeView mSimpleDraweeView;
        private CheckBox mCBItem;
        private RelativeLayout mRllayout;

        public Holder(View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.item_Image_multiSelect);
            mCBItem = itemView.findViewById(R.id.cb1_item);
            mRllayout = itemView.findViewById(R.id.rl_layout);

        }
    }
}
