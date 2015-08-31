package tw.medfirst.com.project;

import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.google.android.exoplayer.VideoSurfaceView;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.audio.AudioCapabilitiesReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.baseview.BaseActivity;
import tw.medfirst.com.project.baseview.ExoPlayerView;
import tw.medfirst.com.project.exoplayer.DemoPlayer;
import tw.medfirst.com.project.manager.MessageManager;

/**
 * Created by KCTsai on 2015/7/22.
 */
public class GuidanceActivity extends BaseActivity{
    private static final String TAG = "GuidanceActivity";
    private ExoPlayerView surfaceView;
    private List<String> videoList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        layoutID = R.layout.activity_guidance;
        super.onCreate(savedInstanceState);
        Application.sendMessage(mHandler, MessageManager.PROCESS_LOADING, 0, 0, null);
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
        surfaceView.addPlayList(videoList);
        if(surfaceView != null)
            surfaceView.preparePlayer();
    }

    @Override
    protected void init() {
        surfaceView = (ExoPlayerView) findViewById(R.id.surface_view);
        videoList = new ArrayList<String>();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        surfaceView.releasePlayer(true);
        super.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_UP){
//            Logger.e(TAG, "MotionEvent.ACTION_UP");
            startActivity(MainActivity.class);
        }
        return super.onTouchEvent(event);
    }
}
