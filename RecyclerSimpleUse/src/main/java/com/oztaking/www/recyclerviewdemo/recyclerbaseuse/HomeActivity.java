package com.oztaking.www.recyclerviewdemo.recyclerbaseuse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.oztaking.www.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;
    private StaggeredHomeAdapter mStaggeredHomeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);
        /**
         * [1]ListView样式
         */
       /* //设置LayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); //ListView样式
        //设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        //下面的效果无效
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL_LIST));
*/
        /**
         * [2]GridView样式
         */
        /*mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));*/

        /**
         * [3]瀑布流
         */
//       纵向： item的frameLayout参数设置： android:layout_width="match_parent"  android:layout_height="80dp"
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));

        // 横向：item的frameLayout参数设置：android:layout_width="100dp" android:layout_height="match_parent"
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL));
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        //设置Adapter
       /* mAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mAdapter);*/


        //随机高度瀑布流
        /**
         * 随机高度设置的原理：
         * 1.随机值的产生：维护一个List<Integer> mHeights，在构造方法中add赋值；
         * 2.设置：在StaggeredHomeAdapter的onBindViewHolder中，设置TextView的LayoutParams.height值
         */
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        mStaggeredHomeAdapter = new StaggeredHomeAdapter(this,mDatas);
        mRecyclerView.setAdapter(mStaggeredHomeAdapter);


        //[4]添加动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //[5]设置监听
        mStaggeredHomeAdapter.setOnItemClickListener(new StaggeredHomeAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(HomeActivity.this, position+"被点击", Toast.LENGTH_SHORT).show();
                Logger.d(position+"被点击了");
//                mStaggeredHomeAdapter.addData(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Logger.d(position+"被长按了");
                mStaggeredHomeAdapter.removeData(position);
            }
        });


//        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
//        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
//        int width = mDisplayMetrics.widthPixels;
//        int height = mDisplayMetrics.heightPixels;
//        float density = mDisplayMetrics.density;
//        int densityDpi = mDisplayMetrics.densityDpi;
//        Logger.d("Screen Ratio: ["+width+"x"+height+"],density="+density+",densityDpi="+densityDpi);
//        Logger.d("Screen mDisplayMets: "+mDisplayMetrics);

    }

    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i='A'; i<'z'; i++){
            mDatas.add(" "+(char)i);
        }
    }

    //Menu触发：
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu的item布局加载设置
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //MenuItem点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.action_add:
                mStaggeredHomeAdapter.addData(1);
            break;
            case R.id.action_remove:
                mStaggeredHomeAdapter.removeData(1);
                break;
            default:
                break;
        }
        return true;
    }

    private class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{

        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
            View view = inflater.inflate(R.layout.item_recyclersimple, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {
            int random = (int)Math.random() * 50+position;
            TextView tv = holder.tv;
            tv.setHeight(random);
            tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class  MyViewHolder extends RecyclerView.ViewHolder{
            TextView tv;
            public MyViewHolder(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.num);
            }
        }
    }




}
