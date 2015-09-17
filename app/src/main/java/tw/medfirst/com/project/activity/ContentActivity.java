package tw.medfirst.com.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import tw.medfirst.com.project.R;
import tw.medfirst.com.project.adapter.ViewPagerAdapter;
import tw.medfirst.com.project.baseview.BaseActivity;
import tw.medfirst.com.project.baseview.NLViewPager;
import tw.medfirst.com.project.baseview.ViewPagerIndicator;

/**
 * Created by KCTsai on 2015/9/11.
 */
public abstract class ContentActivity extends BaseActivity implements View.OnClickListener{

    protected ViewGroup actionbarGroup;

    //product info viewpager
    private ViewPagerIndicator indicator;
    private ViewPagerAdapter pagerAdapter;
    private NLViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
//        initViewPager();
    }

    protected void initActionBar(){
        actionbarGroup = (ViewGroup) findViewById(R.id.action_bar_root);
        if(actionbarGroup != null && pageIndex != -3) {
            for(int i = 0; i < actionbarGroup.getChildCount(); i++)
                actionbarGroup.getChildAt(i).setTag(i - 1);
            actionbarGroup.getChildAt(pageIndex + 1).setSelected(true); // group內最新優惠為1而不是0
        }

    }

    public void onActionBarClick(View v){
        if((int)v.getTag() == pageIndex)
            return;
        switch ((int)v.getTag()){
            case MAIN_PAGE:
                startActivity(MainActivity.class, MAIN_PAGE);
                break;
            case PRODUCT_PAGE:
                startActivity(ProductActivity.class, PRODUCT_PAGE);
                break;
            case HISTORY_PAGE:
                startActivity(HistoryActivity.class, HISTORY_PAGE);
                break;
        }
    }

}
