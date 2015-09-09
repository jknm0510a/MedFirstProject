package tw.medfirst.com.project.animation;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

import tw.medfirst.com.project.baseunit.Logger;

/**
 * Created by KCTsai on 2015/7/28.
 */
public class IconAnimation {

    private final Context mContext;
    private View view;
//    private AnimationSet set;
    private List<Rotate3dAnimation> animationList;

    public IconAnimation(Context context, View view){
        this.mContext = context;
        this.view = view;
//        set = new AnimationSet(false);
        animationList = new ArrayList<Rotate3dAnimation>();
        init();
    }

    private void init() {
        float[] fromDegrees, toDegrees;
        fromDegrees = new float[]{0f, 0f, 0f};
        toDegrees = new float[]{7f, -10f, -7f};
        Rotate3dAnimation in = new Rotate3dAnimation(fromDegrees, toDegrees, 150, 0, 0, false);
//        in.setFillBefore(false);
        in.setDuration(1000);

        fromDegrees = toDegrees;
        toDegrees = new float[]{-6f, -9f, 6f};
        Rotate3dAnimation in2 = new Rotate3dAnimation(fromDegrees, toDegrees, 150, 0, 0, false);

        in2.setDuration(1000);

        fromDegrees = toDegrees;
        toDegrees = new float[]{4f, 3f, -4f};
        Rotate3dAnimation in3 = new Rotate3dAnimation(fromDegrees, toDegrees, 150, 0, 0, false);
        in3.setDuration(1250);

        fromDegrees = toDegrees;
        toDegrees = new float[]{-5f, -2f, 3f};
        Rotate3dAnimation in4 = new Rotate3dAnimation(fromDegrees, toDegrees, 150, 0, 0, false);
        in4.setDuration(1250);

        fromDegrees = toDegrees;
        toDegrees = new float[]{1f, 1f, -1f};
        Rotate3dAnimation in5 = new Rotate3dAnimation(fromDegrees, toDegrees, 150, 0, 0, false);
        in5.setDuration(1250);

        fromDegrees = toDegrees;
        toDegrees = new float[]{0f, 0f, 0f};
        Rotate3dAnimation in6 = new Rotate3dAnimation(fromDegrees, toDegrees, 150, 0, 0, false);
        in6.setDuration(1250);
//        in6.setFillAfter(true);

        animationList.add(in);
        animationList.add(in2);
        animationList.add(in3);
        animationList.add(in4);
        animationList.add(in5);
        animationList.add(in6);
        setAnmationListener(animationList);

//        Rotate3dAnimation in = new Rotate3dAnimation(0, -10, 150, 0, 0,false);
//        in.setDuration(1500);
//        in.setStartOffset(1000);
//        Rotate3dAnimation in2 = new Rotate3dAnimation(-10, 5, 150, 0, 0,false);
//        in2.setDuration(1500);
//        Rotate3dAnimation in3 = new Rotate3dAnimation(5, 0, 150, 0, 0,false);
//        in3.setDuration(1500);
//        Rotate3dAnimation in4 = new Rotate3dAnimation(-5, 0, 150, 150, 0,false);
//        in4.setDuration(1500);
//        animationList.add(in);
//        animationList.add(in2);
//        animationList.add(in3);
//        animationList.add(in4);
//        setAnmationListener(animationList);

    }

    public void startAnimation(){

        view.startAnimation(animationList.get(0));

    }


    public void setAnmationListener(List<Rotate3dAnimation> animationList) {
        for(int i = 0; i < animationList.size() - 1; i++) {
            animationList.get(i).setAnimationListener(new setAnmationListener(animationList.get(i + 1)));
        }

    }

    protected class setAnmationListener implements Animation.AnimationListener{
        Rotate3dAnimation index;

        public setAnmationListener(Rotate3dAnimation index){
            this.index = index;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
//
            view.startAnimation(index);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
