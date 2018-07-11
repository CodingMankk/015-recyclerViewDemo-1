package com.oztaking.www.recyclerviewdemo.recyclerviewmultiselector;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.oztaking.www.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @function:
 */

public class RecyclerViewMultiSelectorActivity extends Activity{

    private RecyclerView mRecyclerView;
    private List<String> mListPic;
    private Button mBtn;
    private RecyclerViewMultiSelectorAdapter mAdapter;
//    private RecAdapter mAdapter;

    private List<String> checkList;
    private boolean isShowCheck;

    private RelativeLayout mRllayout;


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
            }
        });

        mAdapter.setListener(new RecyclerViewMultiSelectorAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
//                Toast.makeText(RecyclerViewMultiSelectorActivity.this, pos + "", Toast.LENGTH_SHORT)
//                        .show();
                Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.linearlayoutbg);
                mRllayout.setBackground(drawable);

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
                if (isShowCheck) {
                    mBtn.setVisibility(View.GONE);
                    mAdapter.setShowCheckBox(false);



                    mAdapter.notifyDataSetChanged();
                    checkList.clear();
                } else {
                    mAdapter.setShowCheckBox(true);
                    mAdapter.notifyDataSetChanged();
                    mBtn.setVisibility(View.VISIBLE);
                }
                isShowCheck = !isShowCheck;
                return false;
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
//        for (int i=0; i<20; i++){
//            mListPic.add(R.drawable.tomcat);
//        }

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
        mRllayout = (RelativeLayout) findViewById(R.id.rl_layout);

    }






}
