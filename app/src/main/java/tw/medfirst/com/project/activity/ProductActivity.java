package tw.medfirst.com.project.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.common.collect.ArrayTable;

import java.util.ArrayList;
import java.util.List;

import tw.medfirst.com.project.Entity.ProductEntity;
import tw.medfirst.com.project.R;
import tw.medfirst.com.project.adapter.ViewPagerAdapter;
import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.baseview.BaseActivity;
import tw.medfirst.com.project.baseview.ExoPlayerLayout;
import tw.medfirst.com.project.baseview.NLCustomDialog;
import tw.medfirst.com.project.baseview.NLViewPager;
import tw.medfirst.com.project.baseview.ViewPagerIndicator;
import tw.medfirst.com.project.manager.MessageManager;

/**
 * Created by KCTsai on 2015/8/24.
 */
public class ProductActivity extends BaseActivity{
    private List<View> viewList;
//    private LayoutInflater mInflater;
    private ViewGroup infoView;
    private NLViewPager viewPager;
    private ViewPagerIndicator indicator;
    private ViewPagerAdapter pagerAdapter;
    private boolean b = true;
    private OnActivityStateChangeListener mActivityStateChangeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        layoutID = R.layout.activity_product;
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

    }

    private List<ProductEntity> sample(){
        List<ProductEntity> list = new ArrayList<>();
        ProductEntity p1 = new ProductEntity("555", "p", "635714285841680854.jpg", "", 1);
        ProductEntity p2 = new ProductEntity("555", "p", "635714285841750858.jpg", "", 2);
        ProductEntity p3 = new ProductEntity("555", "p", "635714285841810861.jpg", "", 3);
        ProductEntity p4 = new ProductEntity("555", "v", "635724721531434309.mp4", "", 4);
        list.add(p4);
        list.add(p1);
        list.add(p2);
        list.add(p3);

        return list;
    }

    private List<ProductEntity> sample2(){
        List<ProductEntity> list = new ArrayList<>();
        ProductEntity p1 = new ProductEntity("555", "p", "635714285841680854.jpg", "", 1);
        ProductEntity p2 = new ProductEntity("555", "p", "635714285841750858.jpg", "", 2);
        ProductEntity p3 = new ProductEntity("555", "v", "635724721531434309.mp4", "", 3);
        ProductEntity p4 = new ProductEntity("555", "v", "635726599845794411.mp4", "", 4);
        ProductEntity p5 = new ProductEntity("555", "p", "635714285841810861.jpg", "", 5);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        return list;
    }

    @Override
    protected void processMessage(Message msg) {

    }

    @Override
    protected void processLoading() {

    }

    @Override
    protected void init() {
        infoView = (RelativeLayout)findViewById(R.id.info_root_view);
        viewPager = (NLViewPager) findViewById(R.id.viewpager);
        indicator = (ViewPagerIndicator) infoView.findViewById(R.id.indicator);

        viewPager.setIndicator(indicator);
        infoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        runSwitch = false;
        if(mActivityStateChangeListener != null)
            mActivityStateChangeListener.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mActivityStateChangeListener != null)
            mActivityStateChangeListener.onPause();
    }

    public void onChangeClick(View v){
        infoView.setVisibility(View.VISIBLE);
        if(b){
            createProductViewPager(sample());
        }else{
            createProductViewPager(sample2());
        }

    }

    public void onCancelClick(View v){
        infoView.setVisibility(View.GONE);
        pagerAdapter.clearAdapterViews();
        mActivityStateChangeListener = null;
//        viewPager.removeAllViews();
        b = !b;
    }

    private void createProductViewPager(List<ProductEntity> viewList) {
        if(viewList == null)
            return;

//        if(viewPager.getAdapter() != null)
//            pagerAdapter = (ViewPagerAdapter) viewPager.getAdapter();
        pagerAdapter = new ViewPagerAdapter(this);
        pagerAdapter.setEntityList(viewList);
        pagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pagerAdapter);
        mActivityStateChangeListener = viewPager.getActivityStateChangeListener();

    }

    public void onTurnPageClick(View v){
        if(viewPager == null)
            return;
        int currentItem = viewPager.getCurrentItem();
        switch (v.getId()){
            case R.id.btn_next_page:
                viewPager.setCurrentItem(currentItem + 1, true);
                break;
            case R.id.btn_pre_page:
                viewPager.setCurrentItem(currentItem - 1, true);
                break;
        }
    }

    public interface OnActivityStateChangeListener{
        public void onPause();
        public void onResume();
    }

}
