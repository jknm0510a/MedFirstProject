package tw.medfirst.com.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.dolphinwang.imagecoverflow.CoverFlowView;

import java.io.InputStream;
import java.util.ArrayList;

import tw.medfirst.com.project.adapter.CoverFlowAdapter2;
import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.baseview.BaseActivity;
//import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import tw.medfirst.com.project.gcm.GcmRegistrationAsyncTask;
import tw.medfirst.com.project.manager.HttpManager;
import tw.medfirst.com.project.manager.MessageManager;

public class MainActivity extends BaseActivity{
    private final static String TAG = "MainActivity";
    private int template;
    private TextView tv_name;
    private ViewGroup notificationView;
    private String[] iconName;
    protected GcmRegistrationAsyncTask gcmRegistrationAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        template = 1;
        switch(template) {
            case 1:             //����1
                layoutID = R.layout.activity_main_template_1;
                super.onCreate(savedInstanceState);
//                init();
                initTemplate1();
                break;
        }
        gcmRegistrationAsyncTask = new GcmRegistrationAsyncTask(this);
        gcmRegistrationAsyncTask.execute();
        Application.sendMessage(mHandler, MessageManager.PROCESS_LOADING, 0, 0, null);

    }

    @Override
    protected void init() {
        tv_name = (TextView)findViewById(R.id.tv_name);
        iconName = getResources().getStringArray(R.array.icon_name);
        notificationView = (ViewGroup) findViewById(R.id.notification_view);
    }

    private void initTemplate1() {
        CoverFlowView<CoverFlowAdapter2> mCoverFlowView=(CoverFlowView<CoverFlowAdapter2>)findViewById(R.id.coverflow);
        mCoverFlowView.setCoverFlowGravity(CoverFlowView.CoverFlowGravity.CENTER_VERTICAL);
        mCoverFlowView.setCoverFlowLayoutMode(CoverFlowView.CoverFlowLayoutMode.WRAP_CONTENT);

//        mCoverFlowView.enableReflection(true);
//        mCoverFlowView.setReflectionHeight(15);
//        mCoverFlowView.setReflectionGap(100);
//        mCoverFlowView.enableReflectionShader(true);
        mCoverFlowView.setVisibleImage(5);
        ViewGroup view = (ViewGroup) mCoverFlowView.getParent();
        view.setBackgroundDrawable(new BitmapDrawable(getBitmapFromRes(this, R.drawable.home_bg)));

        ArrayList<Bitmap> mData = new ArrayList<Bitmap>();
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.scan_code_icon));
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.onsale_icon));
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.product_icon));
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.health_info_icon));
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.membership_icon));
//        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.a_2));
//        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.a_1));
        CoverFlowAdapter2 adapter = new CoverFlowAdapter2(mHandler);
//        Logger.e("AAFFDSFSDF", mData.get(0).getHeight());
        adapter.setData(mData);
        mCoverFlowView.setAdapter(adapter);
        mCoverFlowView.setCoverFlowListener(new CoverFlowView.CoverFlowListener<CoverFlowAdapter2>() {

            @Override
            public void imageOnTop(CoverFlowView<CoverFlowAdapter2> coverFlowView, int position, float left, float top, float right, float bottom) {
//                Logger.e("imageOnTop", position);
                tv_name.setText(iconName[position]);
            }

            @Override
            public void topImageClicked(CoverFlowView<CoverFlowAdapter2> coverFlowView, int position) {
                Logger.e("topImageClicked", position);
//                notificationView.setAnimation();
                notificationView.setVisibility(View.VISIBLE);
                notificationView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.grow_from_bottom));

            }

            @Override
            public void invalidationCompleted() {

            }
        });

//        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
//        mCoverFlowTitle = (TextSwitcher) findViewById(R.id.title);
//
//        coverFlowEntityList = new ArrayList<CoverFlowEntity>();
//        mCoverFlowAdapter = new CoverFlowAdapter(this);
//
//        mCoverFlowTitle.setFactory(new ViewSwitcher.ViewFactory() {
//            @Override
//            public View makeView() {
//                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
//                TextView textView = (TextView) inflater.inflate(R.layout.item_coverflow_title, null);
//                return textView;
//            }
//        });
//        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
//        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
//        mCoverFlowTitle.setInAnimation(in);
//        mCoverFlowTitle.setOutAnimation(out);
//        mCoverFlow.setOnItemClickListener(this);
//        mCoverFlow.setOnScrollPositionListener(this);

    }

    @Override
    protected void processMessage(Message msg) {

    }

    @Override
    protected void processLoading() {

        HttpManager.getInstance().getDerviceResponseRunnableFromHttp(this, null, "123", "1.1.1.1");
        HttpManager.getInstance().getProductMenuRunnableFromHttp(this, null, "123", "1.1.1.1");
        HttpManager.getInstance().getLayoutInfoRunnableFromHttp(this, null, "123", "1.1.1.1");
    }

    private static Bitmap getBitmapFromRes(Context context, int resId){
        try{
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;

            InputStream is = context.getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(is, null, opt);
//            Bitmap bitmap = BitmapFactory.decodeFile(sd + "/" + file);
//            return bitmap;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onDestroy() {
//        Logger.e(TAG, "onDestroy()");
//        gcmRegistrationAsyncTask.onDestroy();
        super.onDestroy();
    }
}
