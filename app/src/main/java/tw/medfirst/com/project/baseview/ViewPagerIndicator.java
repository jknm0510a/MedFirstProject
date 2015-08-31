package tw.medfirst.com.project.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import tw.medfirst.com.project.R;

/**
 * Created by KCTsai on 2015/7/9.
 */
public class ViewPagerIndicator extends LinearLayout{
    protected Context context;
    protected int count;

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
//        context = context1;
        this.context = context;
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        context = context1;
        this.context = context;
    }

    public ViewPagerIndicator(Context context) {
        super(context);
        this.context = context;
    }

    public void initView(int size){
        LayoutParams params = new LayoutParams(15, 15);
        if(size == 0)
            return;
        if(getChildCount() != 0)
            removeAllViews();
        for(int i = 0; i < size; i++){
            View child = new View(context);
            child.setBackgroundResource(R.drawable.viewpager_indicator_selector);
//            child.set(0, 0, 5, 0);
            params.setMargins(0,0,5,0);
            child.setLayoutParams(params);
            addView(child);
        }
        getChildAt(0).setSelected(true);
    }

    public void ClearSelected() {
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).setSelected(false);
    }
    public void setSelected(int i){
        getChildAt(i).setSelected(true);
    }
}
