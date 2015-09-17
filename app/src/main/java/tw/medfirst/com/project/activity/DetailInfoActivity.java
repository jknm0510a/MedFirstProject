package tw.medfirst.com.project.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import tw.medfirst.com.project.Entity.ProductEntity;
import tw.medfirst.com.project.R;
import tw.medfirst.com.project.adapter.ProductListAdapter;
import tw.medfirst.com.project.adapter.ViewPagerAdapter;
import tw.medfirst.com.project.baseview.NLViewPager;
import tw.medfirst.com.project.baseview.ProductListLinearLayout;
import tw.medfirst.com.project.baseview.ViewPagerIndicator;

/**
 * Created by KCTsai on 2015/9/17.
 */
public abstract class DetailInfoActivity extends ContentActivity{
    //product info view pager
    protected ViewGroup infoView;
    protected NLViewPager viewPager;
    protected ViewPagerIndicator indicator;
    protected ViewPagerAdapter pagerAdapter;
    protected OnActivityStateChangeListener mActivityStateChangeListener;
    protected boolean b = true;

    //products listview
    protected ProductListLinearLayout listLinearLayout;
    protected ProductListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInfoViewPager();
        initListView();
    }

    private void initListView() {
        listLinearLayout = (ProductListLinearLayout) findViewById(R.id.product_list);
        listAdapter = new ProductListAdapter(this);
    }

    private void initInfoViewPager() {
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

    protected void createProductViewPager(List<ProductEntity> viewList) {
        if(viewList == null || infoView == null)
            return;

//        if(viewPager.getAdapter() != null)
//            pagerAdapter = (ViewPagerAdapter) viewPager.getAdapter();
        pagerAdapter = new ViewPagerAdapter(this);
        pagerAdapter.setEntityList(viewList);
        pagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pagerAdapter);
        mActivityStateChangeListener = viewPager.getActivityStateChangeListener();
        infoView.setVisibility(View.VISIBLE);
    }

    public void onViewPagerCancelClick(View v){
        infoView.setVisibility(View.GONE);
        pagerAdapter.clearAdapterViews();
        mActivityStateChangeListener = null;
//        viewPager.removeAllViews();
        b = !b;
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

    protected List<ProductEntity> viewPagerSample(){
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

    protected List<ProductEntity> viewPagerSample2(){
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



    public interface OnActivityStateChangeListener{
        public void onPause();
        public void onResume();
    }

}
