package tw.medfirst.com.project.baseview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import tw.medfirst.com.project.animation.IconAnimation;

/**
 * Created by KCTsai on 2015/9/7.
 */
public class PhotoStyleImageView extends LinearLayout{
    private static final int IMAGE_WIDTH = 290;
    private static final int IMAGE_HEIGHT = 410;
    private final static int[] DEGREE = {7, 3, 0, -3, -7};
    private Context mContext;
    private IconAnimation animation;


    public PhotoStyleImageView(Context context) {
        super(context);
        mContext = context;
    }

    public PhotoStyleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public PhotoStyleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void setImageBitmap(Bitmap bitmap, int index){
        int rotation = DEGREE[index];
        ImageView img = new ImageView(mContext);
        img.setLayoutParams(new LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT));
        img.setImageBitmap(bitmap);
        img.setRotation(rotation);
        img.setTag(index);
        addView(img);

        float rotationArea[] = rotationArea(rotation, IMAGE_WIDTH, IMAGE_HEIGHT);
        RelativeLayout.LayoutParams l = (RelativeLayout.LayoutParams) getLayoutParams();
        l.height = (int)rotationArea[1];
        l.width = (int)rotationArea[0];
        setGravity(Gravity.CENTER);
        setLayoutParams(l);

        animation = new IconAnimation(mContext, this);
//        animation.startAnimation();
    }

    @Override
    public void setOnClickListener(OnClickListener l){
        if(getChildCount() == 0 || l == null)
            return;
        getChildAt(0).setOnClickListener(l);
    }

    public float[] rotationArea(int degree, int width, int height){
        Matrix m = new Matrix();
        m.setRotate(degree);
        float[] roationMatrix = new float[9];
        m.getValues(roationMatrix);
        float helfWidth = width / 2;
        float helfHeight = height / 2;
        //�H�ϧΤ����I�����I�A���X�|�Ө����y��
        List<float[]> corner = new ArrayList<float[]>();
        float[] p0 = {-helfWidth, helfHeight};
        float[] p1 = { helfWidth, helfHeight};
        float[] p2 = {-helfWidth, -helfHeight};
        float[] p3 = {helfWidth, -helfHeight};
        corner.add(p0);
        corner.add(p1);
        corner.add(p2);
        corner.add(p3);
        //�α���᪺�y�Ч�X�P���I�۶Z�̻���X Y�y�� - �q�`���
//        List<float[]> rotationCorner = new ArrayList<float[]>();
        float MaxX = 0, MaxY = 0;
        for(float[] p : corner){
            //�o���I����᪺�y��
            float ppX = p[0] * roationMatrix[0] + p[1] * roationMatrix[1];
            float ppY = p[0] * roationMatrix[3] + p[1] * roationMatrix[4];
            //��X�̤j�� - �Y���I��X��Y�y�лP���I�۶Z�̻�
            if (Math.abs(ppX) >= MaxX)
                MaxX = Math.abs(ppX);
            if (Math.abs(ppY) >= MaxY)
                MaxY = Math.abs(ppY);
        }
        //�]���H�x�}���߱��ର��١AX&Y * 2�N�O�x�}�����
        return new float[]{MaxX * 2, MaxY * 2};

    }

    public void startAnimation(){
        if(animation != null)
            animation.startAnimation();
    }

//    public class AnimationRunnable implements Runnable {
//        private int index;
//
//
//        public int getIndex() {
//            return index;
//        }
//
//        public AnimationRunnable(int index){
//
//        }
//        @Override
//        public void run() {
//
//        }
//    }

}
