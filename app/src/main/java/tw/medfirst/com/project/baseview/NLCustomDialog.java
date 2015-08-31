package tw.medfirst.com.project.baseview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tw.medfirst.com.project.Application;
import tw.medfirst.com.project.R;
import tw.medfirst.com.project.adapter.ViewPagerAdapter;

/**
 * Created by KCTsai on 2015/8/24.
 */
public class NLCustomDialog extends Dialog {

    public NLCustomDialog(Context context) {
        super(context);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    public NLCustomDialog(Context context, int theme) {
        super(context, theme);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    public static class Builder {

        private Context context;
        private int mLeftIcon;
        private String mTitle;
        private int mRightIcon;
        private String mTip;
        private String mMessage;
        private int mGravity;
        private String mPositiveButtonText;
        private String mNegativeButtonText;
        private View mContentView;
        private DialogInterface.OnClickListener mPositiveButtonClickListener;
        private DialogInterface.OnClickListener mNegativeButtonClickListenr;
        private boolean mCancelable = true;

        public Builder(Context context) {

            this.context = context;
            mLeftIcon = -1;
            mRightIcon = -1;
            mGravity = Gravity.CENTER;
        }

        public Builder setLeftIcon(int leftIcon) {

            this.mLeftIcon = leftIcon;
            return this;
        }

        public Builder setTitle(String title) {

            this.mTitle = title;
            return this;
        }

        public Builder setTitle(int title) {

            this.mTitle = (String) context.getText(title);
            return this;
        }

        public Builder setRightIcon(int rightIcon) {

            this.mRightIcon = rightIcon;
            return this;
        }

        public Builder setTip(String tip) {

            this.mTip = tip;
            return this;
        }

        public Builder setTip(int tip) {

            this.mTip = (String) context.getText(tip);
            return this;
        }

        public Builder setTitle(CharSequence title) {

            this.mTitle = title.toString();
            return this;
        }

        public Builder setMessage(int message) {

            this.mMessage = (String) context.getText(message);
            return this;
        }

        public Builder setMessage(String message) {

            this.mMessage = message;
            return this;
        }

        public Builder setMessage(CharSequence message) {

            this.mMessage = message.toString();
            return this;
        }

        public Builder setMessage(String message, int gravity) {

            this.mMessage = message;
            this.mGravity = gravity;
            return this;
        }

        public Builder setMessage(int message, int gravity) {

            this.mMessage = (String) context.getText(message);
            this.mGravity = gravity;
            return this;
        }

        public Builder setMessage(CharSequence message, int gravity) {

            this.mMessage = message.toString();
            this.mGravity = gravity;
            return this;
        }

        public Builder setView(View v) {

            this.mContentView = v;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {

            this.mCancelable = cancelable;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener) {

            this.mPositiveButtonText = (String) context.getText(positiveButtonText);
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener) {
            this.mPositiveButtonText = positiveButtonText;
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(CharSequence positiveButtonText, DialogInterface.OnClickListener listener) {

            this.mPositiveButtonText = positiveButtonText.toString();
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, DialogInterface.OnClickListener listener) {

            this.mNegativeButtonText = (String) context.getText(negativeButtonText);
            this.mNegativeButtonClickListenr = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,DialogInterface.OnClickListener listener) {

            this.mNegativeButtonText = negativeButtonText;
            this.mNegativeButtonClickListenr = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence negativeButtonText,DialogInterface.OnClickListener listener) {

            this.mNegativeButtonText = negativeButtonText.toString();
            this.mNegativeButtonClickListenr = listener;
            return this;
        }

//        public NLCustomDialog createInfoDialog() {
//
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final NLCustomDialog dialog = new NLCustomDialog(context, R.style.CustomDialog);
//
//            RelativeLayout contentView = (RelativeLayout) inflater.inflate(R.layout.viewpager_view, null);
////            RelativeLayout.LayoutParams l= new RelativeLayout.LayoutParams(1440, 880);
////            contentView.setLayoutParams(l);
//
//            NLViewPager viewPager = (NLViewPager) contentView.findViewById(R.id.viewpager);
//            ViewPagerIndicator indicator = (ViewPagerIndicator) contentView.findViewById(R.id.indicator);
////            ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this);
//            viewPager.setIndicator(indicator);
//
//            View view1, view2, view3;
//            List<View> viewList = new ArrayList<View>();
//            view1 = inflater.inflate(R.layout.layout1, null);
//            view2 = inflater.inflate(R.layout.layout2, null);
//            view3 = inflater.inflate(R.layout.layout3, null);
//            viewList.add(view1);
//
//
//            ExoPlayerLayout exoPlayerLayout;
//            if(Application.checkIsFileExist(Environment.getExternalStorageDirectory().getPath() + "/MedFirst/video/635724721531434309.mp4")) {
//                exoPlayerLayout = (ExoPlayerLayout) inflater.inflate(R.layout.exoplayer_view, null);
//                exoPlayerLayout.initView(Environment.getExternalStorageDirectory().getPath() + "/MedFirst/video/635724721531434309.mp4");
//                viewList.add(exoPlayerLayout);
//            }
//            viewList.add(view2);
//            viewList.add(view3);
//            pagerAdapter.setViewList(viewList);
//            viewPager.setAdapter(pagerAdapter);
//            pagerAdapter.notifyDataSetChanged();
//
//            dialog.setContentView(contentView);
//            dialog.setCancelable(true);
//            dialog.setDialogSize(1440, 850);
//            return dialog;
//        }

    }

    public void setDialogSize(int w, int h){
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.width = w;
//        lp.height = h;
//        lp.copyFrom(getWindow().getAttributes());
//        getWindow().setAttributes(lp);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.x = 450;
        lp.width = w;
        lp.height = h;
        dialogWindow.setAttributes(lp);
    }
}