package tw.medfirst.com.project.baseview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import tw.medfirst.com.project.Entity.ProductMenuEntity;
import tw.medfirst.com.project.adapter.ProductListAdapter;

/**
 * Created by KCTsai on 2015/9/16.
 */
public class ProductListLinearLayout extends LinearLayout{

    private static final int MAX_ROW_NUMBER = 3;
    private ProductListAdapter mAdapter;
    private Context mContext;
    private LayoutInflater mInflater;

    public ProductListLinearLayout(Context context) {
        super(context);
        initLayout(context);

    }

    public ProductListLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public ProductListLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);

    }

    private void initLayout(Context context) {
        setOrientation(HORIZONTAL);
        this.mContext = context;
        mInflater = LayoutInflater.from(context);

    }

    public void setAdapter(ProductListAdapter adapter, Handler handler, OnClickListener l) {
        // TODO Auto-generated method stub

        if(mAdapter != null && getChildCount() > 0) {
            removeAllViews();
        }
        mAdapter = adapter;
        addPageData(handler, l);
    }

    public void addPageData(Handler handler, OnClickListener l){
        if(mAdapter == null || mAdapter.getCount() == 0)
            return;

        View view = null;
        LinearLayout layoutItem = initNewLayoutItem();
        for(int i = 0; i < mAdapter.getCount(); i++){
            if(layoutItem == null)
                layoutItem = initNewLayoutItem();

            view = mAdapter.getView(i, null, layoutItem, handler, l);

            if (view == null){
                continue;
            }

            layoutItem.addView(view);

            if(layoutItem.getChildCount() == MAX_ROW_NUMBER) {
                addView(layoutItem);
                layoutItem = initNewLayoutItem();
            }
        }
        if(layoutItem.getChildCount() != 0){
            addView(layoutItem);
        }
    }

    public LinearLayout initNewLayoutItem(){
        LinearLayout layoutItem = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutItem.setOrientation(VERTICAL);
        layoutItem.setLayoutParams(params);
        layoutItem.setPadding(0, 0, 40, 0);
        return layoutItem;
    }
}
