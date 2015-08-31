package tw.medfirst.com.project.baseview;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.VideoSurfaceView;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.audio.AudioCapabilitiesReceiver;
import com.google.android.exoplayer.extractor.mp4.Mp4Extractor;
import com.google.android.exoplayer.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tw.medfirst.com.project.R;
import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.exoplayer.DemoPlayer;
import tw.medfirst.com.project.exoplayer.ExtractorRendererBuilder;

/**
 * Created by KCTsai on 2015/7/9.
 */
public class ExoPlayerLayout extends RelativeLayout implements SurfaceHolder.Callback,
        DemoPlayer.Listener, DemoPlayer.TextListener, DemoPlayer.Id3MetadataListener,
        AudioCapabilitiesReceiver.Listener{
    protected Context mContext;
    private VideoSurfaceView surfaceView;
    private DemoPlayer player;
    private List<String> playList;
    private Uri contentUri;
    private boolean playerNeedsPrepare;
//    private String path = null;
    protected Button playBtn;
    public long pauseTime = 0;

    private int playIndex = 0;
    private boolean isRecycle;

    public ExoPlayerLayout(Context context) {
        super(context);
        mContext = context;
//        initView();
    }

    public ExoPlayerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
//        initView();
    }

    public ExoPlayerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
//        initView();
    }

    public void initSingleSourceView(String path, boolean b) {
        List<String> l = new ArrayList<>(1);
        l.add(path);
        initView(l, b);
    }

    public void initView(final List<String> playList, final boolean isRecycle) {
        LayoutParams l = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        this.setLayoutParams(l);
        surfaceView = (VideoSurfaceView) findViewById(R.id.surface_view);
        surfaceView.getHolder().addCallback(this);

        this.playList = playList;
        this.isRecycle = isRecycle;

        playBtn = (Button) findViewById(R.id.play_btn);
        playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                preparePlayer(0);

            }
        });
    }

    public void preparePlayer(long seekTime) {
        String userAgent = Util.getUserAgent(mContext, "ExoPlayerDemo");
//        this.playIndex = playIndex;
        if(playList == null || playIndex < 0 || playIndex >= playList.size() || playList.get(playIndex) == null)
            return;

        contentUri = Uri.parse(playList.get(playIndex));
        if(player == null){
            player = new DemoPlayer(new ExtractorRendererBuilder(mContext, userAgent, contentUri, null,
                    new Mp4Extractor()));
            player.addListener(this);
            player.setTextListener(this);
            player.seekTo(seekTime);
            player.setMetadataListener(this);
            playerNeedsPrepare = true;
        }
        if (playerNeedsPrepare) {
            player.prepare();
            playerNeedsPrepare = false;
        }
        player.setSurface(surfaceView.getHolder().getSurface());
        player.setPlayWhenReady(true);
        playBtn.setVisibility(View.GONE);

    }

    public void stopVideo(){
        if(player != null){
            releasePlayer();
            playBtn.setVisibility(View.VISIBLE);
        }
    }

    public void pausePlayer(){
        pauseTime = player.getCurrentPosition();
        releasePlayer();
    }

    public void resumePlayer(){
        preparePlayer(pauseTime);
//        player.seekTo(pauseTime);

    }

    public void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
//            eventLogger.endSession();
//            eventLogger = null;
        }
    }

    public void setPlayIndex(int playIndex) {
        this.playIndex = playIndex;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onAudioCapabilitiesChanged(AudioCapabilities audioCapabilities) {

    }

    @Override
    public void onId3Metadata(Map<String, Object> metadata) {

    }

    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        if(playbackState == ExoPlayer.STATE_ENDED){
            playIndex++;

            if(playIndex < 0 || playIndex >= playList.size()){
                if(isRecycle) {
                    playIndex = playIndex % playList.size();
                    releasePlayer();
                    preparePlayer(0);
                }
                else{
                    playBtn.setVisibility(View.VISIBLE);
                    releasePlayer();
                    playIndex = 0;
                }
            }else{
                releasePlayer();
                preparePlayer(0);
            }

        }
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, float pixelWidthHeightRatio) {

    }

    @Override
    public void onText(String text) {

    }



}
