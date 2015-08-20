package tw.medfirst.com.project.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import tw.medfirst.com.project.baseunit.Logger;

/**
 * Created by KCTsai on 2015/8/13.
 */
public class TScrollView extends HorizontalScrollView {
    public TScrollView(Context context) {
        super(context);
    }

    public TScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void scrollTo(int x, int y) {
        Logger.e("TScrollView", "scrollTo: " + x + ", " + y);
        super.scrollTo(x, y);
    }

}
