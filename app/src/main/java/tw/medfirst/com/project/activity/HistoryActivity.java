package tw.medfirst.com.project.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import tw.medfirst.com.project.R;
import tw.medfirst.com.project.adapter.HistoryAdapter;
import tw.medfirst.com.project.baseview.BaseActivity;
import tw.medfirst.com.project.baseview.HistoryWall;

/**
 * Created by KCTsai on 2015/8/10.
 */
public class HistoryActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layoutID = R.layout.activity_history;
        super.onCreate(savedInstanceState);
//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative);
        HistoryWall wall = (HistoryWall) findViewById(R.id.wall);


        ArrayList<Bitmap> mData = new ArrayList<Bitmap>();
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.little_square));
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.little_square));
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.big_square));
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.little_square));
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.little_square));
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.recttangle));
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.little_square));
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.little_square));
        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.big_square));
//        mData.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.little_square));
        HistoryAdapter adapter = new HistoryAdapter(mHandler);
        adapter.setData(mData);
        wall.setAdapter(adapter);

    }

    @Override
    protected void processMessage(Message msg) {

    }

    @Override
    protected void processLoading() {

    }

    @Override
    protected void init() {

    }



    @Override
    protected void onResume() {
        super.onResume();
        runSwitch = false;
    }
}
