package tw.medfirst.com.project.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import tw.medfirst.com.project.Application;
import tw.medfirst.com.project.R;
import tw.medfirst.com.project.activity.MainActivity;
import tw.medfirst.com.project.baseview.BaseActivity;
import tw.medfirst.com.project.baseview.ExoPlayerLayout;
import tw.medfirst.com.project.manager.MessageManager;

/**
 * Created by KCTsai on 2015/7/22.
 */
public class GuidanceActivity extends BaseActivity{
    private static final String TAG = "GuidanceActivity";
    private ExoPlayerLayout exoPlayerLayout;
    private List<String> videoList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        layoutID = R.layout.activity_guidance;
        super.onCreate(savedInstanceState);
        Application.sendMessage(mHandler, MessageManager.PROCESS_LOADING, 0, 0, null);
        pageIndex = LOADING_PAGE;
    }

    @Override
    protected void onResume() {
        super.onResume();
        runSwitch = false;
    }

    @Override
    protected void processMessage(Message msg) {

    }

    @Override
    protected void processLoading() {
        videoList.add(Application.VEDIO_PATH + "/635724721531434309.mp4");
        videoList.add(Application.VEDIO_PATH + "/635726599845794411.mp4");
        if(exoPlayerLayout != null) {
            exoPlayerLayout.initView(videoList, true);
            exoPlayerLayout.preparePlayer(0);
        }
    }

    @Override
    protected void init() {
        exoPlayerLayout = (ExoPlayerLayout) findViewById(R.id.exoplayer_root_layout);
        videoList = new ArrayList<String>();


    }

    @Override
    protected void initIndex() {
        pageIndex = GUIDANCE_PAGE;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        exoPlayerLayout.releasePlayer();
        super.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_UP){
//            Logger.e(TAG, "MotionEvent.ACTION_UP");
            startActivity(MainActivity.class, MAIN_PAGE);
        }
        return super.onTouchEvent(event);
    }
}
