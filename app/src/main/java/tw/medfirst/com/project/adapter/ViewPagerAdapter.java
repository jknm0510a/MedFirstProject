package tw.medfirst.com.project.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import tw.medfirst.com.project.Application;
import tw.medfirst.com.project.Entity.ProductEntity;
import tw.medfirst.com.project.R;
import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.baseview.ExoPlayerLayout;

/**
 * Created by KCTsai on 2015/6/16.
 */
public class ViewPagerAdapter extends PagerAdapter {
    protected List<View> viewList;
    protected List<String> titleList;
    private LayoutInflater mInflater;
    Context mContext;

    public ViewPagerAdapter(Context context){
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setViewList(List<View> viewList){
        this.viewList = viewList;
    }

    public void setEntityList(List<ProductEntity> pList){
        List<View> viewList = new ArrayList<>();

        for(ProductEntity p : pList){
            String path = Application.getPath(p.getName(), p.getType());
            if(path == null || !Application.checkIsFileExist(path))
                continue;

            switch (p.getType()){
                case "v":
                    ExoPlayerLayout exoPlayerLayout = (ExoPlayerLayout) mInflater.inflate(R.layout.exoplayer_viewpager_item, null);
                    exoPlayerLayout.initView(path);
                    viewList.add(exoPlayerLayout);
                    break;
                case "p":
                    ViewGroup root = (ViewGroup) mInflater.inflate(R.layout.image_viewpager_item, null);
                    ImageView image = (ImageView) root.getChildAt(0);
                    if(image != null){
                       image.setBackground(new BitmapDrawable(mContext.getResources(),
                               Application.getBitmapFromSDCard(path)));
                        viewList.add(root);
                    }
                    break;
            }
        }
        setViewList(viewList);
    }

    public void setTitleList(List<String> titleList){
        this.titleList = titleList;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;
    }

    @Override
    public int getCount() {

        return viewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        Logger.e("ViewPagerAdapter" + "destroyItem: ", "container - " + container.getChildCount());
        Logger.e("ViewPagerAdapter" + "destroyItem: ", "viewList - " + viewList.size());
        Logger.e("ViewPagerAdapter" + "destroyItem: ", "position - " + position);
        container.removeView(viewList.get(position));
//        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {

        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);//直接用适配器?完成??的?示，所以?上面可以看到，我??有使用PagerTitleStrip。?然你可以使用。

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    public View getView(int i){
        return viewList.get(i);
    }

    public void clearAllViews(){
        viewList.clear();
    }

    public boolean checkIsListNull(){
        if(viewList == null)
            return true;
        return false;
    }

    public void clearAdapterViews(){
        if(!checkIsListNull()) {
            for (int i = 0; i < getCount(); i++) {
                if (getView(i) instanceof ExoPlayerLayout) {
                    ((ExoPlayerLayout) getView(i)).releasePlayer();
                }
            }
//            clearAllViews();
            notifyDataSetChanged();
        }
        System.gc();
    }
}
