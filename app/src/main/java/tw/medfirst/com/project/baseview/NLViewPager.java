package tw.medfirst.com.project.baseview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.lang.reflect.Field;

import tw.medfirst.com.project.activity.ProductActivity;
import tw.medfirst.com.project.adapter.ViewPagerAdapter;
import tw.medfirst.com.project.baseunit.FixedSpeedScroller;
import tw.medfirst.com.project.baseunit.Logger;

/**
 * Created by KCTsai on 2015/7/9.
 */
public class NLViewPager extends ViewPager implements ProductActivity.OnActivityStateChangeListener {
    protected int childCount;
    protected ViewPagerIndicator indicator;
    protected ViewPagerAdapter mAdapter;
    protected int currentPosition;
    public NLViewPager(Context context) {
        super(context);
        setOnPageChangeListener();
        setViewPagerScrollSpeed();

    }
    public NLViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnPageChangeListener();
        setViewPagerScrollSpeed();
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
//        Logger.e("setAdapter", adapter.getCount());
        childCount = adapter.getCount();
        currentPosition = 0;
        indicator.initView(childCount);
        mAdapter = (ViewPagerAdapter) adapter;
        super.setAdapter(adapter);
        if(childCount != 0 && mAdapter != null && mAdapter.getView(0) instanceof ExoPlayerLayout)
            ((ExoPlayerLayout) mAdapter.getView(0)).preparePlayer(0);

    }

    public void setOnPageChangeListener(){
        super.setOnPageChangeListener(listener);
    }


    protected OnPageChangeListener listener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
//            Log.e("onPageSelected", Integer.toString(position));
//            stopAllVideo();
            if((mAdapter.getView(currentPosition)) instanceof ExoPlayerLayout) {
                ((ExoPlayerLayout)mAdapter.getView(currentPosition)).stopVideo();
            }
            if((mAdapter.getView(position)) instanceof ExoPlayerLayout) {
//                setOffscreenPageLimit(position);
                ((ExoPlayerLayout)mAdapter.getView(position)).preparePlayer(0);

            }

            indicator.ClearSelected();
            indicator.setSelected(position);
            currentPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };



    protected void stopAllVideo(){
        for(int i = 0; i < childCount; i++){
            if((mAdapter.getView(i)) instanceof ExoPlayerLayout){
                ((ExoPlayerLayout)mAdapter.getView(i)).stopVideo();
            }
        }
    }

    public void setIndicator(ViewPagerIndicator indicator) {
        this.indicator = indicator;
    }

    private void setViewPagerScrollSpeed(){
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getContext());
            mScroller.set( this, scroller);
        }catch(NoSuchFieldException e){

        }catch (IllegalArgumentException e){

        }catch (IllegalAccessException e){

        }
    }


    public ProductActivity.OnActivityStateChangeListener getActivityStateChangeListener() {
        return this;
    }

    @Override
    public void onPause() {
        Logger.e("ViewPager", "pausePlayer");
        if(mAdapter.getView(currentPosition) instanceof ExoPlayerLayout){
            ((ExoPlayerLayout) mAdapter.getView(currentPosition)).pausePlayer();
        }

    }

    @Override
    public void onResume() {
        Logger.e("ViewPager", "pausePlayer");
        if(mAdapter.getView(currentPosition) instanceof ExoPlayerLayout){
            ((ExoPlayerLayout) mAdapter.getView(currentPosition)).resumePlayer();
        }

    }
}
