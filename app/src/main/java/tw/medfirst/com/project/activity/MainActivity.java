package tw.medfirst.com.project.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tw.medfirst.com.project.Application;
import tw.medfirst.com.project.R;
import tw.medfirst.com.project.adapter.CustomCoverFlowAdapter;
import tw.medfirst.com.project.baseview.BaseActivity;
//import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import tw.medfirst.com.project.baseview.CoverFlowView;
import tw.medfirst.com.project.baseview.PhotoStyleImageView;
import tw.medfirst.com.project.gcm.GcmRegistrationAsyncTask;
import tw.medfirst.com.project.manager.HttpManager;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private final static String TAG = "MainActivity";
    private final static int PRODUCT_ACTIVITY = 1;
    private final static int HISTORY_ACTIVITY = 4;
    private final static int ANMATION_START_DELAY = 10000;
    private final static int ANMATION_DELAY = 500;


    private int template;
//    private TextView tv_name;
    private ViewGroup notificationView;
    private String[] iconName;
    protected GcmRegistrationAsyncTask gcmRegistrationAsyncTask;
    private ViewGroup iconGroup;
    private int timeCount = 0;
//    private IconAnimation icon;
//    private PhotoStyleImageView member_icon;
//    private PhotoStyleImageView sale_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        template = 2;
        switch(template) {
            case 1:
                layoutID = R.layout.activity_main_template_1;
                super.onCreate(savedInstanceState);
//                init();
                initTemplate1();
                break;
            case 2:
                layoutID = R.layout.activity_main_template_2;
                super.onCreate(savedInstanceState);
                initTemplate2();
                break;
        }
        gcmRegistrationAsyncTask = new GcmRegistrationAsyncTask(this);
        gcmRegistrationAsyncTask.execute();
//        Application.sendMessage(mHandler, MessageManager.PROCESS_LOADING, 0, 0, null);

