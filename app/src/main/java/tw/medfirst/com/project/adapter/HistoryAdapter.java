package tw.medfirst.com.project.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by KCTsai on 2015/8/10.
 */
public class HistoryAdapter extends BaseCoverFlowAdapter{
    protected ArrayList<Bitmap> mData;
    protected Handler mHandler;

    public HistoryAdapter(Handler hadler){
        this.mHandler = mHandler;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public void setData(ArrayList<Bitmap> mData){
        this.mData = mData;
    }

    public Bitmap getImage(int position){
        return mData.get(position);
    }
}
