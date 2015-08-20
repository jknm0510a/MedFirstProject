package tw.medfirst.com.project.baseview;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.VideoSurfaceView;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.audio.AudioCapabilitiesReceiver;
import com.google.android.exoplayer.extractor.mp4.Mp4Extractor;
import com.google.android.exoplayer.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.exoplayer.DemoPlayer;
import tw.medfirst.com.project.exoplayer.ExtractorRendererBuilder;

/**
 * Created by KCTsai on 2015/7/23.
 */
public class ExoPlayerView extends VideoSurfaceView implements SurfaceHolder.Callback,
        DemoPlayer.Listener, DemoPlayer.TextListener, DemoPlayer.Id3MetadataListener,
        AudioCapabilitiesReceiver.Listener{
    private Context mContext;
    private DemoPlayer player;
    private Uri contentUri;
    private boolean playerNeedsPrepare;
    private List<String> playList;
    private int videoCount;
    private int index;
    private String prefix;

    public ExoPlayerView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public ExoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }



    private void init() {
        getHolder().addCallback(this);
        prefix = Environment.getExternalStorageDirectory().getPath();
        index = 0;
        videoCount = 0;
//        playList = new ArrayList<String>();
    }

    public void preparePlayer() {
        Logger.e("PlayWhenReady", videoCount);
        if(playList == null || videoCount == 0)
            return;

        String userAgent = Util.getUserAgent(mContext, "ExoPlayerDemo");
        contentUri = Uri.parse(prefix + playList.get(index));
        Logger.e("PlayWhenReady", contentUri);
        if(player == null){
            player = new DemoPlayer(new ExtractorRendererBuilder(mContext, userAgent, contentUri, null,
                    new Mp4Extractor()));

            player.addListener(this);
            player.setTextListener(this);
            player.setMetadataListener(this);
            playerNeedsPrepare = true;
        }
        if (playerNeedsPrepare) {
            player.prepare();
            playerNeedsPrepare = false;
        }
        player.setSurface(getHolder().getSurface());
        player.setPlayWhenReady(true);
        Logger.e("PlayWhenReady", "PlayWhenReady");
    }

    public void releasePlayer(boolean clearRecord) {
        if (player != null) {
            player.release();
            player = null;

            if(clearRecord){
                index = 0;
            }
        }
    }

    public void addPlayList(List<String> l){
        if(l != null){
            playList = l;
            videoCount = playList.size();
            index = 0;
        }
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
    public void onId3Metadata(Map<String, Object> metadata) {

    }

    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        if(playbackState == ExoPlayer.STATE_ENDED){
            index = (index + 1) % videoCount;
            releasePlayer(false);
            preparePlayer();

        }
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, float pixelWidthHeightRatio) {

    }

    @Override
    public void onAudioCapabilitiesChanged(AudioCapabilities audioCapabilities) {

    }

    @Override
    public void onText(String text) {

    }
}
