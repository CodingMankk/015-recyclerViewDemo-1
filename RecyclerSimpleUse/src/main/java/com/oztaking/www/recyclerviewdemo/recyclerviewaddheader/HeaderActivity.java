package com.oztaking.www.recyclerviewdemo.recyclerviewaddheader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.oztaking.www.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @function:
 */

public class HeaderActivity extends AppCompatActivity{

    private RecyclerView mRv;
    private HeaderRVAdapter mHeaderRVAdapter;

    private List<String> mDatas;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initData();

        mRv = (RecyclerView) findViewById(R.id.rv_recyclerView);

        //[1]LinearLayoutManager
      /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mRv.setLayoutManager(linearLayoutManager);*/

        //[2]GrideView
     /*   GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mRv.setLayoutManager(gridLayoutManager);*/


        //[3]StaggeredGridLayoutManager
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRv.setLayoutManager(staggeredGridLayoutManager);

        mRv.addItemDecoration(new DividerItemDecorationHeader(this,true,DividerItemDecorationHeader.VERTICAL_LIST));
//        mRv.setItemAnimator(new DefaultItemAnimator());

        mHeaderRVAdapter = new HeaderRVAdapter(this,mDatas);
        mRv.setAdapter(mHeaderRVAdapter);
        /**
         * 注意：//使用随机高度时，不能再addDatas，否则会重复添加数据；
         */
//        mHeaderRVAdapter.addDatas(mDatas);
        setHeaders(mRv);

        mHeaderRVAdapter.setOnItemClickListener(new HeaderRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String data) {

                Toast.makeText(HeaderActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                Logger.d(String.valueOf(position));
            }

            @Override
            public void onItemLongClick(int position) {
                mHeaderRVAdapter.removeHeaderData(position);
            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.action_add:
////                mHeaderRVAdapter.addData(0,"我是头部");
//                setHeaders(mRv);
//                mHeaderRVAdapter.addHeaderData(0);
//            break;
//            case R.id.action_remove:
////                mDatas.remove(0);
//                mHeaderRVAdapter.removeHeaderData(0);
//                break;
//            default:
//                break;
//        }
//
////        return super.onOptionsItemSelected(item);
//        return true;
//    }

    private void initData() {
        mDatas = new ArrayList<>();
//        for (int i='A'; i<'z'; i++){
//            mDatas.add(" "+(char)i);
//        }

        for (int i='0'; i<'5'; i++){
            mDatas.add(" "+(char)i);
        }

        for(int i=0; i<mDatas.size(); i++){
            String s = mDatas.get(i);
            Log.i("HeaderActivity",s);
        }
    }

    private void setHeaders(RecyclerView recyclerView){
        View view = LayoutInflater.from(this).inflate(R.layout.item_recyclerheader,
                recyclerView, false);
        mHeaderRVAdapter.setHeaderView(view);
    }
}