//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mHandler.sendEmptyMessage(9999);
//            }
//        }, 7000);

    }

    private void initTemplate2() {
        iconGroup = (ViewGroup) findViewById(R.id.main_icon_root);
        if(iconGroup == null)
            return;
        List<Bitmap> bitmapList = new ArrayList<Bitmap>();
        addBitmap(bitmapList);

        if(bitmapList.size() != 5 || iconGroup.getChildCount() != 5)
            return;

        for(int i = 0; i < iconGroup.getChildCount(); i++){
            PhotoStyleImageView v = (PhotoStyleImageView) iconGroup.getChildAt(i);
            v.setImageBitmap(bitmapList.get(i), i);
            v.setOnClickListener(this);
        }

        mHandler.postDelayed(animationRunnable, ANMATION_START_DELAY);
    }

    private Runnable animationRunnable = new Runnable() {
        @Override
        public void run() {

            switch(timeCount){
                case 0:
                case 1:
                case 2:
                case 3:
                    ((PhotoStyleImageView)iconGroup.getChildAt(timeCount)).startAnimation();
                    timeCount++;
                    mHandler.postDelayed(animationRunnable, ANMATION_DELAY);
                    break;
                case 4:
                    ((PhotoStyleImageView)iconGroup.getChildAt(timeCount)).startAnimation();
                    timeCount = 0;
                    mHandler.postDelayed(animationRunnable, ANMATION_START_DELAY);
                    break;
            }

        }
    };

    private void addBitmap(List<Bitmap> bitmapList) {
        Bitmap bitmap = Application.getBitmapFromRes(this, R.mipmap.main_icon_t2_sale);
        bitmapList.add(bitmap);
        Bitmap bitmap1 = Application.getBitmapFromRes(this, R.mipmap.main_icon_t2_product);
        bitmapList.add(bitmap1);
        Bitmap bitmap2 = Application.getBitmapFromRes(this, R.mipmap.main_icon_t2_member);
        bitmapList.add(bitmap2);
        Bitmap bitmap3 = Application.getBitmapFromRes(this, R.mipmap.main_icon_t2_price);
        bitmapList.add(bitmap3);
        Bitmap bitmap4 = Application.getBitmapFromRes(this, R.mipmap.main_icon_t2_history);
        bitmapList.add(bitmap4);
    }


    @Override
    protected void init() {
//        tv_name = (TextView)findViewById(R.id.tv_name);
        iconName = getResources().getStringArray(R.array.icon_name);
        notificationView = (ViewGroup) findViewById(R.id.notification_view);
        if(root != null)
            root.setBackground(new BitmapDrawable(getResources(), Application.getBitmapFromRes(this, R.mipmap.main_t2_background)));

    }

    @Override
    protected void initIndex() {
        pageIndex = MAIN_PAGE;
    }

    private void initTemplate1() {
        CoverFlowView<CustomCoverFlowAdapter> mCoverFlowView=(CoverFlowView<CustomCoverFlowAdapter>)findViewById(R.id.coverflow);
        mCoverFlowView.setCoverFlowGravity(CoverFlowView.CoverFlowGravity.CENTER_VERTICAL);
        mCoverFlowView.setCoverFlowLayoutMode(CoverFlowView.CoverFlowLayoutMode.WRAP_CONTENT);

        mCoverFlowView.setVisibleImage(5);
//        ViewGroup view = (ViewGroup) mCoverFlowView.getParent();
//        view.setBackgroundDrawable(new BitmapDrawable(getResources(), Application.getBitmapFromRes(this, R.drawable.home_bg)));

        ArrayList<Bitmap> mData = new ArrayList<Bitmap>();
        mData.add(Application.getBitmapFromRes(this, R.mipmap.scan_code_icon));
        mData.add(Application.getBitmapFromRes(this, R.mipmap.onsale_icon));
        mData.add(Application.getBitmapFromRes(this, R.mipmap.product_icon));
        mData.add(Application.getBitmapFromRes(this, R.mipmap.health_info_icon));
        mData.add(Application.getBitmapFromRes(this, R.mipmap.membership_icon));
        CustomCoverFlowAdapter adapter = new CustomCoverFlowAdapter(mHandler);

        adapter.setData(mData);
        mCoverFlowView.setAdapter(adapter);
        mCoverFlowView.setCoverFlowListener(new CoverFlowView.CoverFlowListener<CustomCoverFlowAdapter>() {

            @Override
            public void imageOnTop(CoverFlowView<CustomCoverFlowAdapter> coverFlowView, int position, float left, float top, float right, float bottom) {
//                Logger.e("imageOnTop", position);
//                tv_name.setText(iconName[position]);
            }

            @Override
            public void topImageClicked(CoverFlowView<CustomCoverFlowAdapter> coverFlowView, int position) {
                coverFlowView.setTag(position);
                onIconClick(coverFlowView);
            }

            @Override
            public void invalidationCompleted() {

            }
        });

    }

    public void onIconClick(View v) {
        final int position = (int) v.getTag();
        if(v != null){
            switch (position){
                case PRODUCT_ACTIVITY:
                    startActivity(ProductActivity.class, position);
                    break;
                case HISTORY_ACTIVITY:
                    startActivity(HistoryActivity.class, position);
                    break;

            }
        }
    }

    @Override
    protected void processMessage(Message msg) {
        switch (msg.what){
            case 9999:
                notificationView.setVisibility(View.VISIBLE);
                notificationView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.grow_from_bottom));
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(9998);
                    }
                }, 7000);
                break;
            case 9998:
                notificationView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.grow_from_top));
                notificationView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void processLoading() {

//        HttpManager.getInstance().getDerviceResponseRunnableFromHttp(this, null, "123", "1.1.1.1");
//        HttpManager.getInstance().getProductMenuRunnableFromHttp(this, null, "123", "1.1.1.1");
//        HttpManager.getInstance().getLayoutInfoRunnableFromHttp(this, null, "123", "1.1.1.1");
    }

    public void onRetryClick(View view){
        if(iconGroup == null)
            return;
        for(int i = 0; i < iconGroup.getChildCount(); i++)
            ((PhotoStyleImageView)iconGroup.getChildAt(i)).startAnimation();


    }



    @Override
    protected void onDestroy() {
//        Logger.e(TAG, "onDestroy()");
//        gcmRegistrationAsyncTask.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        runSwitch = false;
    }

    @Override
    public void onClick(View v) {
        onIconClick(v);
    }
}
