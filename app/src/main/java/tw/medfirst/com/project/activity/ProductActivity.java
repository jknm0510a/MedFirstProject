package tw.medfirst.com.project.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
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

import tw.medfirst.com.project.Application;
import tw.medfirst.com.project.Entity.ProductEntity;
import tw.medfirst.com.project.Entity.ProductMenuEntity;
import tw.medfirst.com.project.R;
import tw.medfirst.com.project.adapter.ProductListAdapter;
import tw.medfirst.com.project.adapter.ViewPagerAdapter;
import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.baseview.BaseActivity;
import tw.medfirst.com.project.baseview.ExoPlayerLayout;
import tw.medfirst.com.project.baseview.NLCustomDialog;
import tw.medfirst.com.project.baseview.NLViewPager;
import tw.medfirst.com.project.baseview.ProductListLinearLayout;
import tw.medfirst.com.project.baseview.ViewPagerIndicator;
import tw.medfirst.com.project.manager.MessageManager;

/**
 * Created by KCTsai on 2015/8/24.
 */
public class ProductActivity extends DetailInfoActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        layoutID = R.layout.activity_product;
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //test listview
        addList();
    }

    private void addList() {

        List<ProductMenuEntity> list = new ArrayList<>();
        for(int i = 0; i < 55; i++){
            ProductMenuEntity e = new ProductMenuEntity(0,0,"12", "lp", i, "f", 0, 0);
            list.add(e);
        }
        listAdapter.addData(list);
        listLinearLayout.setAdapter(listAdapter, null, this);
    }



    @Override
    protected void processMessage(Message msg) {

    }

    @Override
    protected void processLoading() {
    }

    @Override
    protected void init() {
        if(root != null)
            root.setBackground(new BitmapDrawable(getResources(), Application.getBitmapFromRes(this, R.mipmap.main_t1_background)));

    }

    @Override
    protected void initIndex() {
        pageIndex = PRODUCT_PAGE;
    }

    @Override
    protected void onResume() {
        super.onResume();
        runSwitch = false;

    }


    public void onChangeClick(View v){
        //test viewpager
//        if(infoView == null)
//            return;
//        infoView.setVisibility(View.VISIBLE);
//        if(b){
//            createProductViewPager(viewPagerSample());
//        }else{
//            createProductViewPager(viewPagerSample2());
//        }

        //test listview

        if(listLinearLayout == null)
            return;
        if(listAdapter == null)
            listAdapter = new ProductListAdapter(this);

        List<ProductMenuEntity> list = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            ProductMenuEntity e = new ProductMenuEntity(0,0,"12", "lp", i, "f", 0, 0);
            list.add(e);
        }
        listAdapter.addData(list);
        listLinearLayout.setAdapter(listAdapter, null, this);

    }

    @Override
    public void onClick(View v) {

        ProductMenuEntity entity = (ProductMenuEntity) v.getTag();
        Logger.e("onClick", entity.getSort());

        if(entity == null || infoView == null)
            return;
        //test viewpager
        int i = entity.getSort();
        if(i % 2 == 0)
            createProductViewPager(viewPagerSample());
        else
            createProductViewPager(viewPagerSample2());
//        Logger.e("Product", entity.getSort());
    }



}
