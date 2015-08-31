package tw.medfirst.com.project.baseview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.ViewGroup;

import tw.medfirst.com.project.activity.GuidanceActivity;
import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.manager.MessageManager;

/**
 * Created by KCTsai on 2015/6/16.
 */
public abstract class BaseActivity extends Activity{
    private static final String TAG = "BaseActivity";
    protected Handler mHandler;
    protected int layoutID = -1;
    protected ViewGroup root;
    protected static int guidanceTimer = 0;
    protected final static int MAX_WAITTING = 30;
    protected boolean runSwitch = false;

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
                startActivity(GuidanceActivity.class);
            }
        }
    };

    public void startActivity(Class a){
        Intent intent = new Intent();
        intent.setClass(this, a);
        startActivity(intent);
        finish();
    }

    protected abstract void processMessage(Message msg);
    protected abstract void processLoading();
    protected abstract void init();

}
