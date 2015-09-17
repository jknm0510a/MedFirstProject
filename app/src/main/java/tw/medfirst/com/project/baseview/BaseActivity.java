package tw.medfirst.com.project.baseview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;

import tw.medfirst.com.project.R;
import tw.medfirst.com.project.activity.GuidanceActivity;
import tw.medfirst.com.project.activity.MainActivity;
import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.manager.MessageManager;

/**
 * Created by KCTsai on 2015/6/16.
 */
public abstract class BaseActivity extends Activity{
    private static final String TAG = "BaseActivity";
    protected final static int LOADING_PAGE = -3;
    protected final static int GUIDANCE_PAGE = -2;
    protected final static int MAIN_PAGE = -1;
    protected final static int ONSALE_PAGE = 0;
    protected final static int PRODUCT_PAGE = 1;
    protected final static int MEMBER_PAGE = 2;
    protected final static int SCAN_PAGE = 3;
    protected final static int HISTORY_PAGE = 4;

    protected Handler mHandler;
    protected int layoutID = -1;
    protected ViewGroup root;
    protected static int guidanceTimer = 0;
    protected final static int MAX_WAITTING = 30;
    protected boolean runSwitch = false;
    protected int pageIndex;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_UP){
            guidanceTimer = 0;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(layoutID != -1){
            setContentView(layoutID);
            root = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        }
        initHandler();
        init();
        initIndex();
        mHandler.post(guidanceRunnable);

    }

    protected void initHandler(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case MessageManager.PROCESS_LOADING:
                        processLoading();
                        break;
                    default:
                        processMessage(msg);
                }
            }
        };
        runSwitch = true;
    }



    @Override
    protected void onResume() {
        if(mHandler != null) {
            runSwitch = true;
            guidanceTimer = 0;
//            mHandler.post(guidanceRunnable);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        runSwitch = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        runSwitch = false;

    }

    private Runnable guidanceRunnable= new Runnable() {

        @Override
        public void run() {
            if(!runSwitch)
                return;
            mHandler.postDelayed(this, 1000);
            guidanceTimer++;

            Logger.e(TAG, guidanceTimer);
            if(guidanceTimer >= MAX_WAITTING) {
                guidanceTimer = 0;
                startActivity(GuidanceActivity.class, -2);
            }
        }
    };

    public void startActivity(Class a, int index){
        Intent intent = new Intent();
        intent.setClass(this, a);
//        intent.putExtra("index", index);
        startActivity(intent);
        if(index == MAIN_PAGE)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        else
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();

    }

//    protected void initIndex(){
//        Intent intent = getIntent();
//        if(intent != null){
//            pageIndex = intent.getIntExtra("index", -3);
//        }
//    }

//    @Override
//    public void onAttachedToWindow() {
////        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
//        super.onAttachedToWindow();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if(pageIndex == MAIN_PAGE)
                    Logger.e(TAG, "KEYCODE_BACK");
                else
                    startActivity(MainActivity.class, MAIN_PAGE);
                return true;
            case KeyEvent.KEYCODE_HOME:
                Logger.e(TAG, "KEYCODE_HOME");
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
//        return super.onKeyDown(keyCode, event);
    }

    protected abstract void processMessage(Message msg);
    protected abstract void processLoading();
    protected abstract void init();
    protected abstract void initIndex();

}
