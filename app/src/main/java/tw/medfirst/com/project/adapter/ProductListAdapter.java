package tw.medfirst.com.project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import tw.medfirst.com.project.Application;
import tw.medfirst.com.project.Entity.ProductMenuEntity;
import tw.medfirst.com.project.R;

/**
 * Created by KCTsai on 2015/9/16.
 */
public class ProductListAdapter extends BaseAdapter{
    //List放進去前請先排好序
    private List<ProductMenuEntity> productList;
    private Context mContext;
    private LayoutInflater mInflater;

    public ProductListAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);

    }

    public void addData(List<ProductMenuEntity> list){
        if(list == null)
            return;
        if(productList != null)
            productList = null;

        productList = list;

    }

    @Override
    public int getCount() {
        if(productList != null)
            return productList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(productList != null && productList.size() < position)
            return productList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public View getView(int position, ImageView convertView, ViewGroup parent, Handler handler, View.OnClickListener listener) {

        if (convertView == null) {
            convertView = new ImageView(mContext);
            convertView.setPadding(0, 0, 0, 40);

            ProductMenuEntity entity = productList.get(position);
            Bitmap bitmap = null;
            String savePath = Application.getPath(entity.getPicName(), "p");

            if(!Application.checkIsFileExist(savePath)){
                //picture not exist - defult bitmap
                bitmap = Application.getBitmapFromRes(mContext, R.mipmap.commodity);
            }else
                bitmap = Application.getBitmapFromSDCard(savePath);

            convertView.setTag(entity);
            convertView.setOnClickListener(listener);

            if(bitmap == null)
                return null;

            convertView.setImageBitmap(bitmap);
        }

        return convertView;
    }
}
