package tw.medfirst.com.project.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;


/**
 * Created by KCTsai on 2015/7/28.
 */
public class Rotate3dAnimation extends Animation{
//    private final float mFromDegrees;
//    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
//    private final float[] mDegressChange;
    private final float[] mFromDegrees;
    private final float[] mToDegrees;
    private Camera mCamera;

    /**
     * Creates a new 3D rotation on the Y axis. The rotation is defined by its
     * start angle and its end angle. Both angles are in degrees. The rotation
     * is performed around a center point on the 2D space, definied by a pair
     * of X and Y coordinates, called centerX and centerY. When the animation
     * starts, a translation on the Z axis (depth) is performed. The length
     * of the translation can be specified, as well as whether the translation
     * should be reversed in time.
     *
//     * @param fromDegrees the start angle of the 3D rotation
//     * @param toDegrees the end angle of the 3D rotation
     * @param centerX the X center of the 3D rotation
     * @param centerY the Y center of the 3D rotation
     * @param reverse true if the translation should be reversed, false otherwise
     */
//    public Rotate3dAnimation(float fromDegrees, float toDegrees,
//                             float centerX, float centerY, float depthZ, boolean reverse) {
//        mFromDegrees = fromDegrees;
//        mToDegrees = toDegrees;
//        mCenterX = centerX;
//        mCenterY = centerY;
//        mDepthZ = depthZ;
//        mReverse = reverse;
//    }

    public Rotate3dAnimation(float[] fromDegrees, float[] toDegrees, float centerX, float centerY, float depthZ, boolean reverse){
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
//        final float fromDegrees = mFromDegrees;
//        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

        float degreesX = mFromDegrees[0] + ((mToDegrees[0] - mFromDegrees[0]) * interpolatedTime);
        float degreesY = mFromDegrees[1] + ((mToDegrees[1] - mFromDegrees[1]) * interpolatedTime);
        float degreesZ = mFromDegrees[2] + ((mToDegrees[2] - mFromDegrees[2]) * interpolatedTime);

        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;
        final Matrix matrix = t.getMatrix();

        camera.save();
        if (mReverse) {
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        } else {
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }
        camera.rotate(degreesX, degreesY, degreesZ);
//        camera.rotateX(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }


}

