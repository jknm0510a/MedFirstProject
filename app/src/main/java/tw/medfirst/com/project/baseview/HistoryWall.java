package tw.medfirst.com.project.baseview;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.medfirst.com.project.Application;
import tw.medfirst.com.project.adapter.HistoryAdapter;
import tw.medfirst.com.project.baseunit.Logger;

/**
 * Created by KCTsai on 2015/8/10.
 */
public class HistoryWall extends View {




    enum SHAPE{LITTLE_SQUARE, BIG_SQUARE, RECTTANGLE}

    private final static String TAG = "HistoryWall";

    private static final int GROUP_COUNT = 6;
    private static final int GROUP_WIDTH = 1365;
    private static final int PADDING_SIZE = 10;
    private static final int[] littleSquareSize = new int[]{265, 265};
    private static final int[] bigSquareSize = new int[]{540, 540};
    private static final int[] rectangleSize = new int[]{540, 265};
//    private static final int REFLECTION_HEIGHT = 265;

    private static final int MAX_HEIGHT = 540;
    protected int itemCount;
    private boolean buildReflection;
    protected HistoryAdapter mAdapter;
    protected int totleWidth;
    private float mTouchCurrentX;
    private float mTouchStartX;
    private int dis = 0;
    private boolean isClick = false;
    private int clickItemNo;
//    private RectF mTouchRect;
    private Map<RectF, Integer> rectFMap;

    private PaintFlagsDrawFilter mDrawFilter;
    private boolean mDrawing = false;

    private int[] nextStartPosition;
//    private int[] nextReflectionPosition;


    private Paint mDrawChildPaint;
    private Matrix mChildTransfromer;
    private Matrix mReflectionTransfromer;
    private RecycleBin mRecycler;


    public HistoryWall(Context context) {
        super(context);
        init();
    }

    public HistoryWall(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HistoryWall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);
        mChildTransfromer = new Matrix();
        mReflectionTransfromer = new Matrix();
        mDrawChildPaint = new Paint();
        mDrawChildPaint.setAntiAlias(true);
        mDrawChildPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        mChildTransfromer = new Matrix();
        nextStartPosition = new int[]{0, 0};

//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Logger.e(TAG, "onClick!!!!");
//            }
//        });
//        setLayoutParams(new LinearLayout.LayoutParams(2130, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    public void setAdapter(HistoryAdapter adapter){
        mAdapter = adapter;

        if(adapter != null){
            itemCount = adapter.getCount();
            mRecycler = new RecycleBin();
            totleWidth = getTotleWidth();
            rectFMap = new HashMap<RectF, Integer>(itemCount);
        }

        if (mRecycler != null) {

            mRecycler.clear();
        }
        requestLayout();
    }

    public int getTotleWidth() {
        int n1 = (itemCount / GROUP_COUNT) * GROUP_WIDTH;
        int s = itemCount % GROUP_COUNT;
        switch (s){
            case 1:
            case 2:
                n1 += PADDING_SIZE + littleSquareSize[0];
                break;
            case 3:
                n1 += PADDING_SIZE + littleSquareSize[0] + PADDING_SIZE + bigSquareSize[0];
                break;
            case 4:
                n1 += PADDING_SIZE + littleSquareSize[0] + PADDING_SIZE + bigSquareSize[0] + PADDING_SIZE + littleSquareSize[0];
                break;
            case 5:
                n1 += GROUP_WIDTH;
                break;
        }
        return n1 + PADDING_SIZE;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
//        Log.e(TAG, width + ", " + height);
        setMeasuredDimension(width, height);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mAdapter == null)
            return;
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        Logger.e(TAG, "onDraw");

        if (mAdapter == null) {
            super.onDraw(canvas);
            return;
        }

        mDrawing = true;

        canvas.setDrawFilter(mDrawFilter);

        nextStartPosition[0] = 0;
        nextStartPosition[1] = 0;

        for(int i = 0; i < itemCount; i++){
            drawChild(canvas, i);
//            Logger.e(TAG, canvas.getWidth());
        }

        mDrawing = false;
        for (Map.Entry<RectF, Integer> entry : rectFMap.entrySet()) {
            RectF f = entry.getKey();
//            Logger.e(TAG + " rectF", entry.getValue() +"(" + f.left + ", " + f.top + ", " + f.right + ", " + f.bottom + ")");
        }
        super.onDraw(canvas);
