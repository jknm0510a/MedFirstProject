package tw.medfirst.com.project.adapter;

import android.graphics.Bitmap;
import android.os.Handler;

import java.util.ArrayList;

/**
 * Created by KCTsai on 2015/6/18.
 */
public class CustomCoverFlowAdapter extends BaseCoverFlowAdapter{
    protected ArrayList<Bitmap> mData;
    protected Handler mHandler;

    public CustomCoverFlowAdapter(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public void setData(ArrayList<Bitmap> mData){
        this.mData = mData;
    }



    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Bitmap getImage(int position) {
        return mData.get(position);
    }
}
