package com.oztaking.www.recyclerviewdemo.recyclerviewmultiselector;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.oztaking.www.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @function: 长按出现item的多选checkBox，选中的item的背景会发生变化，点击button“选中的数据”
 * 会将选中的item的位置Toast出来，并取消checkBox和item背景
 *
 * [思路]
 *
 * 单/长按点击事件的对象：item中的根部局RelativeLayout
 *      在Adapter中对该事件进行监听，并处理；
 *      单击onClick事件的处理：【1】切换CheckBox的状态【2】调用onItemClickListener中的onClick方法，回传数据；
 *      长按onLongClick事件的处理：【1】调用onItemLongClick，回传数据；
 *      ============
 *    首先说明：在Activity中维护了“checkList”：记录单击的CheckBox的位置
 *      在Acitivity中点击事件的处理：
 *      onItemClick：checkList数据的更新
 *      onItemLongClick：CheckBox的显示切换
 * ---------------------
 *
 * 【说明】
 * 在Adapter中：维护了SparseBooleanArray mCheckStates表，记录checkBox点击的pos与isChecked状态的对应关系；
 *
 */

public class RecyclerViewMultiSelectorActivity extends Activity{

    private RecyclerView mRecyclerView;
    private List<String> mListPic;
    private Button mBtn;
    private RecyclerViewMultiSelectorAdapter mAdapter;
//    private RecAdapter mAdapter;

    private List<String> checkList; //存储的是CheckBox选中的位置

    private boolean isShowCheck;

//    private RelativeLayout mRllayout;
    private RelativeStateLayout mRllayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_multiselector);

        initView();

        initData();

        initListener();
    }

    private void initListener() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerViewMultiSelectorActivity.this, checkList.toString(), Toast.LENGTH_SHORT)
                        .show();
                mAdapter.setShowCheckBox(false);
                mAdapter.setShowRelativityBackground(false);
                mAdapter.notifyDataSetChanged();
            }
        });

        /**
         * 功能：对item点击之后，checkList数据的更新
         */
        mAdapter.setListener(new RecyclerViewMultiSelectorAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
//                Toast.makeText(RecyclerViewMultiSelectorActivity.this, pos + "", Toast.LENGTH_SHORT)
//                        .show();
//                Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.linearlayoutbg);
//                mRllayout.setBackground(drawable);

                //控制checkBoxlist的数据的添加/删除
                if (checkList.contains(String.valueOf(pos))) {
                    checkList.remove(String.valueOf(pos));
                } else {
                    checkList.add(String.valueOf(pos));
                }
            }

            @Override
            public boolean onItemLongClick(View view, int pos) {
//                Toast.makeText(RecyclerViewMultiSelectorActivity.this, "长按" + pos, Toast.LENGTH_SHORT)
//                        .show();
                if (!isShowCheck) {  //0
//                    mBtn.setVisibility(View.GONE);
                    mAdapter.setShowCheckBox(false); //长按不显示CheckBox
                    mAdapter.setShowRelativityBackground(false);
                    mAdapter.notifyDataSetChanged();
                    checkList.clear();
                } else {
                    mAdapter.setShowCheckBox(true);
                    mAdapter.setShowRelativityBackground(true);
                    mAdapter.notifyDataSetChanged();
//                    mBtn.setVisibility(View.VISIBLE);
                }
                isShowCheck = !isShowCheck; //1

                return false;
//                return true;
            }
        });
    }

    private void initData() {
        //获取的屏幕的宽度
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;

        mListPic = new ArrayList<>();
        mListPic.add("1");

        checkList = new ArrayList<>();

        mAdapter = new RecyclerViewMultiSelectorAdapter(this,mListPic,screenWidth);
//        mAdapter = new RecAdapter(this,screenWidth,mListPic);
        mRecyclerView.setAdapter(mAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);

    }

    private void initView() {
        mRecyclerView = findViewById(R.id.multiSelector_rv);
        mBtn = (Button) findViewById(R.id.Btn);
//        mRllayout = (RelativeLayout) findViewById(R.id.rl_layout);
        mRllayout = (RelativeStateLayout) findViewById(R.id.rl_layout);
    }
}