//        Logger.e(TAG, getLeft() + ", " + getTop() + ", " + getRight() + ", " + getBottom());
    }

    private final void drawChild(Canvas canvas, int position) {
        final Bitmap child = mAdapter.getImage(position);
        Bitmap reflection = null;

        makeChildTransfromer(child, position);

        if(buildReflection)
            reflection = obtainReflection(position, child);

        canvas.drawBitmap(child, mChildTransfromer, mDrawChildPaint);
        if(reflection != null){
            canvas.drawBitmap(reflection, mReflectionTransfromer, mDrawChildPaint);
        }
    }

    private Bitmap obtainReflection(int position, Bitmap child) {

        Bitmap reflection = mRecycler.getCachedBitmap(position);
        if (reflection == null || reflection.isRecycled()) {
            mRecycler.removeCachedBitmap(position);
            reflection = createReflectedImage(child);
            if (reflection != null) {
                mRecycler.addBitmap2Cache(position, reflection);

                return reflection;
            }
        }

        return reflection;
    }

    private void makeChildTransfromer(Bitmap child, int position){
        mChildTransfromer.reset();
        mReflectionTransfromer.reset();

        int relativePosition = getRelativePosition(position);
        float[] scale = new float[]{1, 1};
        int[] currentPositon = new int[]{nextStartPosition[0], nextStartPosition[1]};

        int height = child.getHeight();
        int width = child.getWidth();

        mChildTransfromer.postTranslate(currentPositon[0], currentPositon[1]);
        mReflectionTransfromer.postTranslate(currentPositon[0], currentPositon[1] + MAX_HEIGHT + 15);
        if(currentPositon[1] < MAX_HEIGHT / 2)
            buildReflection = true;
        else
            buildReflection = false;

        switch(relativePosition){
            case 0:
                setRectF2Map(currentPositon, littleSquareSize, position);
                nextStartPosition[1] = currentPositon[1] + littleSquareSize[1] + PADDING_SIZE;
                scale = getScale(SHAPE.LITTLE_SQUARE, height, width);
                break;
            case 1:
                setRectF2Map(currentPositon, littleSquareSize, position);
                nextStartPosition[0] = currentPositon[0] + littleSquareSize[0] + PADDING_SIZE;
                nextStartPosition[1] = currentPositon[1] - (littleSquareSize[1] + PADDING_SIZE);
                scale = getScale(SHAPE.LITTLE_SQUARE, height,width);
                break;
            case 2:
                setRectF2Map(currentPositon, bigSquareSize, position);
                nextStartPosition[0] = currentPositon[0] + bigSquareSize[0] + PADDING_SIZE;
                scale = getScale(SHAPE.BIG_SQUARE, height,width);
                break;
            case 3:
                setRectF2Map(currentPositon, littleSquareSize, position);
                nextStartPosition[0] = currentPositon[0] + littleSquareSize[0] + PADDING_SIZE;
                scale = getScale(SHAPE.LITTLE_SQUARE, height,width);
                break;
            case 4:
                setRectF2Map(currentPositon, littleSquareSize, position);
                nextStartPosition[0] = currentPositon[0] - (littleSquareSize[0] + PADDING_SIZE);
                nextStartPosition[1] = currentPositon[1] + littleSquareSize[1] + PADDING_SIZE;
                scale = getScale(SHAPE.LITTLE_SQUARE, height,width);
                break;
            case 5:
                setRectF2Map(currentPositon, rectangleSize, position);
                nextStartPosition[0] = currentPositon[0] + rectangleSize[0] + PADDING_SIZE; //or 2 * littleSquareSize[0] + 2 * PADDING_SIZE
                nextStartPosition[1] = 0;
                scale = getScale(SHAPE.RECTTANGLE, height, width);
                break;
        }
        mChildTransfromer.preScale(scale[0], scale[1]);
        mReflectionTransfromer.preScale(scale[0], scale[1]);
    }

    private void setRectF2Map(int[] currentPositon, final int shapeSize[], int position){
        RectF rectF = new RectF();
        rectF.left = currentPositon[0];
        rectF.top = currentPositon[1];
        rectF.right = rectF.left + shapeSize[0];
        rectF.bottom = rectF.top + shapeSize[1];
        rectFMap.put(rectF, position);
    }

    private float[] getScale(SHAPE shape, int height, int width) {
        float[] scale = new float[2];

        switch(shape){
            case LITTLE_SQUARE:
                scale[0] = (float)littleSquareSize[0] / (float)width;
                scale[1] = (float)littleSquareSize[1] / (float)height;
//                Logger.e(TAG + " 2", littleSquareSize[0] + ", " + littleSquareSize[1]);
                break;
            case BIG_SQUARE:
                scale[0] = (float)bigSquareSize[0] / (float)width;
                scale[1] = (float)bigSquareSize[1] / (float)height;
//                Logger.e(TAG + " 2", bigSquareSize[0] + ", " + bigSquareSize[1]);
                break;
            case RECTTANGLE:
                scale[0] = (float)rectangleSize[0] / (float)width;
                scale[1] = (float)rectangleSize[1] / (float)height;
//
                break;
        }
//        Logger.e(TAG + "scale", scale[0] + ", " + scale[1]);
        return scale;
    }

    private int getRelativePosition(int position) {
        return position % GROUP_COUNT;
    }

    public static Bitmap createReflectedImage(Bitmap originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, 0, width, MAX_HEIGHT / 3, matrix, false);
        Bitmap finalReflection = Bitmap.createBitmap(width, MAX_HEIGHT / 3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(finalReflection);
        canvas.drawBitmap(reflectionImage, 0.0f, 0.0f, null);
        Paint shaderPaint = new Paint();
        LinearGradient shader = new LinearGradient(0, 0, 0, reflectionImage.getHeight(), 0x70ffffff,
                0x00ffffff, Shader.TileMode.MIRROR);
        shaderPaint.setShader(shader);
        shaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, 0, width, reflectionImage.getHeight() + 1, shaderPaint);
        return finalReflection;
    }

    class RecycleBin {

        @SuppressLint("NewApi")
        final LruCache<Integer, Bitmap> bitmapCache = new LruCache<Integer, Bitmap>(
                getCacheSize(getContext())) {
            @Override
            protected int sizeOf(Integer key, Bitmap bitmap) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                } else {
                    return bitmap.getByteCount();
                }
            }

            @Override
            protected void entryRemoved(boolean evicted, Integer key,
                                        Bitmap oldValue, Bitmap newValue) {
                if (evicted && oldValue != null && !oldValue.isRecycled()) {
                    oldValue.recycle();
                    oldValue = null;
                }
            }
        };

        public Bitmap getCachedBitmap(int position) {
            return bitmapCache.get(position);
        }

        public void addBitmap2Cache(int position, Bitmap b) {
            bitmapCache.put(position, b);
            Runtime.getRuntime().gc();
        }

        public Bitmap removeCachedBitmap(int position) {
            if (position < 0 || position >= bitmapCache.size()) {
                return null;
            }

            return bitmapCache.remove(position);
        }

        public void clear() {
            bitmapCache.evictAll();
        }

        private int getCacheSize(Context context) {
            final ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            final int memClass = am.getMemoryClass();
            // Target ~5% of the available heap.
            int cacheSize = 1024 * 1024 * memClass / 21;
            return cacheSize;
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        //去除倒影部分，觸控不回應
        if(event.getY() > MAX_HEIGHT)
            return false;

        switch (action){
            case MotionEvent.ACTION_DOWN:
                mTouchCurrentX = mTouchStartX = event.getX();
                clickItemNo = checkRectF(dis + event.getX(), event.getY());
                isClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(event.getX() - mTouchStartX) < 50) {
                    isClick = true;
                }else{
                    isClick = false;
                    float shift = event.getX() - mTouchCurrentX;
                    mTouchCurrentX = event.getX();
                    dis -= shift * 1.5;
                    if(dis < 0)
                        dis = 0;
                    else if(dis >= totleWidth - (Application.screen_width - getLeft()))
                        dis = totleWidth - (Application.screen_width - getLeft());
                    scrollTo(dis, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
//                Logger.e(TAG, Boolean.toString(isClick) + " & " + clickItemNo);
                if(isClick && clickItemNo != -1) {
                    Toast.makeText(getContext(), "Ckick Item : " + clickItemNo, Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
    }

    private int checkRectF(float x, float y) {
        for (Map.Entry<RectF, Integer> entry : rectFMap.entrySet()) {
            if(entry.getKey().contains(x, y)){
//                Logger.e(TAG, "click: " + entry.getValue() + ": (" + x + ", " + y + ")");
                return entry.getValue();
            }
        }
        return -1;
    }


}
