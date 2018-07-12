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

import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;
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
    private boolean mShowRelativity;
    private int screen;
    private GridLayoutManager.LayoutParams mParams;
    private LayoutInflater mLayoutInflater;

    private List<String> mList = new ArrayList<>();

    private onItemClickListener mListener;

//    private boolean showCheckBox;

    /**
     * 防止Checkbox错乱 做setTag getTag操作
     */
    private SparseBooleanArray mCheckStates = new SparseBooleanArray(); //存储的是存储的是CheckBox选中的状态
    private SparseBooleanArray mRlStates = new SparseBooleanArray(); //存储的是存储的是Releativity选中的状态
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


    public void setShowRelativityBackground(boolean showRelativity){
        this.mShowRelativity = showRelativity;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_recyclerviewmutiselector, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        holder.mCBItem.setTag(position);
        holder.mRllayout.setTag(position);

        mParams = (GridLayoutManager.LayoutParams)holder.mRllayout.getLayoutParams();
        mParams.width = screen/3;
        mParams.height = screen/3;

        /**
         *  注意：在长按的时候，单个checkbox的状态没有必要设置，即使设置CheckStates中的数据为空
         */
        if (mShowCheckBox){ //如果是长按：此值由activity传入设置
            holder.mCBItem.setVisibility(View.VISIBLE);
            holder.mCBItem.setChecked(mCheckStates.get(position,false)); //从列表中获取状态并设置状态
        }else{
            holder.mCBItem.setVisibility(View.GONE);
            holder.mCBItem.setChecked(false);//设置未选中状态
            mCheckStates.clear(); //list状态清除
        }

        if (mShowRelativity){
            holder.mRllayout.setLayoutClick(mRlStates.get(position,false));
        }else{
            holder.mRllayout.setLayoutClick(false);
            mRlStates.clear();
        }


        //
        holder.mRllayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShowCheckBox && mShowRelativity){
                    //点击之后对是否选中切换显示
                    boolean checked = holder.mCBItem.isChecked();
                    if (checked == true){
                        checked = false;
                        holder.mRllayout.setLayoutClick(false);
                        mRlStates.put(position,false);
                    }else {
                        checked = true;
                        holder.mRllayout.setLayoutClick(true);
//                        mRlStates.put(position,true);
                        mRlStates.delete(position);
                    }
                    holder.mCBItem.setChecked(checked);
//                    holder.mCBItem.setChecked(!holder.mCBItem.isChecked());
//                    holder.mRllayout.setLayoutClick(!holder.mRllayout.isLayoutClick());
//                    holder.mRllayout.setLayoutClick(true);
                }


//                else{
//                    holder.mRllayout.setLayoutClick(false);
//                }

//                holder.mRllayout.setLayoutClick(false);
//                    holder.mRllayout.setLayoutClick(false);

                mListener.onItemClick(v,position); //返回数据
            }
        });



        /**
         * 返回的值的说明：public boolean onLongClick(View v)
         *参数v：参数v为事件源控件，当长时间按下此控件时才会触发该方法。
         *返回值：该方法的返回值为一个boolean类型的变量，
         *当返回true时，表示已经完整地处理了这个事件，并不希望其他的回调方法再次进行处理；
         *当返回false时，表示并没有完全处理完该事件，更希望其他方法继续对其进行处理。
         */

        holder.mRllayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                holder.mRllayout.setLayoutClick(false);
//                if (!isShowCheckBox()){
//                    holder.mRllayout.setLayoutClick(false);
//                }
                return mListener.onItemLongClick(v,position); //返回数据
            }
        });

        /**
         * 在列表中添加数据
         */
        holder.mCBItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos  = (int)buttonView.getTag();
                Logger.d(buttonView.getParent());
                if (isChecked){
                    mCheckStates.put(pos,true); //点击选中，则加入到list表
                }else {
                    mCheckStates.delete(pos); //未点击选中，则从列表中删除
                }
            }
        });

        holder.mSimpleDraweeView.setImageURI(uri);
//        holder.mSimpleDraweeView.setImageResource(R.drawable.tomcat);

    }


    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        return position;
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
        private RelativeStateLayout mRllayout;
//        private RelativeLayout mRllayout;

        public Holder(View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.item_Image_multiSelect);
            mCBItem = itemView.findViewById(R.id.cb1_item);
            mRllayout = itemView.findViewById(R.id.rl_layout);

        }
    }
}
